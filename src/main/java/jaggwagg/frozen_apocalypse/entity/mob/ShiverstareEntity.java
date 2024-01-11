package jaggwagg.frozen_apocalypse.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.world.World;

public class ShiverstareEntity extends EndermanEntity implements SlownessAfflicting {
    public ShiverstareEntity(EntityType<? extends ShiverstareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean canAttack = super.tryAttack(target);

        if (canAttack && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            this.inflictSlowness(this, (LivingEntity) target);
        }

        return canAttack;
    }
}
