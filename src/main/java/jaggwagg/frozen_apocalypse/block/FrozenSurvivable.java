package jaggwagg.frozen_apocalypse.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public interface FrozenSurvivable {
    default boolean canNotSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.getFluidState().getLevel() == 8) {
            return true;
        } else {
            if (blockState.isOf(Blocks.SNOW)) {
                return false;
            }

            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i >= world.getMaxLightLevel();
        }
    }
}
