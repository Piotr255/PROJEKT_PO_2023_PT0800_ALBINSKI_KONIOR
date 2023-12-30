package model;

import java.util.*;

public class AbstractWorldMap implements WorldMap {
    protected List<MapChangeListener> subscribers = new ArrayList<>();
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();

    protected Map<Vector2d,Plant> plants = new HashMap<>();

    protected int mapHeight;

    protected int mapWidth;

    public AbstractWorldMap(int mapHeight, int mapWidth){
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    private final List<MapChangeListener> observers = new ArrayList<>();

    public void addObserver(MapChangeListener observer){
        observers.add(observer);
    }
    public void removeObserver(MapChangeListener observer){
        observers.remove(observer);
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

    public void subscribe(MapChangeListener observer){
        subscribers.add(observer);
    }
    public void unsubscribe(MapChangeListener observer){
        subscribers.remove(observer);
    }
    public void mapChanged(String message){
        System.out.println(observers);
        for(MapChangeListener observer : subscribers){
            observer.mapChanged(this,message);
        }
    }
    @Override
    public void removeAnimal(Animal animal){
        animals.get(animal.getPosition()).remove(animal);
    }

}


