package jaggwagg.frozen_apocalypse.mixin.client;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Unique
    private Matrix4f sunMatrixCopy;

    @ModifyVariable(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0), ordinal = 1)
    private Matrix4f scaleSun(Matrix4f in) {
        float scale = (FrozenApocalypse.timeOfDay / 240000.0f) * 2;

        sunMatrixCopy = copy(in);
        Matrix4f copy = copy(in);

        if (scale < 1.0f) {
            copy.scale(1.0f - scale, 1.0f, 1.0f - scale);
        } else {
            copy.scale(0.1f, 1.0f, 0.1f);
        }

        return copy;
    }

    @ModifyVariable(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 1), ordinal = 1)
    private Matrix4f fixMoonAndStars(Matrix4f in) {
        Matrix4f copy = copy(sunMatrixCopy);

        sunMatrixCopy = null;

        return copy;
    }

    private Matrix4f copy(Matrix4f matrix) {
        Matrix4f copy = new Matrix4f();

        copy.m00(matrix.m00());
        copy.m01(matrix.m01());
        copy.m02(matrix.m02());
        copy.m03(matrix.m03());
        copy.m10(matrix.m10());
        copy.m11(matrix.m11());
        copy.m12(matrix.m12());
        copy.m13(matrix.m13());
        copy.m20(matrix.m20());
        copy.m21(matrix.m21());
        copy.m22(matrix.m22());
        copy.m23(matrix.m23());
        copy.m30(matrix.m30());
        copy.m31(matrix.m31());
        copy.m32(matrix.m32());
        copy.m33(matrix.m33());

        return copy;
    }
}
