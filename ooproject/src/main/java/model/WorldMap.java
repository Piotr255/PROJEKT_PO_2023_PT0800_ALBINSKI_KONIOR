package model;

import java.util.*;

public interface WorldMap extends MoveValidator {

    void place(Animal animal);

    void move(Animal animal, MoveDirection direction);
    
    List<Animal> animalsAt(Vector2d position);

    void removeAnimal(Animal animal);

    Boundary getCurrentBounds();

    void turn(Animal animal, int direction);

    void eatPlant(Vector2d position);

    String getStrongestGenom();
}