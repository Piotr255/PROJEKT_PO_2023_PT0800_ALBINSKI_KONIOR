package model;

import model.exceptions.TooLittleEnergyToReproduceException;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement, Comparable<Animal> {
    //public boolean placed;
    private MapDirection orientation;
    private Vector2d position;
    private int energy;

    private int days;

    private Boolean deathDay = null;
    private int currentGenomPosition = 0;
    private int genomIterator = 1;

    private int[] genom;

    private int ageInSimulationTurns = 0;

    private int childrenCount = 0;

    private List<Animal> children = new ArrayList<>();


    private int countAllDescendants(){

    }

    @Override
    public int compareTo(Animal other){
        if (this.energy!=other.energy){
            return Integer.compare(this.energy, other.energy);
        }
        else if (this.ageInSimulationTurns != other.ageInSimulationTurns){
            return Integer.compare(this.ageInSimulationTurns,other.ageInSimulationTurns);
        }
        return Integer.compare(this.childrenCount, other.childrenCount);
    }

    public void reproduce(int reproductionEnergyCost, int requiredReproductionEnergyCost){
        if (energy<requiredReproductionEnergyCost){
            throw new TooLittleEnergyToReproduceException();
        }
        else{
            energy-=reproductionEnergyCost;
            childrenCount+=1;
        }
    }

    public int getAgeInSimulationTurns() {
        return ageInSimulationTurns;
    }

    public int getChildrenCount() {
        return childrenCount;
    }
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

    public int getCurrentGenom(){
        return genom[currentGenomPosition];
    }
    public void setGenomPosition(){
        currentGenomPosition+=genomIterator;
        currentGenomPosition%=genom.length;
    }

    public void turn(int direction){
        for(int i = 0; i<direction;i++){
            orientation = orientation.next();
        }
    }
    public void move(MoveDirection direction, MoveValidator validator){
        Vector2d possiblePosition = position.add(orientation.toUnitVector());
        if (validator.canMoveTo(possiblePosition) == 0){
            position = possiblePosition;
        }
        else if (validator.canMoveTo(possiblePosition) == 1)){
            turn(4);
        }
        else {


        }
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
  
  
  
}

