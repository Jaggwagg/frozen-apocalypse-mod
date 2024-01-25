package jaggwagg.frozen_apocalypse.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;

import java.util.Locale;

public enum ModBlocks {
    FROSTED_GRASS_BLOCK(new FrostedGrassBlock(FabricBlockSettings.create().strength(0.6f).mapColor(MapColor.DARK_AQUA).ticksRandomly().sounds(BlockSoundGroup.GRASS))),
    DEAD_GRASS_BLOCK(new DeadGrassBlock(FabricBlockSettings.create().strength(0.6f).mapColor(MapColor.DIRT_BROWN).ticksRandomly().sounds(BlockSoundGroup.GRASS))),
    PERMAFROST(new Block(FabricBlockSettings.create().strength(1.0f, 1.0f).mapColor(MapColor.DIRT_BROWN).sounds(BlockSoundGroup.GRAVEL))),
    DEAD_LEAVES(new LeavesBlock(FabricBlockSettings.create().strength(0.2f).mapColor(MapColor.DIRT_BROWN).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(net.minecraft.block.Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never))),
    ICICLE(new IcicleBlock(FabricBlockSettings.create().mapColor(MapColor.PALE_PURPLE).slipperiness(0.98f).solid().instrument(Instrument.BASEDRUM).nonOpaque().sounds(BlockSoundGroup.GLASS).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never))),
    ICY_COBWEB(new IcyCobwebBlock(FabricBlockSettings.copyOf(Blocks.COBWEB)));

    private final String id;
    private final Block block;

    <T extends Block> ModBlocks(T block) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.block = block;
    }

    public String getId() {
        return this.id;
    }

    public Block getBlock() {
        return this.block;
    }
}
