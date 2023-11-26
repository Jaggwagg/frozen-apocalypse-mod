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
        Matrix4f copy = new Matrix4f(in);
        sunMatrixCopy = new Matrix4f(copy);

        if (!FrozenApocalypse.isFrozenApocalypseEnabled) {
            return copy;
        }

        return switch (FrozenApocalypse.frozenApocalypseLevel) {
            case 0 -> copy;
            case 1 -> copy.scale(0.8f, 1.0f, 0.8f);
            case 2 -> copy.scale(0.6f, 1.0f, 0.6f);
            case 3 -> copy.scale(0.4f, 1.0f, 0.4f);
            default -> copy.scale(0.2f, 1.0f, 0.2f);
        };
    }

    @ModifyVariable(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 1), ordinal = 1)
    private Matrix4f fixMoonAndStars(Matrix4f in) {
        Matrix4f copy = new Matrix4f(sunMatrixCopy);

        sunMatrixCopy = null;

        return copy;
    }
}
