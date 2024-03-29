package jaggwagg.frozen_apocalypse.config;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    private final boolean isFrozenApocalypseEnabled;
    private final boolean isSunSizeChangesEnabled;
    private final boolean isConvertingMobsEnabled;
    private final boolean isPlacingCustomBlocksEnabled;
    private final List<AffectedDimension> affectedDimensions;
    private final List<FreezingImmuneEntity> freezingImmuneEntities;
    private final List<ApocalypseLevel> apocalypseLevels;

    public ModConfig() {
        this.isFrozenApocalypseEnabled = true;
        this.isSunSizeChangesEnabled = true;
        this.isConvertingMobsEnabled = true;
        this.isPlacingCustomBlocksEnabled = true;
        this.affectedDimensions = new ArrayList<>();
        this.freezingImmuneEntities = new ArrayList<>();
        this.apocalypseLevels = new ArrayList<>();

        addDefaultAffectedDimensions();
        addDefaultFreezingImmuneEntities();
        addDefaultApocalypseLevels();
    }

    private void addDefaultAffectedDimensions() {
        this.affectedDimensions.add(new AffectedDimension("minecraft:overworld"));
    }

    private void addDefaultFreezingImmuneEntities() {
        this.freezingImmuneEntities.addAll(List.of(
                new FreezingImmuneEntity("frozen_apocalypse:cryoboomer"),
                new FreezingImmuneEntity("frozen_apocalypse:frostbite"),
                new FreezingImmuneEntity("frozen_apocalypse:iceweaver"),
                new FreezingImmuneEntity("frozen_apocalypse:shiverstare"),
                new FreezingImmuneEntity("minecraft:creeper"),
                new FreezingImmuneEntity("minecraft:ender_dragon"),
                new FreezingImmuneEntity("minecraft:polar_bear"),
                new FreezingImmuneEntity("minecraft:skeleton"),
                new FreezingImmuneEntity("minecraft:spider"),
                new FreezingImmuneEntity("minecraft:stray"),
                new FreezingImmuneEntity("minecraft:warden"),
                new FreezingImmuneEntity("minecraft:wither"),
                new FreezingImmuneEntity("minecraft:wither_skeleton"),
                new FreezingImmuneEntity("minecraft:zombie")
        ));
    }

    private void addDefaultApocalypseLevels() {
        this.apocalypseLevels.addAll(List.of(
                new ApocalypseLevel.Builder(0, 0, 0, 1.0f).build(),
                new ApocalypseLevel.Builder(1, 1, 1, 0.9f).freezeEntities(150, 0, 0, 0.0f).grassToFrostedGrass().build(),
                new ApocalypseLevel.Builder(2, 2, 3, 0.8f).freezeEntities(112, 1, 0, 0.0f).grassToFrostedGrass().build(),
                new ApocalypseLevel.Builder(3, 3, 5, 0.7f).freezeEntities(84, 2, 0, 0.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().placeIcicles().leavesToDeadLeaves().build(),
                new ApocalypseLevel.Builder(4, 4, 7, 0.6f).freezeEntities(62, 3, 0, 0.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().placeIcicles().leavesToDeadLeaves().build(),
                new ApocalypseLevel.Builder(5, 5, 9, 0.5f).freezeEntities(45, 4, 32, 0.5f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().placeIcicles().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().build(),
                new ApocalypseLevel.Builder(6, 6, 11, 0.4f).freezeEntities(30, 5, 32, 1.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().placeIcicles().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().placeSnowBlock().mobsCanSpawnDaylight().build(),
                new ApocalypseLevel.Builder(7, 7, 13, 0.3f).freezeEntities(20, 6, 16, 1.5f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().placeIcicles().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().placeSnowBlock().mobsCanSpawnDaylight().build(),
                new ApocalypseLevel.Builder(8, 8, 15, 0.2f).freezeEntities(20, 7, 16, 2.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().placeIcicles().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().placeSnowBlock().mobsCanSpawnDaylight().grassToPermafrost().leavesDecay().build(),
                new ApocalypseLevel.Builder(9, 9, 17, 0.1f).freezeEntities(20, 8, 16, 2.5f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().placeIcicles().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().placeSnowBlock().mobsCanSpawnDaylight().grassToPermafrost().leavesDecay().build()
        ));
    }

    public boolean isFrozenApocalypseEnabled() {
        return this.isFrozenApocalypseEnabled;
    }

    public boolean isSunSizeChangesEnabled() {
        return this.isSunSizeChangesEnabled;
    }

    public boolean isConvertingMobsEnabled() {
        return this.isConvertingMobsEnabled;
    }

    public boolean isPlacingCustomBlocksEnabled() {
        return this.isPlacingCustomBlocksEnabled;
    }

    public List<AffectedDimension> getAffectedDimensions() {
        return this.affectedDimensions;
    }

    public List<FreezingImmuneEntity> getFreezingImmuneEntities() {
        return this.freezingImmuneEntities;
    }

    public List<ApocalypseLevel> getApocalypseLevels() {
        return this.apocalypseLevels;
    }
}
