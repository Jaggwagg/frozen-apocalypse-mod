package jaggwagg.frozen_apocalypse.item;

import net.minecraft.item.ArmorMaterial;

public enum ModArmorMaterials {
    GOLD_THERMAL_ARMOR(new GoldThermalArmorMaterial()),
    IRON_THERMAL_ARMOR(new IronThermalArmorMaterial()),
    DIAMOND_THERMAL_ARMOR(new DiamondThermalArmorMaterial()),
    NETHERITE_THERMAL_ARMOR(new NetheriteThermalArmorMaterial());

    private final ArmorMaterial material;

    <T extends ArmorMaterial> ModArmorMaterials(T material) {
        this.material = material;
    }

    public ArmorMaterial getMaterial() {
        return material;
    }
}
