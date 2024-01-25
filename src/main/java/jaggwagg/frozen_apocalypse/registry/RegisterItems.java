package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.item.ModItemGroups;
import jaggwagg.frozen_apocalypse.item.ModItems;
import jaggwagg.frozen_apocalypse.item.ModSpawnEggItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterItems {
    public static void init() {
        RegistryKey<ItemGroup> modItemGroupGeneral = ModItemGroups.FROZEN_APOCALYPSE.getItemGroup();

        Arrays.stream(ModItems.values()).forEach(value -> registerItem(value.getId(), value.getItem(), modItemGroupGeneral));
        Arrays.stream(ModSpawnEggItems.values()).forEach(value -> registerItem(value.getId(), value.getItem(), modItemGroupGeneral));
    }

    private static void registerItem(String id, Item item, RegistryKey<ItemGroup> itemGroup) {
        Registry.register(Registries.ITEM, new Identifier(FrozenApocalypse.MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(item));
    }
}
