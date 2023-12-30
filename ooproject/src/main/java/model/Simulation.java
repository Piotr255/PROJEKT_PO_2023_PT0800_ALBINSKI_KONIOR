package model;

import main.SimulationWindow;
import model.exceptions.SimulationWindowCreationException;
import presenter.SimulationPresenter;

import java.util.*;

public class Simulation implements Runnable {
    private WorldMap simulationMap;
    private List<Animal> animals;
    private final UUID id = UUID.randomUUID();
    private final Configurations configurations; //W tym są wszystkie konfiguracje symulacji

    private final SimulationPresenter simulationPresenter;
    public Simulation(Configurations configurations,
                      AbstractWorldMap simulationMap, SimulationPresenter simulationPresenter){
        //this.animals = animals;
        this.simulationPresenter = simulationPresenter;
        this.simulationMap = simulationMap;
        this.configurations = configurations;
        simulationPresenter.setSimulation(this);
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
    public void pauseSimulation(){

    }
    public void run(){

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
