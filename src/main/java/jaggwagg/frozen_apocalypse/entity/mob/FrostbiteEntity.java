package jaggwagg.frozen_apocalypse.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class FrostbiteEntity extends ZombieEntity implements SlownessAfflicting {
    public FrostbiteEntity(EntityType<? extends FrostbiteEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean attacked = super.tryAttack(target);

        if (attacked && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            this.inflictSlowness(this, (LivingEntity) target);
        }

        return attacked;
    }
}
