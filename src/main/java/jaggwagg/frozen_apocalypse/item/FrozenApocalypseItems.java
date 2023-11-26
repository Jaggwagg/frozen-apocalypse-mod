package jaggwagg.frozen_apocalypse.item;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class FrozenApocalypseItems {
    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(FrozenApocalypse.MOD_ID, "general"));

    public static void init() {
        Arrays.stream(Armor.values()).forEach(value -> registerItem(value.item, value.name));
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(Armor.IRON_THERMAL_CHESTPLATE.item))
                .displayName(Text.translatable("group." + FrozenApocalypse.MOD_ID + ".general"))
                .build());
    }

    public enum ArmorMaterials {
        IRON_THERMAL_ARMOR(new IronThermalArmorMaterial()),
        DIAMOND_THERMAL_ARMOR(new DiamondThermalArmorMaterial()),
        NETHERITE_THERMAL_ARMOR(new NetheriteThermalArmorMaterial());

        public final String name;
        public final ArmorMaterial material;

        <T extends ArmorMaterial> ArmorMaterials(T material) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.material = material;
        }
    }

    public enum Armor {
        IRON_THERMAL_HELMET(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.HELMET, new Item.Settings())),
        IRON_THERMAL_CHESTPLATE(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.CHESTPLATE, new Item.Settings())),
        IRON_THERMAL_LEGGINGS(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.LEGGINGS, new Item.Settings())),
        IRON_THERMAL_BOOTS(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.BOOTS, new Item.Settings())),
        DIAMOND_THERMAL_HELMET(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.HELMET, new Item.Settings())),
        DIAMOND_THERMAL_CHESTPLATE(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.CHESTPLATE, new Item.Settings())),
        DIAMOND_THERMAL_LEGGINGS(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.LEGGINGS, new Item.Settings())),
        DIAMOND_THERMAL_BOOTS(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.BOOTS, new Item.Settings())),
        NETHERITE_THERMAL_HELMET(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.HELMET, new Item.Settings())),
        NETHERITE_THERMAL_CHESTPLATE(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.CHESTPLATE, new Item.Settings())),
        NETHERITE_THERMAL_LEGGINGS(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.LEGGINGS, new Item.Settings())),
        NETHERITE_THERMAL_BOOTS(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.BOOTS, new Item.Settings()));

        public final String name;
        public final Item item;

        <T extends Item> Armor(T item) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
        }
    }

    public static void registerItem(Item item, String name) {
        Registry.register(Registries.ITEM, new Identifier(FrozenApocalypse.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> content.add(item));
    }
}
