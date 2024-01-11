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
        return isWearingThermalArmorItem(livingEntity, EquipmentSlot.FEET) &&
                isWearingThermalArmorItem(livingEntity, EquipmentSlot.LEGS) &&
                isWearingThermalArmorItem(livingEntity, EquipmentSlot.CHEST) &&
                isWearingThermalArmorItem(livingEntity, EquipmentSlot.HEAD);
    }

    private static boolean isWearingThermalArmorItem(LivingEntity livingEntity, EquipmentSlot slot) {
        return livingEntity.getEquippedStack(slot).getItem() instanceof ThermalArmorItem;
    }
}
