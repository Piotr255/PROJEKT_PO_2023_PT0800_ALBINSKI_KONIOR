package model;

import java.util.*;

public interface WorldMap extends MoveValidator {

    void place(Animal animal);

    void move(Animal animal, MoveDirection direction);

    boolean isOccupied(Vector2d position);
    List<Animal> animalsAt(Vector2d position);

    void removeAnimal(Animal animal);
    Boundary getCurrentBounds();

    void addObserver(MapChangeListener observer);
    void removeObserver(MapChangeListener observer);

    void mapChanged(String message);
}