package jaggwagg.frozen_apocalypse.block;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class FrostedGrassBlock extends GrassBlock implements FrozenSurvivable {
    public FrostedGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.canNotSurvive(state, world, pos)) {
            if (FrozenApocalypse.apocalypseLevel.canGrassTurnToPermafrost()) {
                world.setBlockState(pos, ModBlocks.PERMAFROST.getBlock().getDefaultState());
            } else {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
        }
    }
}
