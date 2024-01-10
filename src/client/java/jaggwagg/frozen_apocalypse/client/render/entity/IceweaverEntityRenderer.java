package jaggwagg.frozen_apocalypse.client.render.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.mob.IceweaverEntity;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.entity.mob.CreeperEntity;
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
