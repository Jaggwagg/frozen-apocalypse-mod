package jaggwagg.frozen_apocalypse.config;

public class ApocalypseLevel {
    private final Integer apocalypseLevel;
    private final int startingDay;
    private final int worldUpdateSpeed;
    private final float sunSize;
    private final boolean canMobsSurviveDaylight;
    private final boolean canAllBiomesSnow;
    private final boolean freezeEntities;
    private final int freezingYLevel;
    private final int minimumHeatSourceDistance;
    private final int freezeDamageDelay;
    private final float freezeDamage;
    private final boolean weatherDisabled;
    private final boolean leafDecay;
    private final boolean grassToPodzol;
    private final boolean waterToIce;
    private final boolean placeSnow;
    private final boolean iceToPackedIce;
    private final boolean lavaToObsidian;
    private final boolean placeSnowBlock;

    private ApocalypseLevel(Builder builder) {
        this.apocalypseLevel = builder.apocalypseLevel;
        this.startingDay = builder.startingDay;
        this.worldUpdateSpeed = builder.worldUpdateSpeed;
        this.sunSize = builder.sunSize;
        this.canMobsSurviveDaylight = builder.canMobsSurviveDaylight;
        this.canAllBiomesSnow = builder.canAllBiomesSnow;
        this.freezeEntities = builder.freezeEntities;
        this.freezingYLevel = builder.freezingYLevel;
        this.freezeDamage = builder.freezeDamage;
        this.freezeDamageDelay = builder.freezeDamageDelay;
        this.minimumHeatSourceDistance = builder.minimumHeatSourceDistance;
        this.weatherDisabled = builder.weatherDisabled;
        this.leafDecay = builder.leafDecay;
        this.grassToPodzol = builder.grassToPodzol;
        this.waterToIce = builder.waterToIce;
        this.placeSnow = builder.placeSnow;
        this.iceToPackedIce = builder.iceToPackedIce;
        this.lavaToObsidian = builder.lavaToObsidian;
        this.placeSnowBlock = builder.placeSnowBlock;
    }

    public static class Builder {
        private final Integer apocalypseLevel;
        private final int startingDay;
        private final int worldUpdateSpeed;
        private final float sunSize;
        private boolean canMobsSurviveDaylight;
        private boolean canAllBiomesSnow;
        private boolean freezeEntities;
        private int freezingYLevel;
        private float freezeDamage;
        private int freezeDamageDelay;
        private int minimumHeatSourceDistance;
        private boolean weatherDisabled;
        private boolean leafDecay;
        private boolean grassToPodzol;
        private boolean waterToIce;
        private boolean placeSnow;
        private boolean iceToPackedIce;
        private boolean lavaToObsidian;
        private boolean placeSnowBlock;

        public Builder(Integer apocalypseLevel, int startingDay, int worldUpdateSpeed, float sunSize) {
            this.apocalypseLevel = apocalypseLevel;
            this.startingDay = startingDay;
            this.worldUpdateSpeed = worldUpdateSpeed;
            this.sunSize = sunSize;
        }

        public ApocalypseLevel build() {
            return new ApocalypseLevel(this);
        }

        public Builder freezeEntities(int freezingYLevel, int minimumHeatSourceDistance, int freezeDamageDelay, float freezeDamage) {
            this.freezeEntities = true;
            this.freezingYLevel = freezingYLevel;
            this.minimumHeatSourceDistance = minimumHeatSourceDistance;
            this.freezeDamageDelay = freezeDamageDelay;
            this.freezeDamage = freezeDamage;
            return this;
        }

        public Builder mobsCanSurviveDaylight() {
            this.canMobsSurviveDaylight = true;
            return this;
        }

        public Builder allBiomesSnow() {
            this.canAllBiomesSnow = true;
            return this;
        }

        public Builder weatherDisabled() {
            this.weatherDisabled = true;
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

    public Integer getApocalypseLevel() {
        return this.apocalypseLevel;
    }

    public int getStartingDay() {
        return this.startingDay;
    }

    public int getWorldUpdateSpeed() {
        return this.worldUpdateSpeed;
    }

    public float getSunSize() {
        return this.sunSize;
    }

    public boolean canMobsSurviveDaylight() {
        return this.canMobsSurviveDaylight;
    }

    public boolean canAllBiomesSnow() {
        return this.canAllBiomesSnow;
    }

    public boolean canFreezeEntities() {
        return this.freezeEntities;
    }

    public int getFreezingYLevel() {
        return this.freezingYLevel;
    }

    public int getMinimumHeatSourceDistance() {
        return this.minimumHeatSourceDistance;
    }

    public int getFreezeDamageDelay() {
        return this.freezeDamageDelay;
    }

    public float getFreezeDamage() {
        return this.freezeDamage;
    }

    public boolean isWeatherDisabled() {
        return this.weatherDisabled;
    }

    public boolean canLeavesDecay() {
        return this.leafDecay;
    }

    public boolean canGrassTurnToPodzol() {
        return this.grassToPodzol;
    }

    public boolean canWaterTurnToIce() {
        return this.waterToIce;
    }

    public boolean canPlaceSnow() {
        return this.placeSnow;
    }

    public boolean canIceTurnToPackedIce() {
        return this.iceToPackedIce;
    }

    public boolean canLavaTurnToObsidian() {
        return this.lavaToObsidian;
    }

    public boolean canPlaceSnowBlock() {
        return this.placeSnowBlock;
    }
}
