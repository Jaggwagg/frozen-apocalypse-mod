package jaggwagg.frozen_apocalypse.config;

public class ApocalypseLevel {
    public final Integer APOCALYPSE_LEVEL;
    public final int STARTING_DAY;
    public final boolean FREEZE_ENTITIES;
    public final int FREEZING_Y_LEVEL;
    public final float FREEZE_DAMAGE;
    public final int FREEZE_DAMAGE_DELAY;
    public final boolean DISABLE_WEATHER;
    public final boolean LEAF_DECAY;
    public final boolean GRASS_TO_PODZOL;
    public final boolean WATER_TO_ICE;
    public final boolean PLACE_SNOW;
    public final boolean ICE_TO_PACKED_ICE;
    public final boolean LAVA_TO_OBSIDIAN;
    public final boolean PLACE_SNOW_BLOCK;

    private ApocalypseLevel(Builder builder) {
        this.APOCALYPSE_LEVEL = builder.APOCALYPSE_LEVEL;
        this.STARTING_DAY = builder.STARTING_DAY;
        this.FREEZE_ENTITIES = builder.freezeEntities;
        this.FREEZING_Y_LEVEL = builder.freezingYLevel;
        this.FREEZE_DAMAGE = builder.freezeDamage;
        this.FREEZE_DAMAGE_DELAY = builder.freezeDamageDelay;
        this.DISABLE_WEATHER = builder.disableWeather;
        this.LEAF_DECAY = builder.leafDecay;
        this.GRASS_TO_PODZOL = builder.grassToPodzol;
        this.WATER_TO_ICE = builder.waterToIce;
        this.PLACE_SNOW = builder.placeSnow;
        this.ICE_TO_PACKED_ICE = builder.iceToPackedIce;
        this.LAVA_TO_OBSIDIAN = builder.lavaToObsidian;
        this.PLACE_SNOW_BLOCK = builder.placeSnowBlock;
    }

    public static class Builder {
        private final Integer APOCALYPSE_LEVEL;
        private final int STARTING_DAY;
        private boolean freezeEntities;
        private int freezingYLevel;
        private float freezeDamage;
        private int freezeDamageDelay;
        private boolean disableWeather;
        private boolean leafDecay;
        private boolean grassToPodzol;
        private boolean waterToIce;
        private boolean placeSnow;
        private boolean iceToPackedIce;
        private boolean lavaToObsidian;
        private boolean placeSnowBlock;

        public Builder(Integer apocalypseLevel, int startingDay) {
            this.APOCALYPSE_LEVEL = apocalypseLevel;
            this.STARTING_DAY = startingDay;
        }

        public ApocalypseLevel build() {
            return new ApocalypseLevel(this);
        }

        public Builder freezeEntities(int freezingYLevel, int freezeDamageDelay, float freezeDamage) {
            this.freezeEntities = true;
            this.freezingYLevel = freezingYLevel;
            this.freezeDamageDelay = freezeDamageDelay;
            this.freezeDamage = freezeDamage;
            return this;
        }

        public Builder disableWeather() {
            this.disableWeather = true;
            return this;
        }

        public Builder leafDecay() {
            this.leafDecay = true;
            return this;
        }

        public Builder grassToPodzol() {
            this.grassToPodzol = true;
            return this;
        }

        public Builder waterToIce() {
            this.waterToIce = true;
            return this;
        }

        public Builder placeSnow() {
            this.placeSnow = true;
            return this;
        }

        public Builder iceToPackedIce() {
            this.iceToPackedIce = true;
            return this;
        }

        public Builder lavaToObsidian() {
            this.lavaToObsidian = true;
            return this;
        }

        public Builder placeSnowBlock() {
            this.placeSnowBlock = true;
            return this;
        }
    }
}
