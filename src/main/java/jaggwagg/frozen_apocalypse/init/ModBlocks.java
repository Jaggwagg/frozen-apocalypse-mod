package jaggwagg.frozen_apocalypse.init;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.block.DeadGrassBlock;
import jaggwagg.frozen_apocalypse.block.FrostedGrassBlock;
import jaggwagg.frozen_apocalypse.block.IcicleBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;

public class ModBlocks {
    public static void init() {
        StringJoiner joiner = new StringJoiner(", ");

        Arrays.stream(RegisteredBlocks.values()).forEach(value -> {
            registerBlock(value.getBlock(), value.getName());
            joiner.add(value.getName());
        });

        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Initialized blocks: " + joiner);
    }

    public static void registerBlock(Block block, String name) {
        Registry.register(Registries.BLOCK, new Identifier(FrozenApocalypse.MOD_ID, name), block);
        BlockItem item = Registry.register(Registries.ITEM, new Identifier(FrozenApocalypse.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(ModItems.ITEM_GROUP).register(content -> content.add(item));
    }

    public enum RegisteredBlocks {
        FROSTED_GRASS_BLOCK(new FrostedGrassBlock(FabricBlockSettings.create().strength(0.6f).mapColor(MapColor.DARK_AQUA).ticksRandomly().sounds(BlockSoundGroup.GRASS))),
        DEAD_GRASS_BLOCK(new DeadGrassBlock(FabricBlockSettings.create().strength(0.6f).mapColor(MapColor.DIRT_BROWN).ticksRandomly().sounds(BlockSoundGroup.GRASS))),
        PERMAFROST(new Block(FabricBlockSettings.create().strength(1.0f, 1.0f).mapColor(MapColor.DIRT_BROWN).sounds(BlockSoundGroup.GRAVEL))),
        DEAD_LEAVES(new LeavesBlock(FabricBlockSettings.create().strength(0.2f).mapColor(MapColor.DIRT_BROWN).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(net.minecraft.block.Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never))),
        ICICLE(new IcicleBlock(FabricBlockSettings.create().mapColor(MapColor.PALE_PURPLE).slipperiness(0.98f).solid().instrument(Instrument.BASEDRUM).nonOpaque().sounds(BlockSoundGroup.GLASS).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));

        private final String name;
        private final Block block;

        <T extends Block> RegisteredBlocks(T block) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.block = block;
        }

        public String getName() {
            return this.name;
        }

        public Block getBlock() {
            return this.block;
        }
    }
}
