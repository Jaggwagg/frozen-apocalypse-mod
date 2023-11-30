package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.world.FrozenApocalypseGameRules;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.LeavesBlock;
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
        ChunkPos chunkPos = chunk.getPos();
        BlockPos blockPos = serverWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING, serverWorld.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15));
        FrozenApocalypse.frozenApocalypseLevel = serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL);
        int updateSpeed;

        if (serverWorld.isClient()) {
            return;
        }

        if (!FrozenApocalypse.CONFIG.getFrozenApocalypseEnabled()) {
            return;
        }

        if (!serverWorld.getDimension().bedWorks()) {
            return;
        }

        if (!serverWorld.getGameRules().get(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL_OVERRIDE).get()) {
            serverWorld.getGameRules().get(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL).set(calculateDay(serverWorld), serverWorld.getServer());
            FrozenApocalypse.frozenApocalypseLevel = serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL);

            if (FrozenApocalypse.frozenApocalypseLevel > serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_MAX_LEVEL)) {
                serverWorld.getGameRules().get(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL).set(serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_MAX_LEVEL), serverWorld.getServer());
                FrozenApocalypse.frozenApocalypseLevel = serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL);
            }
        }

        if (serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_UPDATE_SPEED) == 0) {
            updateSpeed = 0;
        } else {
            updateSpeed = (int) Math.ceil((Math.ceil(3.0 / serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_UPDATE_SPEED) * 128) / FrozenApocalypse.frozenApocalypseLevel));
        }

        if (updateSpeed < 1) {
            return;
        }

        if (serverWorld.getRandom().nextInt(updateSpeed) <= 1) {
            if (FrozenApocalypse.frozenApocalypseLevel > 0) {
                setLeafDecay(serverWorld, blockPos);
                setPodzol(serverWorld, blockPos);
            }

            if (FrozenApocalypse.frozenApocalypseLevel > 2) {
                setIce(serverWorld, blockPos);
                setSnow(serverWorld, blockPos);
            }

            if (FrozenApocalypse.frozenApocalypseLevel > 4) {
                setPackedIce(serverWorld, blockPos);
                setObsidian(serverWorld, blockPos);
            }

            if (FrozenApocalypse.frozenApocalypseLevel > 5) {
                setSnowBlock(serverWorld, blockPos);

                if (serverWorld.isRaining()) {
                    serverWorld.setWeather(99999999, 0, false, false);
                }
            }
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

        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.ICE) || serverWorld.getBlockState(blockPos.down()).isOf(Blocks.PACKED_ICE) || serverWorld.getBlockState(blockPos.down()).isOf(Blocks.AIR)) {
            return;
        }

        serverWorld.setBlockState(blockPos, Blocks.SNOW.getDefaultState());
    }

    @Unique
    public void setSnowBlock(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).contains(FluidBlock.LEVEL)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).contains(LeavesBlock.PERSISTENT)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.SNOW_BLOCK) || serverWorld.getBlockState(blockPos.down()).isOf(Blocks.AIR)) {
            return;
        }

        serverWorld.setBlockState(blockPos, Blocks.SNOW_BLOCK.getDefaultState());
    }
}
