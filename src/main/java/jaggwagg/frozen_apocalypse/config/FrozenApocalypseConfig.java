package jaggwagg.frozen_apocalypse.config;

import java.util.ArrayList;
import java.util.List;

public class FrozenApocalypseConfig {
    private final boolean isFrozenApocalypseEnabled;
    private final boolean isSunSizeChangesEnabled;
    private final List<AffectedDimension> affectedDimensions;
    private final List<FreezingImmuneEntity> freezingImmuneEntities;
    private final List<ApocalypseLevel> apocalypseLevels;

    public FrozenApocalypseConfig() {
        this.isFrozenApocalypseEnabled = true;
        this.isSunSizeChangesEnabled = true;
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
                new FreezingImmuneEntity("minecraft:ender_dragon"),
                new FreezingImmuneEntity("minecraft:polar_bear"),
                new FreezingImmuneEntity("minecraft:skeleton"),
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
                new ApocalypseLevel.Builder(1, 1, 1, 0.9f).freezeEntities(150, 1, 32, 1.0f).grassToFrostedGrass().build(),
                new ApocalypseLevel.Builder(2, 2, 1, 0.8f).freezeEntities(112, 2, 32, 1.0f).grassToFrostedGrass().build(),
                new ApocalypseLevel.Builder(3, 3, 2, 0.7f).freezeEntities(84, 3, 32, 1.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().leavesToDeadLeaves().build(),
                new ApocalypseLevel.Builder(4, 4, 2, 0.6f).freezeEntities(62, 4, 32, 1.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().leavesToDeadLeaves().build(),
                new ApocalypseLevel.Builder(5, 5, 3, 0.5f).freezeEntities(45, 5, 32, 1.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().build(),
                new ApocalypseLevel.Builder(6, 6, 3, 0.4f).freezeEntities(30, 6, 32, 1.5f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().placeSnowBlock().build(),
                new ApocalypseLevel.Builder(7, 7, 4, 0.3f).freezeEntities(20, 7, 16, 1.5f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().leavesToDeadLeaves().frostedGrassToDeadGrass().iceToPackedIce().lavaToObsidian().placeSnowBlock().build(),
                new ApocalypseLevel.Builder(8, 8, 4, 0.2f).freezeEntities(20, 8, 16, 2.0f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().leavesToDeadLeaves().frostedGrassToDeadGrass().deadGrassToPermafrost().iceToPackedIce().lavaToObsidian().placeSnowBlock().leavesDecay().build(),
                new ApocalypseLevel.Builder(9, 9, 5, 0.1f).freezeEntities(20, 9, 16, 2.5f).grassToFrostedGrass().mobsCanSurviveDaylight().allBiomesSnow().waterToIce().placeSnow().leavesToDeadLeaves().frostedGrassToDeadGrass().deadGrassToPermafrost().iceToPackedIce().lavaToObsidian().placeSnowBlock().leavesDecay().build()
        ));
    }

    public boolean isFrozenApocalypseEnabled() {
        return this.isFrozenApocalypseEnabled;
    }

    public boolean isSunSizeChangesEnabled() {
        return this.isSunSizeChangesEnabled;
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
