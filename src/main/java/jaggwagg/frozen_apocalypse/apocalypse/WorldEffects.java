package jaggwagg.frozen_apocalypse.apocalypse;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import jaggwagg.frozen_apocalypse.network.FrozenApocalypseNetwork;
import jaggwagg.frozen_apocalypse.registry.FrozenApocalypseGameRules;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

public final class WorldEffects {
    private static int calculateDay(ServerWorld serverWorld) {
        return (int) Math.floor(serverWorld.getTimeOfDay() / 24000.0f);
    }

    public static boolean shouldSkipTick(ServerWorld serverWorld) {
        return serverWorld.isClient() || !FrozenApocalypse.CONFIG.isFrozenApocalypseEnabled() || AllEffects.isSafeDimension(serverWorld);
    }

    public static void initializeFrozenApocalypseLevel(ServerWorld serverWorld) {
        for (ApocalypseLevel apocalypseLevel : FrozenApocalypse.CONFIG.getApocalypseLevels()) {
            if (apocalypseLevel.getApocalypseLevel() == Math.max(0, serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.RegisteredIntegerGameRules.APOCALYPSE_LEVEL.getKey()))) {
                FrozenApocalypse.apocalypseLevel = apocalypseLevel;
            }
        }
    }

    public static void updateFrozenApocalypseLevel(ServerWorld serverWorld) {
        if (serverWorld.getGameRules().get(FrozenApocalypseGameRules.RegisteredBooleanGameRules.LEVEL_UPDATES_EACH_DAY.getKey()).get()) {
            int maxStartingDay = 0;

            for (ApocalypseLevel apocalypseLevel : FrozenApocalypse.CONFIG.getApocalypseLevels()) {
                if (apocalypseLevel.getStartingDay() <= calculateDay(serverWorld) && apocalypseLevel.getStartingDay() >= maxStartingDay) {
                    serverWorld.getGameRules().get(FrozenApocalypseGameRules.RegisteredIntegerGameRules.APOCALYPSE_LEVEL.getKey()).set(apocalypseLevel.getApocalypseLevel(), serverWorld.getServer());
                    maxStartingDay = apocalypseLevel.getStartingDay();
                }
            }
        }
    }

    public static void sendFrozenApocalypseLevelToPlayers(ServerWorld serverWorld) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(FrozenApocalypse.apocalypseLevel.getApocalypseLevel());

        serverWorld.getPlayers().forEach(player -> {
            if (ServerPlayNetworking.canSend(player, FrozenApocalypseNetwork.FROZEN_APOCALYPSE_LEVEL_ID)) {
                ServerPlayNetworking.send(player, FrozenApocalypseNetwork.FROZEN_APOCALYPSE_LEVEL_ID, buf);
            }
        });
    }

    public static int calculateUpdateSpeed(ServerWorld serverWorld) {
        return (int) Math.ceil((Math.ceil(3.0 / serverWorld.getGameRules().getInt(FrozenApocalypseGameRules.RegisteredIntegerGameRules.WORLD_UPDATE_SPEED.getKey()) * 128) / FrozenApocalypse.apocalypseLevel.getWorldUpdateSpeed()));
    }

    public static void applyApocalypseEffects(ServerWorld serverWorld, BlockPos blockPos) {
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canLeavesDecay(), WorldEffects::setLeafDecay);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canGrassTurnToPodzol(), WorldEffects::setPodzol);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canWaterTurnToIce(), WorldEffects::setIce);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canPlaceSnow(), WorldEffects::setSnow);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canIceTurnToPackedIce(), WorldEffects::setPackedIce);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canLavaTurnToObsidian(), WorldEffects::setObsidian);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canPlaceSnowBlock(), WorldEffects::setSnowBlock);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.isWeatherDisabled(), (serverWorld1, blockPos1) -> {
            if (serverWorld.isRaining() || serverWorld.isThundering()) {
                serverWorld.setWeather(99999999, 0, false, false);
            }
        });
    }

    private static void applyEffectIfEnabled(ServerWorld serverWorld, BlockPos blockPos, boolean shouldApply, BiConsumer<ServerWorld, BlockPos> effect) {
        if (shouldApply) {
            effect.accept(serverWorld, blockPos);
        }
    }

    private static void setIce(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.WATER)) {
            serverWorld.setBlockState(blockPos.down(), Blocks.ICE.getDefaultState());
        }
    }

    private static void setPackedIce(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.WATER) || serverWorld.getBlockState(blockPos.down()).isOf(Blocks.ICE)) {
            serverWorld.setBlockState(blockPos.down(), Blocks.PACKED_ICE.getDefaultState());
        }
    }

    private static void setObsidian(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.LAVA)) {
            if (serverWorld.getBlockState(blockPos.down()).getFluidState().getLevel() > 7) {
                serverWorld.setBlockState(blockPos.down(), Blocks.OBSIDIAN.getDefaultState());
                serverWorld.syncWorldEvent(1501, blockPos.down(), 0);
            }
        }
    }

    private static void setPodzol(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.GRASS_BLOCK)) {
            serverWorld.setBlockState(blockPos.down(), Blocks.PODZOL.getDefaultState());
        }
    }

    private static void setLeafDecay(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).contains(LeavesBlock.PERSISTENT)) {
            serverWorld.removeBlock(blockPos.down(), true);
        }
    }

    private static void setSnow(ServerWorld serverWorld, BlockPos blockPos) {
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

    private static void setSnowBlock(ServerWorld serverWorld, BlockPos blockPos) {
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
