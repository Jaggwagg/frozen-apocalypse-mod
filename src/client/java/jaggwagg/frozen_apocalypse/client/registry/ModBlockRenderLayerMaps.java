package jaggwagg.frozen_apocalypse.client.registry;

import jaggwagg.frozen_apocalypse.block.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ModBlockRenderLayerMaps {
    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICICLE.getBlock(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICY_COBWEB.getBlock(), RenderLayer.getCutout());
    }
}
