package jaggwagg.frozen_apocalypse.block;

import jaggwagg.frozen_apocalypse.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class FrostedGrassBlock extends GrassBlock {
    public FrostedGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (FrozenSurvivable.canNotSurvive(state, world, pos)) {
            world.setBlockState(pos, ModBlocks.RegisteredBlocks.DEAD_GRASS_BLOCK.getBlock().getDefaultState());
        }
    }
}
