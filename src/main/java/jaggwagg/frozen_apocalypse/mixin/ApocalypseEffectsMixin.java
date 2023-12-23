package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import jaggwagg.frozen_apocalypse.network.FrozenApocalypseNetworking;
import jaggwagg.frozen_apocalypse.world.FrozenApocalypseGameRules;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
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

import java.util.List;

@Mixin(ServerWorld.class)
public abstract class ApocalypseEffectsMixin {
    @Unique
    private static int calculateDay(World world) {
        return (int) Math.floor(world.getTimeOfDay() / 24000.0f);
    }

    @Inject(method = "tickChunk", at = @At("HEAD"))
    private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerWorld serverWorld = ((ServerWorld) (Object) this);
        List<ServerPlayerEntity> playerList = serverWorld.getPlayers();
        PacketByteBuf buf = PacketByteBufs.create();
        ChunkPos chunkPos = chunk.getPos();
        BlockPos blockPos = serverWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING, serverWorld.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15));
        FrozenApocalypse.frozenApocalypseLevel = serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL);
        int updateSpeed;

        if (serverWorld.isClient()) {
            return;
        }

        if (!FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_ENABLED) {
            return;
        }

        if (!serverWorld.getDimension().bedWorks()) {
            return;
        }

        if (FrozenApocalypse.frozenApocalypseLevel < 0) {
            serverWorld.getGameRules().get(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL).set(0, serverWorld.getServer());
            FrozenApocalypse.frozenApocalypseLevel = serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL);
        }

        if (!serverWorld.getGameRules().get(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL_OVERRIDE).get()) {
            for (ApocalypseLevel frozenApocalypseLevel : FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_LEVELS) {
                if (frozenApocalypseLevel.STARTING_DAY == calculateDay(serverWorld)) {
                    serverWorld.getGameRules().get(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_LEVEL).set(frozenApocalypseLevel.APOCALYPSE_LEVEL, serverWorld.getServer());
                    break;
                }
            }
        }

        if (FrozenApocalypse.CONFIG.SUN_SIZE_CHANGES_ENABLED) {
            buf.writeInt(FrozenApocalypse.frozenApocalypseLevel);

            for (ServerPlayerEntity player : playerList) {
                if (player != null) {
                    ServerPlayNetworking.send(player, FrozenApocalypseNetworking.FROZEN_APOCALYPSE_LEVEL_ID, buf);
                }
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
            for (ApocalypseLevel frozenApocalypseLevel : FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_LEVELS) {
                if (frozenApocalypseLevel.APOCALYPSE_LEVEL == FrozenApocalypse.frozenApocalypseLevel) {
                    if (frozenApocalypseLevel.LEAF_DECAY) {
                        setLeafDecay(serverWorld, blockPos);
                    }

                    if (frozenApocalypseLevel.GRASS_TO_PODZOL) {
                        setPodzol(serverWorld, blockPos);
                    }

                    if (frozenApocalypseLevel.WATER_TO_ICE) {
                        setIce(serverWorld, blockPos);
                    }

                    if (frozenApocalypseLevel.PLACE_SNOW) {
                        setSnow(serverWorld, blockPos);
                    }

                    if (frozenApocalypseLevel.ICE_TO_PACKED_ICE) {
                        setPackedIce(serverWorld, blockPos);
                    }

                    if (frozenApocalypseLevel.LAVA_TO_OBSIDIAN) {
                        setObsidian(serverWorld, blockPos);
                    }

                    if (frozenApocalypseLevel.PLACE_SNOW_BLOCK) {
                        setSnowBlock(serverWorld, blockPos);
                    }

                    if (frozenApocalypseLevel.DISABLE_WEATHER) {
                        if (serverWorld.isRaining() || serverWorld.isThundering()) {
                            serverWorld.setWeather(99999999, 0, false, false);
                        }
                    }

                    break;
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
