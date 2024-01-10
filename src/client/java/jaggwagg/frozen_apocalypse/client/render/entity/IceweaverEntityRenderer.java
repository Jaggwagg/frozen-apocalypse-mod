package jaggwagg.frozen_apocalypse.client.render.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.Identifier;

public class IceweaverEntityRenderer extends SpiderEntityRenderer<SpiderEntity> {
    private static final Identifier TEXTURE = new Identifier(FrozenApocalypse.MOD_ID, "textures/entity/iceweaver.png");

    public IceweaverEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SpiderEntity spiderEntity) {
        return TEXTURE;
    }
}
