package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Unique
    private Matrix4f sunMatrixCopy;

    @ModifyVariable(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0), ordinal = 1)
    private Matrix4f scaleSun(Matrix4f in) {
        float scale = (FrozenApocalypse.timeOfDay / 240000.0f) * 2;

        sunMatrixCopy = new Matrix4f(in);
        Matrix4f copy = new Matrix4f(in);

        if (scale < 1.0f) {
            return copy.scale(1.0f - scale, 1.0f, 1.0f - scale);
        } else {
            return copy.scale(0.1f, 1.0f, 0.1f);
        }
    }

    @ModifyVariable(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 1), ordinal = 1)
    private Matrix4f fixMoonAndStars(Matrix4f in) {
        Matrix4f copy = new Matrix4f(sunMatrixCopy);

        sunMatrixCopy = null;

        return copy;
    }
}
