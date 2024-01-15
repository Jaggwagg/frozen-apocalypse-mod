package jaggwagg.frozen_apocalypse.block;

import jaggwagg.frozen_apocalypse.entity.mob.IceweaverEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IcyCobwebBlock extends CobwebBlock {
    public IcyCobwebBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.slowMovement(state, new Vec3d(0.25, 0.05000000074505806, 0.25));

        if (!(entity instanceof IceweaverEntity)) {
            entity.setInPowderSnow(true);
        }
    }
}
