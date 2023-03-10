package jaggwagg.frozen_apocalypse.item;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Locale;

public class FrozenApocalypseItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrozenApocalypse.MOD_ID);

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
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
        THERMAL_HELMET(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, EquipmentSlot.HEAD, new Item.Properties())),
        THERMAL_CHESTPLATE(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, EquipmentSlot.CHEST, new Item.Properties())),
        THERMAL_LEGGINGS(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, EquipmentSlot.LEGS, new Item.Properties())),
        THERMAL_BOOTS(new ArmorItem(ArmorMaterials.THERMAL_ARMOR.material, EquipmentSlot.FEET, new Item.Properties()));

        public final String name;
        public final RegistryObject<Item> item;

        <T extends Item> Armor(T item) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.item = ITEMS.register(name, () -> item);
        }
    }
}
