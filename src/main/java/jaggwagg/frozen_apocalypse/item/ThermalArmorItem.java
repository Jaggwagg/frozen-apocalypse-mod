package jaggwagg.frozen_apocalypse.item;

import jaggwagg.frozen_apocalypse.registry.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;

public class ThermalArmorItem extends ArmorItem {
    public ThermalArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public static boolean wearingThermalArmor(LivingEntity livingEntity) {
        Item thermalBoots = ModItems.RegisteredItems.IRON_THERMAL_BOOTS.getItem();
        Item thermalLeggings = ModItems.RegisteredItems.IRON_THERMAL_LEGGINGS.getItem();
        Item thermalChestplate = ModItems.RegisteredItems.IRON_THERMAL_CHESTPLATE.getItem();
        Item thermalHelmet = ModItems.RegisteredItems.IRON_THERMAL_HELMET.getItem();

        return isWearingItem(livingEntity, EquipmentSlot.FEET, thermalBoots) &&
                isWearingItem(livingEntity, EquipmentSlot.LEGS, thermalLeggings) &&
                isWearingItem(livingEntity, EquipmentSlot.CHEST, thermalChestplate) &&
                isWearingItem(livingEntity, EquipmentSlot.HEAD, thermalHelmet);
    }

    private static boolean isWearingItem(LivingEntity livingEntity, EquipmentSlot slot, Item item) {
        return livingEntity.getEquippedStack(slot).getItem() == item;
    }
}
