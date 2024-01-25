package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.ModMobEntityTypes;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterEntityTypes {
    public static void init() {
        Arrays.stream(ModMobEntityTypes.values()).forEach(value -> registerEntity(value.getId(), value.getEntityType(), value.getEntityAttributes()));
    }

    private static void registerEntity(String id, EntityType<? extends LivingEntity> entityType, DefaultAttributeContainer attributes) {
        Registry.register(Registries.ENTITY_TYPE, new Identifier(FrozenApocalypse.MOD_ID, id), entityType);
        FabricDefaultAttributeRegistry.register(entityType, attributes);
    }
}
