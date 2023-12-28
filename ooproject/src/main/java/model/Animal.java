package model;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement {
    //public boolean placed;
    private MapDirection orientation;
    private Vector2d position;
    private int[] genom;



/*
    public Animal(){
        orientation = MapDirection.NORTH;
        position = new Vector2d(2,2);

    }

    public Animal(Vector2d position) {
        this();
        this.position = position;
    }*/

    public Animal(Vector2d position, int[] genom) {
        this.orientation = MapDirection.NORTH;
        this.position = position;
        this.genom = genom;
    }

    @Override
    public String toString() {
        return orientation.toString();
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void turn(int direction){
        for(int i = 0; i<direction;i++){
            orientation = orientation.next();
        }
    }
    public void move(MoveDirection direction, MoveValidator validator){
        Vector2d possiblePosition = position.add(orientation.toUnitVector());
        if (validator.canMoveTo(possiblePosition)){
            position = possiblePosition;
        }
        else{
            turn(4);
        }

    }
    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }
}

