package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Simulation implements Runnable {
    private WorldMap simulationMap;
    private List<Animal> animals;
    public Simulation(List<Animal> animals, WorldMap simulationMap){
        this.animals = animals;
        this.simulationMap = simulationMap;
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
            animal.turn(animal.getCurrentGenom());
            animal.setGenomPosition();
            animal.move(MoveDirection.FORWARD, simulationMap);
        }
    }

    private void plantsConsumption(){

    }

    public void run(){

    }

    List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    WorldMap getSimulationMap() { //do testów
        return simulationMap;
    }
}

