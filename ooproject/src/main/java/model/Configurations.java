package model;

import main.SimulationWindow;
import model.util.AnimalBehaviorVariant;
import model.util.PlantsGrowthVariant;
import presenter.SimulationPresenter;

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

    public int getStartingPlantsCount() {
        return startingPlantsCount;
    }

    public int getEnergyFromSinglePlant() {
        return energyFromSinglePlant;
    }

    public int getEverydayGrowingPlantsCount() {
        return everydayGrowingPlantsCount;
    }

    public int getStartingAnimalCount() {
        return startingAnimalCount;
    }

    public int getStartingEnergyCount() {
        return startingEnergyCount;
    }

    public int getRequiredReproductionEnergyCount() {
        return requiredReproductionEnergyCount;
    }

    public int getMinimumMutationCount() {
        return minimumMutationCount;
    }

    public int getMaximumMutationCount() {
        return maximumMutationCount;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public PlantsGrowthVariant getPlantsGrowthVariant() {
        return plantsGrowthVariant;
    }

    public AnimalBehaviorVariant getAnimalBehaviorVariant() {
        return animalBehaviorVariant;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
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

    public Simulation configureSimulation(SimulationPresenter simulationPresenter){
        AbstractWorldMap abstractWorldMap = new AbstractWorldMap(mapHeight, mapWidth);
        return new Simulation(this,
                abstractWorldMap, simulationPresenter);
    }
}
