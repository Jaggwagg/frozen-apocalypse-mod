package jaggwagg.frozen_apocalypse.client.render.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

public class ShiverstareEntityRenderer extends EndermanEntityRenderer {
    private static final Identifier TEXTURE = new Identifier(FrozenApocalypse.MOD_ID, "textures/entity/shiverstare.png");

    public ShiverstareEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(EndermanEntity entity) {
        return TEXTURE;
    }
}
