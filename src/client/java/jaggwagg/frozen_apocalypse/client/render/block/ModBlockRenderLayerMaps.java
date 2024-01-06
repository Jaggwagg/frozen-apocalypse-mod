package jaggwagg.frozen_apocalypse.client.render.block;

import jaggwagg.frozen_apocalypse.registry.FrozenApocalypseBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ModBlockRenderLayerMaps {
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(FrozenApocalypseBlocks.RegisteredBlocks.ICICLE.getBlock(), RenderLayer.getCutout());
    }
}
