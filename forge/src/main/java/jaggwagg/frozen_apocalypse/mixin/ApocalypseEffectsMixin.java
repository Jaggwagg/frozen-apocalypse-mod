package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.gamerules.FrozenApocalypseGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ApocalypseEffectsMixin {
    private static int calculateDay(Level world) {
        return (int) Math.floor(world.getGameTime() / 24000.0f);
    }

    @Inject(method = "tickChunk", at = @At("HEAD"))
    private void randomTick(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerLevel serverWorld = ((ServerLevel)(Object)this);
        ChunkPos chunkPos = chunk.getPos();
        int startX = chunkPos.getMinBlockX();
        int startZ = chunkPos.getMinBlockZ();
        BlockPos blockPos = serverWorld.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, serverWorld.getBlockRandomPos(startX, 0, startZ, 15));

        FrozenApocalypse.timeOfDay = (int)serverWorld.getGameTime();

        if (!FrozenApocalypse.CONFIG.getFrozenApocalypseEnabled()) {
            return;
        }

        if (!serverWorld.getGameRules().getBoolean(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL_OVERRIDE)) {
            serverWorld.getGameRules().getRule(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL).set(calculateDay(serverWorld), serverWorld.getServer());
        }

        if (serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL) > FrozenApocalypse.CONFIG.getMaxApocalypseLevel()) {
            serverWorld.getGameRules().getRule(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL).set(FrozenApocalypse.CONFIG.getMaxApocalypseLevel(), serverWorld.getServer());
        }

        int apocalypseLevel = serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL);

        if (!serverWorld.dimensionType().bedWorks()) {
            return;
        }

        if (apocalypseLevel > 3) {
            if (serverWorld.random.nextInt((int)Math.ceil(256.0f / apocalypseLevel)) <= 1) {
                setPowderSnow(serverWorld, blockPos);
                setMiscBlocks(serverWorld, blockPos);

                if (serverWorld.isRaining()) {
                    serverWorld.setWeatherParameters(1000000, 0, false, false);
                }
            }
        }

        if (apocalypseLevel > 2) {
            if (serverWorld.random.nextInt((int)Math.ceil(128.0f / apocalypseLevel)) <= 1) {
                setPackedIce(serverWorld, blockPos);
                setObsidian(serverWorld, blockPos);
            }
        }

        if (apocalypseLevel > 1 && apocalypseLevel < 4) {
            if (serverWorld.random.nextInt((int)Math.ceil(128.0f / apocalypseLevel)) <= 1) {
                setIce(serverWorld, blockPos);
                setSnow(serverWorld, blockPos);
            }
        }

        if (apocalypseLevel > 0) {
            if (serverWorld.random.nextInt((int)Math.ceil(64.0f / apocalypseLevel)) <= 1) {
                setLeafDecay(serverWorld, blockPos);
                setPodzol(serverWorld, blockPos);
            }
        }
    }

    public void setIce(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).is(Blocks.WATER)) {
            serverWorld.setBlockAndUpdate(blockPos.below(), Blocks.ICE.defaultBlockState());
        }
    }

    public void setPackedIce(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).is(Blocks.WATER) || serverWorld.getBlockState(blockPos.below()).is(Blocks.ICE)) {
            serverWorld.setBlockAndUpdate(blockPos.below(), Blocks.ICE.defaultBlockState());
        }
    }

    public void setObsidian(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).is(Blocks.LAVA)) {
            if (serverWorld.getBlockState(blockPos.below()).getFluidState().getAmount() > 7) {
                serverWorld.setBlockAndUpdate(blockPos.below(), Blocks.OBSIDIAN.defaultBlockState());
                /*serverWorld.syncWorldEvent(1501, blockPos.below(), 0);
                serverWorld.blockEvent();*/
            }
        }
    }

    public void setPodzol(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).is(Blocks.GRASS_BLOCK)) {
            serverWorld.setBlockAndUpdate(blockPos.below(), Blocks.PODZOL.defaultBlockState());
        }
    }

    public void setLeafDecay(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).hasProperty(LeavesBlock.PERSISTENT)) {
            serverWorld.removeBlock(blockPos.below(), true);
        }
    }

    public void setMiscBlocks(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).is(Blocks.CAMPFIRE) || serverWorld.getBlockState(blockPos.below()).is(Blocks.SOUL_CAMPFIRE) || serverWorld.getBlockState(blockPos.below()).is(Blocks.TORCH)) {
            serverWorld.removeBlock(blockPos.below(), true);
        }
    }

    public void setSnow(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).hasProperty(LiquidBlock.LEVEL)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.below()).hasProperty(LeavesBlock.PERSISTENT)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.below()).is(Blocks.ICE) || serverWorld.getBlockState(blockPos.below()).is(Blocks.PACKED_ICE)) {
            return;
        }

        serverWorld.setBlockAndUpdate(blockPos, Blocks.SNOW.defaultBlockState());
    }

    public void setPowderSnow(ServerLevel serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.below()).hasProperty(LiquidBlock.LEVEL)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.below()).hasProperty(LeavesBlock.PERSISTENT)) {
            return;
        }

        serverWorld.setBlockAndUpdate(blockPos, Blocks.POWDER_SNOW.defaultBlockState());
    }
}
