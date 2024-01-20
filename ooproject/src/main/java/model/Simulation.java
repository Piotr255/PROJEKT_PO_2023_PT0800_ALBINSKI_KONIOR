package model;


import model.util.PlantsGrowthVariant;
import presenter.SimulationPresenter;
import java.util.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Simulation implements Runnable {
    private EarthMap simulationMap;
    private List<Animal> animals = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private final UUID id = UUID.randomUUID();
    private final Configurations configurations; //W tym są wszystkie konfiguracje symulacji
    private boolean simulationPaused = false;
    private float sumOfAgesDeadAnimals;

    private float deadAnimalCount;

    private int daysSinceStart = 0;
    private int onPreferedFieldsCountStart;
    private int onNotPreferedFieldsCountStart;


    public Configurations getConfigurations(){
        return configurations;
    }

    private final SimulationPresenter simulationPresenter;
    public Simulation(Configurations configurations,
                      EarthMap simulationMap, SimulationPresenter simulationPresenter) {
        //this.animals = animals;
        this.simulationPresenter = simulationPresenter;
        this.simulationMap = simulationMap;
        this.configurations = configurations;
        animalStart();
        // simulationMap.generateAnimals(configurations.getStartingAnimalCount(), 11111);
        grassDivisionStart(configurations.getStartingPlantsCount());
        simulationPresenter.setSimulation(this);
        for(Animal animal : animals){
            System.out.println(animal.getPosition());
        }


    }
  /*  public Simulation(List<Animal> animals, WorldMap simulationMap){
        this.animals = animals;
        this.simulationMap = simulationMap;

    }*/

    public boolean getSimulationPaused() {
        return simulationPaused;
    }

    private void grassDivisionStart(int start){
        List<Plant> temporaryList1;
        List<Plant> temporaryList2;
        onPreferedFieldsCountStart = (int) (start * 0.8);
        onNotPreferedFieldsCountStart = start - onPreferedFieldsCountStart;
        temporaryList1 = simulationMap.startGrass(onPreferedFieldsCountStart,true);
        temporaryList2 = simulationMap.startGrass(onNotPreferedFieldsCountStart,false);
        plants.addAll(temporaryList1);
        plants.addAll(temporaryList2);


    }

    private void animalStart(){
        List<Animal> temporaryListAnimals;
        temporaryListAnimals = simulationMap.generateAnimals(11111);
        animals.addAll(temporaryListAnimals);
    }

    public String getMostPopularGenom() {
        return simulationMap.getStrongestGenom();
    }

    private int[] convertGenomToInt(String genom){
        int len = genom.length();
        int[] temporaryGenom = new int[len];
        for(int i = 0; i<len; i++){
            temporaryGenom[i] = Character.getNumericValue(genom.charAt(i));
        }
        return temporaryGenom;

    }

    private void setMostPopularGenom(){
        int[] tmpGenom = convertGenomToInt(getMostPopularGenom());
        for(Animal animal: animals){
            animal.setHasMostPopularGenom(Arrays.equals(tmpGenom, animal.getGenom()));
        }
    }


    public int freePositionsNumber(){
        Boundary boundary = simulationMap.getCurrentBounds();
        int start1 = boundary.leftBottom().getX();
        int start2 = boundary.leftBottom().getY();
        int endPlus1 = boundary.rightTop().getX() + 1;
        int endPlus2 = boundary.rightTop().getY() + 1;
        int freePositions = configurations.getMapWidth()*configurations.getMapHeight();
        Map <Vector2d,List<Animal>> tmpAnimals = simulationMap.getAnimals();
        Map <Vector2d, Plant> tmpPlants = simulationMap.getPlants();
        for(int i = start1; i < endPlus1; i++){
            for(int j = start2; j < endPlus2; j++){
                if((tmpAnimals.get(new Vector2d(i,j)) !=null && !tmpAnimals.get(new Vector2d(i,j)).isEmpty())
                        || tmpPlants.get(new Vector2d(i,j)) != null){
                    freePositions--;
                }
            }
        }
        return freePositions;
    }

    public float averageEnergyLevel() {
        float animalCount = animals.size();
        if (animalCount == 0) {
            return Float.NaN;
        }
        float sumEnergy = 0;
        for(Animal animal : animals){
            sumEnergy += animal.getEnergy();
        }
        return sumEnergy/animalCount;
    }
    public float averageAgeOfLive() {
        if (deadAnimalCount == 0) {
            return Float.NaN;
        }
        return sumOfAgesDeadAnimals / deadAnimalCount;
    }

    public float averageChildrenCount(){
        float childrenCount = 0;
        if(animals.isEmpty()){
            return Float.NaN;
        }
        for(Animal animal: animals){
            childrenCount += animal.getChildrenCount();
        }
        return childrenCount/animals.size();
    }

    public int animalsCount(){
        return animals.size();
    }


    public int plantsCount(){
        return plants.size();
    }


    private void deleteDead() {
        Iterator<Animal> iterator = animals.iterator();

        while (iterator.hasNext()) {
            Animal animal = iterator.next();

            if (animal.getEnergy() <= 0) {
                //System.out.println("dziala iterator przy usuwaniu zwierzat");
                sumOfAgesDeadAnimals+=animal.getDays(); //zrobić zwiększanie liczby dni zwierzaków
                deadAnimalCount++;
                simulationMap.removeAnimal(animal);
                animal.setDeathDay(daysSinceStart);
                iterator.remove();

            }
        }
    }

    private void everydayActivities(){
        daysSinceStart++;
        for(Animal animal: animals){
            animal.lowerEnergy(1);
            animal.addDay();
        }
    }

    private void turnMove(){
        for(Animal animal: animals){
            simulationMap.turn(animal, animal.getCurrentGenom());
            simulationMap.move(animal,MoveDirection.FORWARD);
            animal.setGenomPosition(configurations.getAnimalBehaviorVariant());
          /*
            animal.turn();
            animal.setGenomPosition();
            animal.move(MoveDirection.FORWARD, simulationMap);*/
        }
    }

    private void plantsConsumption() {
        Iterator<Plant> iterator = plants.iterator();
        while (iterator.hasNext()) {
            Plant plant = iterator.next();
            if (simulationMap.animalsAt(plant.getPosition()) != null && !simulationMap.animalsAt(plant.getPosition()).isEmpty()) {
                simulationMap.eatPlant(plant.getPosition());
                if (configurations.getPlantsGrowthVariant() == PlantsGrowthVariant.CREEPING_JUNGLE){
                    simulationMap.deletePreferedFieldInJungle(plant);
                }
                iterator.remove();
            }

        }
    }


    private void globalReproduction(){
        Set<Vector2d> positionsAfterReproduction = new HashSet<>();
        List<Animal> animalsToAddToAnimalsList = new ArrayList<>();
        for (Animal animal : animals){
            if (!positionsAfterReproduction.contains(animal.getPosition())){
                positionsAfterReproduction.add(animal.getPosition());
                animalsToAddToAnimalsList.addAll(simulationMap.reproduce(simulationMap.animalsAt(animal.getPosition())));
            }
        }
        animals.addAll(animalsToAddToAnimalsList);
    }

    public void pauseSimulation(){
        simulationPaused = true;
    }

    public void unpauseSimulation(){
        simulationPaused = false;
    }



    public void run(){
        if (!simulationPaused) {
            deleteDead();
            turnMove();
            plantsConsumption();
            globalReproduction();
            grassDivisionStart(configurations.getEverydayGrowingPlantsCount());
            setMostPopularGenom();
            simulationPresenter.drawMap(simulationMap, false, false);
            everydayActivities();
            if(configurations.geDataToCsv.setStats(animalsCount(),plantsCount(),
                    freePositionsNumber(),getMostPopularGenom(),
                    averageEnergyLevel(),averageAgeOfLive(),averageChildrenCount());
            DataToCsv.writeStatsToCsv(id.toString() + ".csv");
        }
    }

    List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public EarthMap getSimulationMap() { //do testów
        return simulationMap;
    }

    public UUID getId() {
        return id;
    }
}


