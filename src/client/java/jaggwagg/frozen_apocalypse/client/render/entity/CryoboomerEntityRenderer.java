package jaggwagg.frozen_apocalypse.client.render.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;

public class CryoboomerEntityRenderer extends CreeperEntityRenderer {
    private static final Identifier TEXTURE = new Identifier(FrozenApocalypse.MOD_ID, "textures/entity/cryoboomer.png");

    public CryoboomerEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntity entity) {
        return TEXTURE;
    }
}
