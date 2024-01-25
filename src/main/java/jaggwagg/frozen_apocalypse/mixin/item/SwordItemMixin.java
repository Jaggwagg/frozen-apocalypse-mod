package jaggwagg.frozen_apocalypse.mixin.item;

import jaggwagg.frozen_apocalypse.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordItemMixin {
    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    public void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (state.isOf(ModBlocks.ICY_COBWEB.getBlock())) {
            cir.setReturnValue(15.0f);
        }
    }
}
