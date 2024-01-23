package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class ModItems {
    public static void init() {
        Arrays.stream(RegisteredItems.values()).forEach(value -> registerItem(value.getId(), value.getItem()));
        Arrays.stream(RegisteredSpawnEggs.values()).forEach(value -> registerItem(value.getId(), value.getItem()));

        FrozenApocalypse.loggerInfo("Initialized items");
    }

    public static void registerItem(String id, Item item) {
        Registry.register(Registries.ITEM, new Identifier(FrozenApocalypse.MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.RegisteredItemGroups.GENERAL.getItemGroup()).register(content -> content.add(item));
    }

    public enum RegisteredArmorMaterials {
        GOLD_THERMAL_ARMOR(new GoldThermalArmorMaterial()),
        IRON_THERMAL_ARMOR(new IronThermalArmorMaterial()),
        DIAMOND_THERMAL_ARMOR(new DiamondThermalArmorMaterial()),
        NETHERITE_THERMAL_ARMOR(new NetheriteThermalArmorMaterial());

        private final ArmorMaterial material;

        <T extends ArmorMaterial> RegisteredArmorMaterials(T material) {
            this.material = material;
        }

        public ArmorMaterial getMaterial() {
            return material;
        }
    }

    public enum RegisteredItems {
        DIAMOND_THERMAL_HORSE_ARMOR(new ThermalHorseArmorItem(11, "diamond_thermal", new FabricItemSettings().maxCount(1))),
        DIAMOND_THERMAL_HELMET(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        DIAMOND_THERMAL_CHESTPLATE(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        DIAMOND_THERMAL_LEGGINGS(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        DIAMOND_THERMAL_BOOTS(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
        GOLDEN_THERMAL_HORSE_ARMOR(new ThermalHorseArmorItem(7, "golden_thermal", new FabricItemSettings().maxCount(1))),
        GOLDEN_THERMAL_HELMET(new ThermalArmorItem(RegisteredArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        GOLDEN_THERMAL_CHESTPLATE(new ThermalArmorItem(RegisteredArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        GOLDEN_THERMAL_LEGGINGS(new ThermalArmorItem(RegisteredArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        GOLDEN_THERMAL_BOOTS(new ThermalArmorItem(RegisteredArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
        IRON_THERMAL_HORSE_ARMOR(new ThermalHorseArmorItem(5, "iron_thermal", new FabricItemSettings().maxCount(1))),
        IRON_THERMAL_HELMET(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        IRON_THERMAL_CHESTPLATE(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        IRON_THERMAL_LEGGINGS(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        IRON_THERMAL_BOOTS(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
        NETHERITE_THERMAL_HELMET(new ThermalArmorItem(RegisteredArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        NETHERITE_THERMAL_CHESTPLATE(new ThermalArmorItem(RegisteredArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        NETHERITE_THERMAL_LEGGINGS(new ThermalArmorItem(RegisteredArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        NETHERITE_THERMAL_BOOTS(new ThermalArmorItem(RegisteredArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings()));

        private final String id;
        private final Item item;

        <T extends Item> RegisteredItems(T item) {
            this.id = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
        }

        public String getId() {
            return this.id;
        }

        public Item getItem() {
            return this.item;
        }
    }

    public enum RegisteredSpawnEggs {
        CRYOBOOMER_SPAWN_EGG(new SpawnEggItem(ModEntityTypes.RegisteredMobEntityTypes.CRYOBOOMER.getEntityType(), 0x0ba6a7, 0x000000, new FabricItemSettings())),
        FROSTBITE_SPAWN_EGG(new SpawnEggItem(ModEntityTypes.RegisteredMobEntityTypes.FROSTBITE.getEntityType(), 0x0b67a7, 0x659c91, new FabricItemSettings())),
        ICEWEAVER_SPAWN_EGG(new SpawnEggItem(ModEntityTypes.RegisteredMobEntityTypes.ICEWEAVER.getEntityType(), 0x2e3c3e, 0x910c3e, new FabricItemSettings())),
        SHIVERSTARE_SPAWN_EGG(new SpawnEggItem(ModEntityTypes.RegisteredMobEntityTypes.SHIVERSTARE.getEntityType(), 0x001817, 0x000000, new FabricItemSettings()));

        private final String id;
        private final Item item;

        <T extends SpawnEggItem> RegisteredSpawnEggs(T item) {
            this.id = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
        }

        public String getId() {
            return this.id;
        }

        public Item getItem() {
            return this.item;
        }
    }
}
