package jaggwagg.frozen_apocalypse.apocalypse;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.block.IcicleBlock;
import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import jaggwagg.frozen_apocalypse.network.ModNetwork;
import jaggwagg.frozen_apocalypse.registry.ModBlocks;
import jaggwagg.frozen_apocalypse.registry.ModGameRules;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

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
            if (apocalypseLevel.getApocalypseLevel() == Math.max(0, serverWorld.getGameRules().getInt(ModGameRules.RegisteredIntegerGameRules.FROZEN_APOCALYPSE_LEVEL.getKey()))) {
                FrozenApocalypse.apocalypseLevel = apocalypseLevel;
            }
        }
    }

    public static void updateFrozenApocalypseLevel(ServerWorld serverWorld) {
        if (serverWorld.getGameRules().get(ModGameRules.RegisteredBooleanGameRules.FROZEN_APOCALYPSE_LEVEL_UPDATES_EACH_DAY.getKey()).get()) {
            int maxStartingDay = 0;

            for (ApocalypseLevel apocalypseLevel : FrozenApocalypse.CONFIG.getApocalypseLevels()) {
                if (apocalypseLevel.getStartingDay() <= calculateDay(serverWorld) && apocalypseLevel.getStartingDay() >= maxStartingDay) {
                    serverWorld.getGameRules().get(ModGameRules.RegisteredIntegerGameRules.FROZEN_APOCALYPSE_LEVEL.getKey()).set(apocalypseLevel.getApocalypseLevel(), serverWorld.getServer());
                    maxStartingDay = apocalypseLevel.getStartingDay();
                }
            }
        }
    }

    public static void sendFrozenApocalypseLevelToPlayers(ServerWorld serverWorld) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(FrozenApocalypse.apocalypseLevel.getApocalypseLevel());

        serverWorld.getPlayers().forEach(player -> {
            if (ServerPlayNetworking.canSend(player, ModNetwork.FROZEN_APOCALYPSE_LEVEL_ID)) {
                ServerPlayNetworking.send(player, ModNetwork.FROZEN_APOCALYPSE_LEVEL_ID, buf);
            }
        });
    }

    public static int calculateUpdateSpeed(ServerWorld serverWorld) {
        return (int) Math.ceil((Math.ceil(3.0 / serverWorld.getGameRules().getInt(ModGameRules.RegisteredIntegerGameRules.FROZEN_APOCALYPSE_WORLD_UPDATE_SPEED.getKey()) * 512) / FrozenApocalypse.apocalypseLevel.getWorldUpdateSpeed()));
    }

    public static void applyApocalypseEffects(ServerWorld serverWorld, BlockPos blockPos) {
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canGrassTurnToFrostedGrass(), WorldEffects::setFrostedGrass);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canWaterTurnToIce(), WorldEffects::setIce);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canPlaceSnow(), WorldEffects::setSnow);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canPlaceIcicles(), WorldEffects::setIcicle);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canFrostedGrassTurnToDeadGrass(), WorldEffects::setDeadGrass);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canLeavesTurnToDeadLeaves(), WorldEffects::setDeadLeaves);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canIceTurnToPackedIce(), WorldEffects::setPackedIce);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canLavaTurnToObsidian(), WorldEffects::setObsidian);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canPlaceSnowBlock(), WorldEffects::setSnowBlock);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canDeadGrassTurnToPermafrost(), WorldEffects::setPermafrost);
        applyEffectIfEnabled(serverWorld, blockPos, FrozenApocalypse.apocalypseLevel.canLeavesDecay(), WorldEffects::setLeafDecay);
    }

    private static void applyEffectIfEnabled(ServerWorld serverWorld, BlockPos blockPos, boolean shouldApply, BiConsumer<ServerWorld, BlockPos> effect) {
        if (shouldApply) {
            effect.accept(serverWorld, blockPos);
        }
    }

    private static void setFrostedGrass(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.GRASS_BLOCK)) {
            BlockState blockState = serverWorld.getBlockState(blockPos.down());

            if (!(serverWorld.getBlockState(blockPos).isOf(Blocks.SNOW) || serverWorld.getBlockState(blockPos).isOf(Blocks.SNOW_BLOCK) || serverWorld.getBlockState(blockPos).isOf(Blocks.POWDER_SNOW))) {
                serverWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }

            if (FrozenApocalypse.CONFIG.isPlacingCustomBlocksEnabled()) {
                serverWorld.setBlockState(blockPos.down(), ModBlocks.RegisteredBlocks.FROSTED_GRASS_BLOCK.getBlock().getStateWithProperties(blockState));
            } else {
                serverWorld.setBlockState(blockPos.down(), Blocks.PODZOL.getDefaultState());
            }
        }
    }

    private static void setIce(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(Blocks.WATER)) {
            serverWorld.setBlockState(blockPos.down(), Blocks.ICE.getDefaultState());
        }
    }

    private static void setSnow(ServerWorld serverWorld, BlockPos blockPos) {
        BlockState belowBlockState = serverWorld.getBlockState(blockPos.down());

        if (!(serverWorld.isRaining() || serverWorld.isThundering())) {
            return;
        }

        if (belowBlockState.contains(FluidBlock.LEVEL)) {
            return;
        }

        if (belowBlockState.contains(LeavesBlock.PERSISTENT)) {
            return;
        }

        if (belowBlockState.isOf(Blocks.ICE) || belowBlockState.isOf(Blocks.PACKED_ICE) || belowBlockState.isOf(Blocks.AIR) || !belowBlockState.isOpaque()) {
            return;
        }

        serverWorld.setBlockState(blockPos, Blocks.SNOW.getDefaultState());
    }

    private static void setIcicle(ServerWorld serverWorld, BlockPos blockPos) {
        if (!FrozenApocalypse.CONFIG.isPlacingCustomBlocksEnabled()) {
            return;
        }

        if (!(serverWorld.isRaining() || serverWorld.isThundering())) {
            return;
        }

        for (int y = 0; y < 5; y++) {
            if (serverWorld.getBlockState(blockPos.down(y)).isAir() && serverWorld.getRandom().nextInt(4) == 0 && serverWorld.getBlockState(blockPos.down(y - 1)).isOpaque()) {
                serverWorld.setBlockState(blockPos.down(y), ModBlocks.RegisteredBlocks.ICICLE.getBlock().getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN).with(IcicleBlock.GROWTH, serverWorld.getRandom().nextInt(2)));
                break;
            }
        }
    }

    private static void setDeadGrass(ServerWorld serverWorld, BlockPos blockPos) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        BlockState blockStateDown = serverWorld.getBlockState(blockPos.down());

        if (blockStateDown.isOf(ModBlocks.RegisteredBlocks.FROSTED_GRASS_BLOCK.getBlock())) {
            if (!(blockState.isOf(Blocks.SNOW) || blockState.isOf(Blocks.SNOW_BLOCK) || blockState.isOf(Blocks.POWDER_SNOW))) {
                serverWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }

            serverWorld.setBlockState(blockPos.down(), ModBlocks.RegisteredBlocks.DEAD_GRASS_BLOCK.getBlock().getStateWithProperties(blockStateDown));
        }
    }

    private static void setDeadLeaves(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).getBlock() instanceof LeavesBlock) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = -3; y < 1; y++) {
                        BlockPos currentBlockPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);

                        if (serverWorld.getBlockState(currentBlockPos).getBlock() instanceof LeavesBlock) {
                            if (!serverWorld.getBlockState(currentBlockPos).isOf(ModBlocks.RegisteredBlocks.DEAD_LEAVES.getBlock())) {
                                BlockState blockState = serverWorld.getBlockState(currentBlockPos);
                                serverWorld.setBlockState(currentBlockPos, ModBlocks.RegisteredBlocks.DEAD_LEAVES.getBlock().getStateWithProperties(blockState));
                            }
                        }
                    }
                }
            }
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

    private static void setSnowBlock(ServerWorld serverWorld, BlockPos blockPos) {
        BlockState blockState = serverWorld.getBlockState(blockPos.down());

        if (!(serverWorld.isRaining() || serverWorld.isThundering())) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).contains(FluidBlock.LEVEL)) {
            return;
        }

        if (serverWorld.getBlockState(blockPos.down()).contains(LeavesBlock.PERSISTENT)) {
            return;
        }

        if (blockState.isOf(Blocks.SNOW_BLOCK) || blockState.isOf(Blocks.AIR)) {
            return;
        }

        serverWorld.setBlockState(blockPos, Blocks.SNOW_BLOCK.getDefaultState());
    }

    private static void setPermafrost(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).isOf(ModBlocks.RegisteredBlocks.DEAD_GRASS_BLOCK.getBlock())) {
            serverWorld.setBlockState(blockPos.down(), ModBlocks.RegisteredBlocks.PERMAFROST.getBlock().getDefaultState());
        }
    }

    private static void setLeafDecay(ServerWorld serverWorld, BlockPos blockPos) {
        if (serverWorld.getBlockState(blockPos.down()).getBlock() instanceof LeavesBlock || serverWorld.getBlockState(blockPos.down()).isOf(Blocks.VINE)) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = -3; y < 1; y++) {
                        BlockPos currentBlockPos = new BlockPos(blockPos.getX() + x, blockPos.down().getY() + y, blockPos.getZ() + z);

                        if (serverWorld.getBlockState(currentBlockPos).getBlock() instanceof LeavesBlock) {
                            serverWorld.removeBlock(currentBlockPos, true);
                        }
                    }
                }
            }
        }
    }
}
