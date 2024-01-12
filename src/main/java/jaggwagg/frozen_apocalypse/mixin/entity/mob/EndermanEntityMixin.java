package jaggwagg.frozen_apocalypse.mixin.entity.mob;

import jaggwagg.frozen_apocalypse.entity.mob.ShiverstareEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {
    @Inject(method = "isPlayerStaring", at = @At("HEAD"))
    private void isPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        EndermanEntity endermanEntity = (EndermanEntity) (Object) this;
        ItemStack itemStack = player.getInventory().armor.get(3);

        if (player.isCreative() || player.isSpectator()) {
            return;
        }

        if (!itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            Vec3d vec3d = player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(endermanEntity.getX() - player.getX(), endermanEntity.getEyeY() - player.getEyeY(), endermanEntity.getZ() - player.getZ());
            double d = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double e = vec3d.dotProduct(vec3d2);

            if (e > 1.0 - 0.025 / d) {
                if (player.canSee(endermanEntity)) {
                    if (endermanEntity instanceof ShiverstareEntity) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 2));
                    }
                }
            }
        }
    }
}
