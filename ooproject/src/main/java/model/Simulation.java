package model;


import presenter.SimulationPresenter;
import java.util.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Simulation implements Runnable {
    private WorldMap simulationMap;
    private List<Animal> animals;
    private List <Plant> plants;
    private final UUID id = UUID.randomUUID();
    private final Configurations configurations; //W tym są wszystkie konfiguracje symulacji

    private int sumOfAgesDeadAnimals;

    private int deadAnimalCount;

    private int daysSinceStart;




    private final SimulationPresenter simulationPresenter;
    public Simulation(Configurations configurations,
                      BaseWorldMap simulationMap, SimulationPresenter simulationPresenter) {
        //this.animals = animals;
        this.simulationPresenter = simulationPresenter;
        this.simulationMap = simulationMap;
        this.configurations = configurations;
        simulationPresenter.setSimulation(this);
        run();
    }
  /*  public Simulation(List<Animal> animals, WorldMap simulationMap){
        this.animals = animals;
        this.simulationMap = simulationMap;

    }*/

    public int[] getMostPopularGenom() {

    }

    public double averageEnergyLevel() {

    }
    public double averageAgeOfLive(){

    }

    public double averageChildrenCount(){

    }

    public int animalsCount(){

    }


    public int plantsCount(){

    }

    private void deleteDead() {
        Iterator<Animal> iterator = animals.iterator();

        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() <= 0) {
                iterator.remove();
                simulationMap.removeAnimal(animal);
            }
        }
    }

    private void turnMove(){
        for(Animal animal: animals){
            simulationMap.turn(animal, animal.getCurrentGenom());
            simulationMap.move(animal,MoveDirection.FORWARD);
            animal.setGenomPosition();
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

    }

    public void run(){
        simulationPresenter.drawMap((EarthMap) simulationMap);
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


