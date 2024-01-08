package jaggwagg.frozen_apocalypse.client.init;

import jaggwagg.frozen_apocalypse.init.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ModBlockRenderLayerMaps {
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RegisteredBlocks.ICICLE.getBlock(), RenderLayer.getCutout());
    }
}
