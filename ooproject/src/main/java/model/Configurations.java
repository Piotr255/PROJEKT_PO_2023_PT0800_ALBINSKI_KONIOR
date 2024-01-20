package model;

import model.util.AnimalBehaviorVariant;
import model.util.PlantsGrowthVariant;
import presenter.SimulationPresenter;

public class Configurations {

    private String configurationName;
    private int mapHeight;
    private int mapWidth;
    private int startingPlantsCount;
    private int energyFromSinglePlant;
    private int everydayGrowingPlantsCount;
    private int startingAnimalCount;
    private int startingEnergyCount;
    private int requiredReproductionEnergyCount;
    private int reproductionEnergyCost;
    private int minimumMutationCount;
    private int maximumMutationCount;
    private int genomeLength;
    private PlantsGrowthVariant plantsGrowthVariant;
    private AnimalBehaviorVariant animalBehaviorVariant;
    private boolean shouldSaveStatsToCsv;

    public Configurations(int mapHeight, int mapWidth, int startingPlantsCount, int energyFromSinglePlant,
                          int everydayGrowingPlantsCount, int startingAnimalCount, int startingEnergyCount,
                          int requiredReproductionEnergyCount, int reproductionEnergyCost,
                          int minimumMutationCount,
                          int maximumMutationCount, int genomeLength,
                          PlantsGrowthVariant plantsGrowthVariant,
                          AnimalBehaviorVariant animalBehaviorVariant,
                          boolean shouldSaveStatsToCsv) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.startingPlantsCount = startingPlantsCount;
        this.energyFromSinglePlant = energyFromSinglePlant;
        this.everydayGrowingPlantsCount = everydayGrowingPlantsCount;
        this.startingAnimalCount = startingAnimalCount;
        this.startingEnergyCount = startingEnergyCount;
        this.requiredReproductionEnergyCount = requiredReproductionEnergyCount;
        this.reproductionEnergyCost = reproductionEnergyCost;
        this.minimumMutationCount = minimumMutationCount;
        this.maximumMutationCount = maximumMutationCount;
        this.genomeLength = genomeLength;
        this.plantsGrowthVariant = plantsGrowthVariant;
        this.animalBehaviorVariant = animalBehaviorVariant;
        this.shouldSaveStatsToCsv = shouldSaveStatsToCsv;
    }
    public Configurations(){

    }

    public Simulation configureSimulation(SimulationPresenter simulationPresenter){
        EarthMap earthMap = new EarthMap(this);
        return new Simulation(this,
                earthMap, simulationPresenter);
    }



    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setStartingPlantsCount(int startingPlantsCount) {
        this.startingPlantsCount = startingPlantsCount;
    }

    public void setEnergyFromSinglePlant(int energyFromSinglePlant) {
        this.energyFromSinglePlant = energyFromSinglePlant;
    }

    public void setEverydayGrowingPlantsCount(int everydayGrowingPlantsCount) {
        this.everydayGrowingPlantsCount = everydayGrowingPlantsCount;
    }

    public void setStartingAnimalCount(int startingAnimalCount) {
        this.startingAnimalCount = startingAnimalCount;
    }

    public void setStartingEnergyCount(int startingEnergyCount) {
        this.startingEnergyCount = startingEnergyCount;
    }

    public void setRequiredReproductionEnergyCount(int requiredReproductionEnergyCount) {
        this.requiredReproductionEnergyCount = requiredReproductionEnergyCount;
    }

    public void setMinimumMutationCount(int minimumMutationCount) {
        this.minimumMutationCount = minimumMutationCount;
    }

    public void setMaximumMutationCount(int maximumMutationCount) {
        this.maximumMutationCount = maximumMutationCount;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public void setPlantsGrowthVariant(PlantsGrowthVariant plantsGrowthVariant) {
        this.plantsGrowthVariant = plantsGrowthVariant;
    }

    public void setAnimalBehaviorVariant(AnimalBehaviorVariant animalBehaviorVariant) {
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

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    public int getReproductionEnergyCost() {
        return reproductionEnergyCost;
    }

    public void setReproductionEnergyCost(int reproductionEnergyCost) {
        this.reproductionEnergyCost = reproductionEnergyCost;
    }

    public boolean isShouldSaveStatsToCsv() {
        return shouldSaveStatsToCsv;
    }

    public void setShouldSaveStatsToCsv(boolean shouldSaveStatsToCsv) {
        this.shouldSaveStatsToCsv = shouldSaveStatsToCsv;
    }
}
