package jaggwagg.frozen_apocalypse.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class ThermalArmorItem extends ArmorItem {
    public ThermalArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public static boolean wearingThermalArmor(PlayerEntity playerEntity) {
        DefaultedList<ItemStack> playerArmor = playerEntity.getInventory().armor;
        boolean wearingThermalBoots = false;
        boolean wearingThermalLeggings = false;
        boolean wearingThermalChestplate = false;
        boolean wearingThermalHelmet = false;

        if (playerArmor.get(0).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_BOOTS.item.getClass()) {
            wearingThermalBoots = true;
        }

        if (playerArmor.get(1).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_LEGGINGS.item.getClass()) {
            wearingThermalLeggings = true;
        }

        if (playerArmor.get(2).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_CHESTPLATE.item.getClass()) {
            wearingThermalChestplate = true;
        }

        if (playerArmor.get(3).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_HELMET.item.getClass()) {
            wearingThermalHelmet = true;
        }

        return wearingThermalBoots && wearingThermalLeggings && wearingThermalChestplate && wearingThermalHelmet;
    }
}
