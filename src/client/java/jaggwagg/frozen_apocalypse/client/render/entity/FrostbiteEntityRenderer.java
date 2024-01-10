package jaggwagg.frozen_apocalypse.client.render.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.mob.FrostbiteEntity;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class FrostbiteEntityRenderer extends ZombieEntityRenderer {
    private static final Identifier TEXTURE = new Identifier(FrozenApocalypse.MOD_ID, "textures/entity/frostbite.png");

    public FrostbiteEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ZombieEntity entity) {
        return TEXTURE;
    }
}
