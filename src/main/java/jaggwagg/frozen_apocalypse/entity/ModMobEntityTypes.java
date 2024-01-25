package jaggwagg.frozen_apocalypse.entity;

import jaggwagg.frozen_apocalypse.entity.mob.CryoboomerEntity;
import jaggwagg.frozen_apocalypse.entity.mob.FrostbiteEntity;
import jaggwagg.frozen_apocalypse.entity.mob.IceweaverEntity;
import jaggwagg.frozen_apocalypse.entity.mob.ShiverstareEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;

import java.util.Locale;

public enum ModMobEntityTypes {
    CRYOBOOMER(CryoboomerEntity.createCreeperAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CryoboomerEntity::new).dimensions(EntityType.CREEPER.getDimensions()).trackRangeChunks(EntityType.CREEPER.getMaxTrackDistance()).build()),
    FROSTBITE(FrostbiteEntity.createZombieAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FrostbiteEntity::new).dimensions(EntityType.ZOMBIE.getDimensions()).trackRangeChunks(EntityType.ZOMBIE.getMaxTrackDistance()).build()),
    ICEWEAVER(IceweaverEntity.createSpiderAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, IceweaverEntity::new).dimensions(EntityType.SPIDER.getDimensions()).trackRangeChunks(EntityType.SPIDER.getMaxTrackDistance()).build()),
    SHIVERSTARE(ShiverstareEntity.createEndermanAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ShiverstareEntity::new).dimensions(EntityType.ENDERMAN.getDimensions()).trackRangeChunks(EntityType.ENDERMAN.getMaxTrackDistance()).build());

    private final String id;
    private final DefaultAttributeContainer.Builder entityAttributes;
    private final EntityType<? extends MobEntity> entityType;

    ModMobEntityTypes(DefaultAttributeContainer.Builder entityAttributes, EntityType<? extends MobEntity> entityType) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.entityAttributes = entityAttributes;
        this.entityType = entityType;
    }

    public String getId() {
        return this.id;
    }

    public DefaultAttributeContainer getEntityAttributes() {
        return this.entityAttributes.build();
    }

    public EntityType<? extends MobEntity> getEntityType() {
        return this.entityType;
    }
}
