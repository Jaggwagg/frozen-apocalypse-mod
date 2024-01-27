package jaggwagg.frozen_apocalypse.datagen;

import jaggwagg.frozen_apocalypse.datagen.language.ModLanguageEnglishProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FrozenApocalypseDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModLanguageEnglishProvider::new);
    }
}
