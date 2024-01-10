package jaggwagg.frozen_apocalypse.client.registry;

import jaggwagg.frozen_apocalypse.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ModBlockRenderLayerMaps {
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RegisteredBlocks.ICICLE.getBlock(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RegisteredBlocks.ICY_COBWEB.getBlock(), RenderLayer.getCutout());
    }
}
