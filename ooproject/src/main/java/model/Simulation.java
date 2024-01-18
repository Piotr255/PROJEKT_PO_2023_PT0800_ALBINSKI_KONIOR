package model;


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

    private int daysSinceStart;
    private int onPreferedFieldsCountStart;
    private int onNotPreferedFieldsCountStart;

    Timer timer = new Timer();

    private final SimulationPresenter simulationPresenter;
    public Simulation(Configurations configurations,
                      EarthMap simulationMap, SimulationPresenter simulationPresenter) {
        //this.animals = animals;
        this.simulationPresenter = simulationPresenter;
        this.simulationMap = simulationMap;
        this.configurations = configurations;
        animalStart();
        // simulationMap.generateAnimals(configurations.getStartingAnimalCount(), 11111);
        grassDivisionStart();
        simulationPresenter.setSimulation(this);
        for(Animal animal : animals){
            System.out.println(animal.getPosition());
        }


    }
  /*  public Simulation(List<Animal> animals, WorldMap simulationMap){
        this.animals = animals;
        this.simulationMap = simulationMap;

    }*/



    private void grassDivisionStart(){
        List<Plant> temporaryList1;
        List<Plant> temporaryList2;
        int start = configurations.getStartingPlantsCount();
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

    public int[] getMostPopularGenom() {
        String mostPopular = simulationMap.getStrongestGenom();
        return null; //do poprawy
    }


    public float averageEnergyLevel() {
        float animalCount = animals.size();
        float sumEnergy = 0;
        for(Animal animal : animals){
            sumEnergy += animal.getEnergy();
        }
        return sumEnergy/animalCount;
    }
    public float averageAgeOfLive(){
        return sumOfAgesDeadAnimals/deadAnimalCount;

    }

    public double averageChildrenCount(){
        return 0; //do poprawy
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
                sumOfAgesDeadAnimals+=animal.getDays();
                deadAnimalCount++;
                iterator.remove();
                simulationMap.removeAnimal(animal);
            }
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
            if (!simulationMap.animalsAt(plant.getPosition()).isEmpty()) {
                simulationMap.eatPlant(plant.getPosition());
                iterator.remove();
            }
        }
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
            simulationPresenter.drawMap((EarthMap) simulationMap);
        }
    }

    List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public WorldMap getSimulationMap() { //do testów
        return simulationMap;
    }

    public UUID getId() {
        return id;
    }
}


