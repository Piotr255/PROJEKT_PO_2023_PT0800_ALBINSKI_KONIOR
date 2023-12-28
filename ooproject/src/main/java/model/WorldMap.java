package model;

import java.util.*;

public interface WorldMap extends MoveValidator {

    void place(Animal animal);

    void move(Animal animal, MoveDirection direction);


    WorldElement objectAt(Vector2d position);

    HashSet<WorldElement> getElements();

    UUID getId();
}