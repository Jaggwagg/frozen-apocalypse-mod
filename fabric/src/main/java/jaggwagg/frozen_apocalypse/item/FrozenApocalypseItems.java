package jaggwagg.frozen_apocalypse.item;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class FrozenApocalypseItems {
    public static void init() {
        Arrays.stream(Armor.values()).forEach(value -> registerItem(value.item, value.name));
    }

    public enum ArmorMaterials {
        THERMAL_ARMOR(new ThermalArmorMaterial());

        public final String name;
        public final ArmorMaterial material;

        <T extends ArmorMaterial> ArmorMaterials(T material) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.material = material;
        }
    }

    public enum Armor {
        THERMAL_HELMET(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, ArmorItem.Type.HELMET, new Item.Settings())),
        THERMAL_CHESTPLATE(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, ArmorItem.Type.CHESTPLATE, new Item.Settings())),
        THERMAL_LEGGINGS(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, ArmorItem.Type.LEGGINGS, new Item.Settings())),
        THERMAL_BOOTS(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, ArmorItem.Type.BOOTS, new Item.Settings()));

        public final String name;
        public final Item item;

        <T extends Item> Armor(T item) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
        }
    }

    public static void registerItem(Item item, String name) {
        Registry.register(Registries.ITEM, new Identifier(FrozenApocalypse.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(FrozenApocalypse.ITEM_GROUP).register(content -> content.add(item));
    }
}
