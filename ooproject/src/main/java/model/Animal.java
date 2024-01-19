package model;

import model.exceptions.TooLittleEnergyToReproduceException;
import model.util.AnimalBehaviorVariant;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement, Comparable<Animal> {
    //public boolean placed;
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private int energyBoost;
    private int days = 0;

    private boolean hasMostPopularGenom = false;

    private Boolean deathDay = null;
    private int currentGenomPosition = 0;
    private int genomIterator = 1;

    private AnimalBehaviorVariant animalBehaviorVariant;

    private int[] genom;

    private int ageInSimulationTurns = 0;

    private List<Animal> children = new ArrayList<>();

    public void addChild(Animal animal){
        children.add(animal);
    }

    public int countAllDescendants(){
        return 0; // do poprawy
    }

    public void eating(){
        energy+=energyBoost;
    }

    @Override
    public int compareTo(Animal other){
        if (this.energy!=other.energy){
            return Integer.compare(this.energy, other.energy);
        }
        else if (this.ageInSimulationTurns != other.ageInSimulationTurns){
            return Integer.compare(this.ageInSimulationTurns,other.ageInSimulationTurns);
        }
        return Integer.compare(this.getChildrenCount(), other.getChildrenCount());
    }

    public void reproduce(int reproductionEnergyCost, int requiredReproductionEnergyCost, Animal reproducedAnimal){
        energy-=reproductionEnergyCost;
        children.add(reproducedAnimal);
    }

    public int getAgeInSimulationTurns() {
        return ageInSimulationTurns;
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

    public Animal(Vector2d position, int[] genom, int energy, int energyBoost) {
        this.orientation = MapDirection.NORTH;
        this.position = position;
        this.genom = genom;
        this.energy = energy;
        this.energyBoost = energyBoost;
    }

    public void lowerEnergy(int amount){
        energy-=amount;
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
    public void setGenomPosition(AnimalBehaviorVariant animalBehaviorVariant){
        currentGenomPosition += genomIterator;
        if (animalBehaviorVariant == AnimalBehaviorVariant.FULL_PREDESTINATION) {
            currentGenomPosition %= genom.length;
        }
        else{
            if (currentGenomPosition == genom.length - 1){
                genomIterator = -1;
            }
            else if (currentGenomPosition == 0){
                genomIterator = 1;
            }
        }
    }

    public void turn(int direction){
        for(int i = 0; i<direction;i++){
            orientation = orientation.next();
        }
    }
    public void move(MoveDirection direction, EarthMap validator){
        Vector2d possiblePosition = position.add(orientation.toUnitVector());
        if (validator.canMoveTo(possiblePosition) == 0){
            position = possiblePosition;
        }
        else if (validator.canMoveTo(possiblePosition) == 1){
            turn(4);
        }
        else {
            Boundary boundary = validator.getCurrentBounds();
            if (possiblePosition.getX()<boundary.leftBottom().getX()){
                possiblePosition = new Vector2d(boundary.rightTop().getX(),possiblePosition.getY());
                position = possiblePosition;
            }
            else{
                possiblePosition = new Vector2d(boundary.leftBottom().getX(),possiblePosition.getY());
                position = possiblePosition;

            }



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


    public int[] getGenom() {
        return genom;
    }

    public int getDays() {
        return days;
    }

    public Boolean getDeathDay(){
        return deathDay;
    }

    public void setHasMostPopularGenom(boolean hasMostPopularGenom) {
        this.hasMostPopularGenom = hasMostPopularGenom;
    }

    public boolean isHasMostPopularGenom() {
        return hasMostPopularGenom;
    }

    public int getChildrenCount(){
        return children.size();
    }

    public void addDay(){
        days++;
    }
}

