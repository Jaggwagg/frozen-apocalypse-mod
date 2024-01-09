package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.item.DiamondThermalArmorMaterial;
import jaggwagg.frozen_apocalypse.item.IronThermalArmorMaterial;
import jaggwagg.frozen_apocalypse.item.NetheriteThermalArmorMaterial;
import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class ModItems {
    public static void init() {
        Arrays.stream(RegisteredItems.values()).forEach(value -> registerItem(value.getId(), value.getItem()));

        FrozenApocalypse.loggerInfo("Initialized items");
    }

    public static void registerItem(String id, Item item) {
        Registry.register(Registries.ITEM, new Identifier(FrozenApocalypse.MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.RegisteredItemGroups.GENERAL.getItemGroup()).register(content -> content.add(item));
    }

    public enum RegisteredArmorMaterials {
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
        IRON_THERMAL_HELMET(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        IRON_THERMAL_CHESTPLATE(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        IRON_THERMAL_LEGGINGS(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        IRON_THERMAL_BOOTS(new ThermalArmorItem(RegisteredArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
        DIAMOND_THERMAL_HELMET(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        DIAMOND_THERMAL_CHESTPLATE(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        DIAMOND_THERMAL_LEGGINGS(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        DIAMOND_THERMAL_BOOTS(new ThermalArmorItem(RegisteredArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
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
}
