package model;

import model.exceptions.TooLittleEnergyToReproduceException;
import model.util.AnimalBehaviorVariant;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement, Comparable<Animal> {
    private final int ResourcesMax = 10000000;
    //public boolean placed;
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private int energyBoost;
    private int days = 0;
    private boolean hasMostPopularGenom = false;
    private Integer deathDay = null;
    private int currentGenomPosition = 0;
    private int genomIterator = 1; // co to jest?
    private int[] genom;
    private boolean ResourcesExceeded = false;

    private int eatenPlantsCount = 0;

    private int ageInSimulationTurns = 0;

    private List<Animal> children = new ArrayList<>();

    public Animal(Vector2d position, int[] genom, int energy, int energyBoost, int currentGenomPosition, MapDirection orientation) {
        this.orientation = orientation;
        this.position = position;
        this.genom = genom;
        this.energy = energy;
        this.energyBoost = energyBoost;
        this.currentGenomPosition = currentGenomPosition;
    }

    @Override
    public int compareTo(Animal other) {
        if (this.energy != other.energy) {
            return Integer.compare(this.energy, other.energy);
        } else if (this.ageInSimulationTurns != other.ageInSimulationTurns) {
            return Integer.compare(this.ageInSimulationTurns, other.ageInSimulationTurns);
        }
        return Integer.compare(this.getChildrenCount(), other.getChildrenCount());
    }

    public void addChild(Animal animal) {
        children.add(animal);
    }

    public int countAllDescendants() {
        if (ResourcesExceeded) {
            return -1;
        }
        int sum = 0;
        if (getChildrenCount() == 0) {
            return 0;
        }

        for (Animal animal : children) {
            sum += animal.countAllDescendants();
        }
        if (sum + getChildrenCount() >= ResourcesMax) {
            ResourcesExceeded = true; // polecam wyjątek


        }
        return sum + getChildrenCount();
    }

    public void eating() {
        energy += energyBoost;
        eatenPlantsCount++;
    }

    public void reproduce(int reproductionEnergyCost, Animal reproducedAnimal) {
        energy -= reproductionEnergyCost;
        children.add(reproducedAnimal);
    }

    public void lowerEnergy(int amount) {
        energy -= amount;
    }

    @Override
    public String toString() {
        return orientation.toString();
    }

    public int getCurrentGenom() {
        return genom[currentGenomPosition];
    }

    public void setGenomPosition(AnimalBehaviorVariant animalBehaviorVariant) { // zwierzę może zmieniać swoje zachowanie w trakcie życia?

        if (animalBehaviorVariant == AnimalBehaviorVariant.FULL_PREDESTINATION) {
            currentGenomPosition += genomIterator;
            currentGenomPosition %= genom.length;
        } else {
            if (currentGenomPosition == genom.length - 1) {
                genomIterator = -1;
            } else if (currentGenomPosition == 0) {
                genomIterator = 1;
            }
            currentGenomPosition += genomIterator;
        }


    }

    public void turn(int direction) {
        for (int i = 0; i < direction; i++) {
            orientation = orientation.next();
        }
    }

    public void move(MoveDirection direction, EarthMap validator) {
        Vector2d possiblePosition = position.add(orientation.toUnitVector());
        if (validator.canMoveTo(possiblePosition) == 0) {
            position = possiblePosition;
        } else if (validator.canMoveTo(possiblePosition) == 1) {
            turn(4);
        } else {
            Boundary boundary = validator.getCurrentBounds();
            if (possiblePosition.getX() < boundary.leftBottom().getX()) {
                possiblePosition = new Vector2d(boundary.rightTop().getX(), possiblePosition.getY());
                position = possiblePosition;
            } else {
                possiblePosition = new Vector2d(boundary.leftBottom().getX(), possiblePosition.getY());
                position = possiblePosition;

            }


        }
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public int[] getGenom() {
        return genom; // dehermetyzacja
    }

    public int getDays() {
        return days;
    }

    public Integer getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(Integer deathDay) {
        this.deathDay = deathDay;
    }

    public boolean isHasMostPopularGenom() {
        return hasMostPopularGenom;
    }

    public void setHasMostPopularGenom(boolean hasMostPopularGenom) {
        this.hasMostPopularGenom = hasMostPopularGenom;
    }

    public int getChildrenCount() {
        return children.size();
    }

    public int getEatenPlantsCount() {
        return eatenPlantsCount;
    }

    public void addDay() {
        days++;
    }
}

