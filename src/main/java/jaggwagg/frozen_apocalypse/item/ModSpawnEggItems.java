package jaggwagg.frozen_apocalypse.item;

import jaggwagg.frozen_apocalypse.entity.ModMobEntityTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

import java.util.Locale;

public enum ModSpawnEggItems {
    CRYOBOOMER_SPAWN_EGG(new SpawnEggItem(ModMobEntityTypes.CRYOBOOMER.getEntityType(), 0x0ba6a7, 0x000000, new FabricItemSettings())),
    FROSTBITE_SPAWN_EGG(new SpawnEggItem(ModMobEntityTypes.FROSTBITE.getEntityType(), 0x0b67a7, 0x659c91, new FabricItemSettings())),
    ICEWEAVER_SPAWN_EGG(new SpawnEggItem(ModMobEntityTypes.ICEWEAVER.getEntityType(), 0x2e3c3e, 0x910c3e, new FabricItemSettings())),
    SHIVERSTARE_SPAWN_EGG(new SpawnEggItem(ModMobEntityTypes.SHIVERSTARE.getEntityType(), 0x001817, 0x000000, new FabricItemSettings()));

    private final String id;
    private final Item item;

    <T extends SpawnEggItem> ModSpawnEggItems(T item) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.item = item;
    }

    public String getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item;
    }
}
