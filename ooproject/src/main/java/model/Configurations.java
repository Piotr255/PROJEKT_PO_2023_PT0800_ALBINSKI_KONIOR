package model;

import model.util.AnimalBehaviorVariant;
import model.util.PlantsGrowthVariant;

public class Configurations {
    public Configurations(int mapHeight, int mapWidth, int startingPlantsCount, int energyFromSinglePlant,
                          int everydayGrowingPlantsCount, int startingAnimalCount, int startingEnergyCount,
                          int requiredReproductionEnergyCount, int minimumMutationCount,
                          int maximumMutationCount, int genomeLength,
                          PlantsGrowthVariant plantsGrowthVariant,
                          AnimalBehaviorVariant animalBehaviorVariant) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.startingPlantsCount = startingPlantsCount;
        this.energyFromSinglePlant = energyFromSinglePlant;
        this.everydayGrowingPlantsCount = everydayGrowingPlantsCount;
        this.startingAnimalCount = startingAnimalCount;
        this.startingEnergyCount = startingEnergyCount;
        this.requiredReproductionEnergyCount = requiredReproductionEnergyCount;
        this.minimumMutationCount = minimumMutationCount;
        this.maximumMutationCount = maximumMutationCount;
        this.genomeLength = genomeLength;
        this.plantsGrowthVariant = plantsGrowthVariant;
        this.animalBehaviorVariant = animalBehaviorVariant;
    }

    private final int mapHeight;
    private final int mapWidth;
    private final int startingPlantsCount;
    private final int energyFromSinglePlant;
    private final int everydayGrowingPlantsCount;
    private final int startingAnimalCount;
    private final int startingEnergyCount;
    private final int requiredReproductionEnergyCount;
    private final int minimumMutationCount;
    private final int maximumMutationCount;
    private final int genomeLength;
    private final PlantsGrowthVariant plantsGrowthVariant;
    private final AnimalBehaviorVariant animalBehaviorVariant;


}
