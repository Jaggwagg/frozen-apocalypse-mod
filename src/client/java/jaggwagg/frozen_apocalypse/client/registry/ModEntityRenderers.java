package jaggwagg.frozen_apocalypse.client.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.client.render.entity.CryoboomerEntityRenderer;
import jaggwagg.frozen_apocalypse.client.render.entity.FrostbiteEntityRenderer;
import jaggwagg.frozen_apocalypse.client.render.entity.IceweaverEntityRenderer;
import jaggwagg.frozen_apocalypse.client.render.entity.ShiverstareEntityRenderer;
import jaggwagg.frozen_apocalypse.entity.ModMobEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Arrays;

public class ModEntityRenderers {
    public static void init() {
        Arrays.stream(RegisteredEntityRenderers.values()).forEach(value -> registerEntityRenderers(value.getEntityType(), value.getEntityRendererFactory()));

        FrozenApocalypse.loggerInfo("Initialized entity types");
    }

    /*
     * Suppressing multiple warnings here.
     * Must be done in order to allow multiple entity types within enums.
     * Works every time, even on Forgified Fabric API with Sinytra Connector.
     * Why must Java not have generic enums :(
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void registerEntityRenderers(EntityType entityType, EntityRendererFactory entityRendererFactory) {
        EntityRendererRegistry.register(entityType, entityRendererFactory);
    }

    public enum RegisteredEntityRenderers {
        CRYOBOOMER_ENTITY_RENDERER(ModMobEntityTypes.CRYOBOOMER.getEntityType(), CryoboomerEntityRenderer::new),
        FROSTBITE_ENTITY_RENDERER(ModMobEntityTypes.FROSTBITE.getEntityType(), FrostbiteEntityRenderer::new),
        ICEWEAVER_ENTITY_RENDERER(ModMobEntityTypes.ICEWEAVER.getEntityType(), IceweaverEntityRenderer::new),
        SHIVERSTARE_ENTITY_RENDERER(ModMobEntityTypes.SHIVERSTARE.getEntityType(), ShiverstareEntityRenderer::new);

        private final EntityType<?> entityType;
        private final EntityRendererFactory<?> entityRendererFactory;

        <T extends Entity> RegisteredEntityRenderers(EntityType<?> entityType, EntityRendererFactory<T> entityRendererFactory) {
            this.entityType = entityType;
            this.entityRendererFactory = entityRendererFactory;
        }

        public EntityType<?> getEntityType() {
            return this.entityType;
        }

        public EntityRendererFactory<?> getEntityRendererFactory() {
            return this.entityRendererFactory;
        }
    }
}
