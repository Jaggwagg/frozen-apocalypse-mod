package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ApocalypseEffectsMixin {
    @Unique
    private static int calculateDay(World world) {
        return (int) Math.floor(world.getTimeOfDay() / 24000.0f);
    }

    @Inject(method = "tickChunk", at = @At("HEAD"))
    private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerWorld serverWorld = ((ServerWorld) (Object) this);
        FrozenApocalypse.frozenApocalypseLevel = calculateDay(serverWorld);
        ChunkPos chunkPos = chunk.getPos();
        BlockPos blockPos = serverWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING, serverWorld.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15));

        if (serverWorld.isClient()) {
            return;
        }

        if (!FrozenApocalypse.CONFIG.getFrozenApocalypseEnabled()) {
            return;
        }

        if (!serverWorld.getDimension().bedWorks()) {
            return;
        }

        if (!serverWorld.getGameRules().get(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL_OVERRIDE).get()) {
            serverWorld.getGameRules().get(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL).set(FrozenApocalypse.frozenApocalypseLevel, serverWorld.getServer());
        }

        if (serverWorld.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > FrozenApocalypse.CONFIG.getMaxApocalypseLevel()) {
            serverWorld.getGameRules().get(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL).set(FrozenApocalypse.CONFIG.getMaxApocalypseLevel(), serverWorld.getServer());
        }

        switch (FrozenApocalypse.frozenApocalypseLevel) {
            case 0:
                break;
            case 1:
                if (serverWorld.random.nextInt(128) == 0) {
                    setLeafDecay(serverWorld, blockPos);
                    setPodzol(serverWorld, blockPos);
                }
                break;
            case 2:
                if (serverWorld.random.nextInt(64) == 0) {
                    setLeafDecay(serverWorld, blockPos);
                    setPodzol(serverWorld, blockPos);
                }
                break;
            case 3:
                if (serverWorld.random.nextInt(64) == 0) {
                    setIce(serverWorld, blockPos);
                    setSnow(serverWorld, blockPos);
                    setLeafDecay(serverWorld, blockPos);
                    setPodzol(serverWorld, blockPos);
                }
                break;
            case 4:
                if (serverWorld.random.nextInt(32) == 0) {
                    setIce(serverWorld, blockPos);
                    setSnow(serverWorld, blockPos);
                    setLeafDecay(serverWorld, blockPos);
                    setPodzol(serverWorld, blockPos);
                }
                break;
            case 5:
                if (serverWorld.random.nextInt(32) == 0) {
                    setSnow(serverWorld, blockPos);
                    setLeafDecay(serverWorld, blockPos);
                    setPodzol(serverWorld, blockPos);
                    setPackedIce(serverWorld, blockPos);
                    setObsidian(serverWorld, blockPos);
                }
                break;
            default:
                if (serverWorld.random.nextInt(32) == 0) {
                    setPowderSnow(serverWorld, blockPos);
                    setLeafDecay(serverWorld, blockPos);
                    setPodzol(serverWorld, blockPos);
                    setPackedIce(serverWorld, blockPos);
                    setObsidian(serverWorld, blockPos);

                    if (serverWorld.isRaining()) {
                        serverWorld.setWeather(99999999, 0, false, false);
                    }
                }
                break;
        }
    }

    @Unique
    public void setIce(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.WATER)) {
            serverWorld.setBlockState(blockPos.down(), Blocks.ICE.getDefaultState());
        }
    }

    @Unique
    public void setPackedIce(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.WATER) || serverWorld.getBlockState(blockPos.down()).isOf(Blocks.ICE)) {
            serverWorld.setBlockState(blockPos.down(), Blocks.PACKED_ICE.getDefaultState());
        }
    }

    @Unique
    public void setObsidian(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.LAVA)) {
            if (serverWorld.getBlockState(blockPos.down()).getFluidState().getLevel() > 7) {
                serverWorld.setBlockState(blockPos.down(), Blocks.OBSIDIAN.getDefaultState());
                serverWorld.syncWorldEvent(1501, blockPos.down(), 0);
            }
        }
    }

    @Unique
    public void setPodzol(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.GRASS_BLOCK)) {
            serverWorld.setBlockState(blockPos.down(), Blocks.PODZOL.getDefaultState());
        }
    }

    @Unique
    public void setLeafDecay(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).contains(LeavesBlock.PERSISTENT)) {
            serverWorld.removeBlock(blockPos.down(), true);
        }
    }

    @Unique
    public void setSnow(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).contains(FluidBlock.LEVEL)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).contains(LeavesBlock.PERSISTENT)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.ICE) || serverWorld.getBlockState(blockPos.down()).isOf(Blocks.PACKED_ICE)) {
            return;
        }

        serverWorld.setBlockState(blockPos, Blocks.SNOW.getDefaultState());
    }

    @Unique
    public void setPowderSnow(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).contains(FluidBlock.LEVEL)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).contains(LeavesBlock.PERSISTENT)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.SNOW_BLOCK)) {
            return;
        }

        serverWorld.setBlockState(blockPos, Blocks.SNOW_BLOCK.getDefaultState());
    }
}
