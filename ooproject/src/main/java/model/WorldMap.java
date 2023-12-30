package model;

import java.util.*;

public interface WorldMap extends MoveValidator {

    void place(Animal animal);

    void move(Animal animal, MoveDirection direction);

    boolean isOccupied(Vector2d position);
    WorldElement objectAt(Vector2d position);

    void removeAnimal(Animal animal);

    UUID getId();
    Boundary getCurrentBounds();
}