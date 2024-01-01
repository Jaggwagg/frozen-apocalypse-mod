package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.item.DiamondThermalArmorMaterial;
import jaggwagg.frozen_apocalypse.item.IronThermalArmorMaterial;
import jaggwagg.frozen_apocalypse.item.NetheriteThermalArmorMaterial;
import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
import java.util.StringJoiner;

public class FrozenApocalypseItems {
    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(FrozenApocalypse.MOD_ID, "general"));

    public static void init() {
        StringJoiner joiner = new StringJoiner(", ");

        Arrays.stream(Items.values()).forEach(value -> {
            registerItem(value.getItem(), value.getName());
            joiner.add(value.name());
        });
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(Items.IRON_THERMAL_CHESTPLATE.item))
                .displayName(Text.translatable("group." + FrozenApocalypse.MOD_ID + ".general"))
                .build());

        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Initialized items: " + joiner);
    }

    public static void registerItem(Item item, String name) {
        Registry.register(Registries.ITEM, new Identifier(FrozenApocalypse.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> content.add(item));
    }

    public enum ArmorMaterials {
        IRON_THERMAL_ARMOR(new IronThermalArmorMaterial()),
        DIAMOND_THERMAL_ARMOR(new DiamondThermalArmorMaterial()),
        NETHERITE_THERMAL_ARMOR(new NetheriteThermalArmorMaterial());

        private final ArmorMaterial material;

        <T extends ArmorMaterial> ArmorMaterials(T material) {
            this.material = material;
        }

        public ArmorMaterial getMaterial() {
            return material;
        }
    }

    public enum Items {
        IRON_THERMAL_HELMET(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        IRON_THERMAL_CHESTPLATE(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        IRON_THERMAL_LEGGINGS(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        IRON_THERMAL_BOOTS(new ThermalArmorItem(ArmorMaterials.IRON_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
        DIAMOND_THERMAL_HELMET(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        DIAMOND_THERMAL_CHESTPLATE(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        DIAMOND_THERMAL_LEGGINGS(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        DIAMOND_THERMAL_BOOTS(new ThermalArmorItem(ArmorMaterials.DIAMOND_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings())),
        NETHERITE_THERMAL_HELMET(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.HELMET, new FabricItemSettings())),
        NETHERITE_THERMAL_CHESTPLATE(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.CHESTPLATE, new FabricItemSettings())),
        NETHERITE_THERMAL_LEGGINGS(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.LEGGINGS, new FabricItemSettings())),
        NETHERITE_THERMAL_BOOTS(new ThermalArmorItem(ArmorMaterials.NETHERITE_THERMAL_ARMOR.getMaterial(), ArmorItem.Type.BOOTS, new FabricItemSettings()));

        private final String name;
        private final Item item;

        <T extends Item> Items(T item) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
        }

        public String getName() {
            return this.name;
        }

        public Item getItem() {
            return this.item;
        }
    }
}
