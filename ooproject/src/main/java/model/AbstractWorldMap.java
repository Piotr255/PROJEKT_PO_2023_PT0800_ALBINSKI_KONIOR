package model;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap {
    protected List<MapChangeListener> subscribers = new ArrayList<>();
    protected  Map<Vector2d, List<Animal>> animals = new HashMap<>();
    //private final UUID id = UUID.randomUUID();

    @Override
    public void place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.get(animal.getPosition()).add(animal);
            mapChanged("Zwierze zostalo postawione na" + animal.getPosition());
        }
    }
    @Override
    public void move(Animal animal, MoveDirection direction) {
        //Vector2d position1 = animal.getPosition();
        if (animals.get(animal.getPosition()) == animal){     //animals.containsValue(animal) animalsanimal.getPosition()  animal.placed
            animals.remove(animal.getPosition());
            animal.move(direction,this);
            animals.put(animal.getPosition(),animal);
            mapChanged("Zwierze poruszylo sie na " + animal.getPosition());
        }
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public HashSet<WorldElement> getElements(){
        return new HashSet<WorldElement>(animals.values());
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animals.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }


    @Override
    public abstract Boundary getCurrentBounds();
    @Override
    public String toString() {
        Boundary boundary = getCurrentBounds();
        return mapVisualizer.draw(boundary.leftBottom(), boundary.rightTop());
    }

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
    }
}


