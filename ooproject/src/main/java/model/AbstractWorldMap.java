package model;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap {
    /*protected List<MapChangeListener> subscribers = new ArrayList<>();
    protected  Map<Vector2d, List<Animal>> animals = new HashMap<>();

    protected Map<Vector2d,Plant> plants = new HashMap<>();
    private final UUID id = UUID.randomUUID();

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
    @Override
    public boolean isOccupied(Vector2d position) {
        return !animals.get(position).isEmpty();
    }


/*
    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);  //do zrobienia
    }
*/
    @Override
    public abstract Boundary getCurrentBounds();

    public void subscribe(MapChangeListener observer){
        subscribers.add(observer);
    }
    public void unsubscribe(MapChangeListener observer){
        subscribers.remove(observer);
    }
    private void mapChanged(String message){
        for(MapChangeListener observer : subscribers){
            observer.mapChanged(this,message);
        }
    }


    @Override
    public UUID getId() {
        return id;
    }*/
}


