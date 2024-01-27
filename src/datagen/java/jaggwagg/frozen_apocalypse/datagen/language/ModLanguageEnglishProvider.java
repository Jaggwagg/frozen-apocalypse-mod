package jaggwagg.frozen_apocalypse.datagen.language;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.block.ModBlocks;
import jaggwagg.frozen_apocalypse.entity.ModMobEntityTypes;
import jaggwagg.frozen_apocalypse.entity.effect.ModStatusEffects;
import jaggwagg.frozen_apocalypse.item.ModItemGroups;
import jaggwagg.frozen_apocalypse.item.ModItems;
import jaggwagg.frozen_apocalypse.item.ModSpawnEggItems;
import jaggwagg.frozen_apocalypse.world.ModBooleanGameRules;
import jaggwagg.frozen_apocalypse.world.ModIntegerGameRules;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;

public class ModLanguageEnglishProvider extends FabricLanguageProvider {
    public ModLanguageEnglishProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        /* Blocks */
        Arrays.stream(ModBlocks.values()).forEach(value -> translationBuilder.add(value.getBlock(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));

        /* Items */
        Arrays.stream(ModItems.values()).forEach(value -> translationBuilder.add(value.getItem(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));
        Arrays.stream(ModSpawnEggItems.values()).forEach(value -> translationBuilder.add(value.getItem(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));

        /* Game Rule Category */
        translationBuilder.add("gamerule.category." + FrozenApocalypse.MOD_ID, "Frozen Apocalypse");

        /* Game Rules */
        Arrays.stream(ModBooleanGameRules.values()).forEach(value -> translationBuilder.add(value.getKey().getTranslationKey(), value.getDescription()));
        Arrays.stream(ModIntegerGameRules.values()).forEach(value -> translationBuilder.add(value.getKey().getTranslationKey(), value.getDescription()));

        /* Item Groups */
        Arrays.stream(ModItemGroups.values()).forEach(value -> translationBuilder.add(value.getItemGroup(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));

        /* Entity Types */
        Arrays.stream(ModMobEntityTypes.values()).forEach(value -> translationBuilder.add(value.getEntityType(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));

        /* Status effects */
        Arrays.stream(ModStatusEffects.values()).forEach(value -> translationBuilder.add(value.getStatusEffect(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));
    }
}
