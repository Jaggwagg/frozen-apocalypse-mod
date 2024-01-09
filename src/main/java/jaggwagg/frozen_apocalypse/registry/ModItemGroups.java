package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class ModItemGroups {
    public static void init() {
        Arrays.stream(RegisteredItemGroups.values()).forEach(value -> registerItemGroup(value.getId(), value.getItemGroupItem(), value.getItemGroup()));

        FrozenApocalypse.loggerInfo("Initialized item groups");
    }

    private static void registerItemGroup(String id, Item item, RegistryKey<ItemGroup> itemGroup) {
        Registry.register(Registries.ITEM_GROUP, itemGroup, FabricItemGroup.builder()
                .icon(() -> new ItemStack(item))
                .displayName(Text.translatable("group." + FrozenApocalypse.MOD_ID + "." + id))
                .build());
    }

    public enum RegisteredItemGroups {
        GENERAL(ModItems.RegisteredItems.IRON_THERMAL_CHESTPLATE.getItem());

        private final String id;
        private final Item itemGroupItem;
        private final RegistryKey<ItemGroup> itemGroup;

        RegisteredItemGroups(Item itemGroupItem) {
            this.id = this.toString().toLowerCase(Locale.ROOT);
            this.itemGroupItem = itemGroupItem;
            this.itemGroup = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(this.id));
        }

        public String getId() {
            return this.id;
        }

        public Item getItemGroupItem() {
            return this.itemGroupItem;
        }

        public RegistryKey<ItemGroup> getItemGroup() {
            return this.itemGroup;
        }
    }
}
