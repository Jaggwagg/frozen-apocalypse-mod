package jaggwagg.frozen_apocalypse.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public class ThermalArmorItem extends ArmorItem {
    public ThermalArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public static boolean wearingThermalArmor(LivingEntity livingEntity) {
        boolean wearingThermalBoots = false;
        boolean wearingThermalLeggings = false;
        boolean wearingThermalChestplate = false;
        boolean wearingThermalHelmet = false;

        if (livingEntity.getEquippedStack(EquipmentSlot.FEET).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_BOOTS.item.getClass()) {
            wearingThermalBoots = true;
        }

        if (livingEntity.getEquippedStack(EquipmentSlot.LEGS).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_LEGGINGS.item.getClass()) {
            wearingThermalLeggings = true;
        }

        if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_CHESTPLATE.item.getClass()) {
            wearingThermalChestplate = true;
        }

        if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_HELMET.item.getClass()) {
            wearingThermalHelmet = true;
        }

        return wearingThermalBoots && wearingThermalLeggings && wearingThermalChestplate && wearingThermalHelmet;
    }
}
