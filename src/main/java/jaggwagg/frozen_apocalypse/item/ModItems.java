package jaggwagg.frozen_apocalypse.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

import java.util.Locale;

public enum ModItems {
    DIAMOND_THERMAL_HORSE_ARMOR(new ThermalHorseArmorItem(11, "diamond_thermal", new FabricItemSettings().maxCount(1))),
    DIAMOND_THERMAL_HELMET(new ThermalArmorItem(ModArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
    DIAMOND_THERMAL_CHESTPLATE(new ThermalArmorItem(ModArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
    DIAMOND_THERMAL_LEGGINGS(new ThermalArmorItem(ModArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
    DIAMOND_THERMAL_BOOTS(new ThermalArmorItem(ModArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
    GOLDEN_THERMAL_HORSE_ARMOR(new ThermalHorseArmorItem(7, "golden_thermal", new FabricItemSettings().maxCount(1))),
    GOLDEN_THERMAL_HELMET(new ThermalArmorItem(ModArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
    GOLDEN_THERMAL_CHESTPLATE(new ThermalArmorItem(ModArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
    GOLDEN_THERMAL_LEGGINGS(new ThermalArmorItem(ModArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
    GOLDEN_THERMAL_BOOTS(new ThermalArmorItem(ModArmorMaterials.GOLD_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
    IRON_THERMAL_HORSE_ARMOR(new ThermalHorseArmorItem(5, "iron_thermal", new FabricItemSettings().maxCount(1))),
    IRON_THERMAL_HELMET(new ThermalArmorItem(ModArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
    IRON_THERMAL_CHESTPLATE(new ThermalArmorItem(ModArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
    IRON_THERMAL_LEGGINGS(new ThermalArmorItem(ModArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
    IRON_THERMAL_BOOTS(new ThermalArmorItem(ModArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
    NETHERITE_THERMAL_HELMET(new ThermalArmorItem(ModArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
    NETHERITE_THERMAL_CHESTPLATE(new ThermalArmorItem(ModArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
    NETHERITE_THERMAL_LEGGINGS(new ThermalArmorItem(ModArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
    NETHERITE_THERMAL_BOOTS(new ThermalArmorItem(ModArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings()));

    private final String id;
    private final Item item;

    <T extends Item> ModItems(T item) {
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
