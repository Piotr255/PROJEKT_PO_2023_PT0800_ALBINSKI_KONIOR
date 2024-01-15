package model;

import java.util.*;

public class BaseWorldMap implements WorldMap {
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
  
    protected Map<Vector2d,Plant> plants = new HashMap<>();

    protected List<MapChangeListener> observers = new ArrayList<>();


    public List<List<Animal>> getAnimals(){
        return ((List<List<Animal>>) animals.values());
    }
    public List<Plant> getPlants(){
        return ((List<Plant>) plants.values());
    }
    @Override
    public void place(Animal animal) {
        animals.get(animal.getPosition()).add(animal);
        mapChanged("Zwierze zostalo postawione na" + animal.getPosition());
    }
    @Override
    public void move(Animal animal, MoveDirection direction) {
        if (animals.get(animal.getPosition()).contains(animal)){
            animals.get(animal.getPosition()).remove(animal);
            animal.move(direction,this);
            animals.get(animal.getPosition()).add(animal);
            mapChanged("Zwierze poruszylo sie na " + animal.getPosition());
        }
    }
  
    public boolean isPlantAt(Vector2d position){
        return plants.get(position)!=null;
    }


    public void addPlant(Vector2d position, int energy){
        Plant plant = new Plant(position, energy);
        plants.put(position, plant);
    }

    public void eatPlant(Animal animal){
        animal.setEnergy(animal.getEnergy() + plants.get(animal.getPosition()).getEnergy());
        plants.remove(animal.getPosition());

    }

 /*   public Animal getStrongest(Vector2d position){
        List<Animal> localAnimals = animals.get(position);


    }*/

    @Override
    public boolean isOccupied(Vector2d position) {
        return !animals.get(position).isEmpty();
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return false;
    }


    @Override
    public List<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public Boundary getCurrentBounds(){
        return null;
    }

    public void addObserver(MapChangeListener observer){
        observers.add(observer);
    }
    public void removeObserver(MapChangeListener observer){
        observers.remove(observer);
    }

    public void mapChanged(String message){
        System.out.println(observers);
        for(MapChangeListener observer : observers){
            observer.mapChanged(this,message);
        }
    }

    @Override
    public void removeAnimal(Animal animal){
        animals.get(animal.getPosition()).remove(animal);
    }

}

