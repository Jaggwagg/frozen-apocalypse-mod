package jaggwagg.frozen_apocalypse.config;

import net.minecraft.block.Block;

public class HeatBlock {
    public final String ID;
    public final int MAX_DISTANCE;
    private transient Block block;

    public HeatBlock(String id, int maxDistance, Block block) {
        this.ID = id;
        this.MAX_DISTANCE = maxDistance;
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
