package jaggwagg.frozen_apocalypse.item;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class FrozenApocalypseItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(FrozenApocalypse.MOD_ID, "general"))
            .icon(() -> new ItemStack(FrozenApocalypseItems.Armor.IRON_THERMAL_CHESTPLATE.item))
            .build();

    public static void init() {
        Arrays.stream(Armor.values()).forEach(value -> registerItem(value.item, value.name));
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
        IRON_THERMAL_HELMET(new ArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.HELMET, new Item.Settings())),
        IRON_THERMAL_CHESTPLATE(new ArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.CHESTPLATE, new Item.Settings())),
        IRON_THERMAL_LEGGINGS(new ArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.LEGGINGS, new Item.Settings())),
        IRON_THERMAL_BOOTS(new ArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.material, ArmorItem.Type.BOOTS, new Item.Settings())),
        DIAMOND_THERMAL_HELMET(new ArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.HELMET, new Item.Settings())),
        DIAMOND_THERMAL_CHESTPLATE(new ArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.CHESTPLATE, new Item.Settings())),
        DIAMOND_THERMAL_LEGGINGS(new ArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.LEGGINGS, new Item.Settings())),
        DIAMOND_THERMAL_BOOTS(new ArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.material, ArmorItem.Type.BOOTS, new Item.Settings())),
        NETHERITE_THERMAL_HELMET(new ArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.HELMET, new Item.Settings())),
        NETHERITE_THERMAL_CHESTPLATE(new ArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.CHESTPLATE, new Item.Settings())),
        NETHERITE_THERMAL_LEGGINGS(new ArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.LEGGINGS, new Item.Settings())),
        NETHERITE_THERMAL_BOOTS(new ArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.material, ArmorItem.Type.BOOTS, new Item.Settings()));

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
