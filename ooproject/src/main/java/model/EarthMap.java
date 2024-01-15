package model;

import model.util.GenerateGenom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EarthMap extends BaseWorldMap {
    private final Boundary boundary;
    private boolean[][] preferedFields;
    private int startingEnergyCount;
    private int requiredReproductionEnergyCount;
    private int reproductionEnergyCost;

    public EarthMap(int width, int height, int startingEnergyCount){
        boundary = new Boundary(new Vector2d(0,0),new Vector2d(width-1,height-1));
        this.startingEnergyCount = startingEnergyCount;
    }




    public void startGrass { // do edycji do nowych zmiennych
        this.grassNumber = grassNumber;
        double ceil = Math.floor(Math.sqrt(grassNumber * 10) + 1);
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator((int) ceil, (int) ceil, grassNumber, 12345L);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }


    @Override
    public int canMoveTo(Vector2d position) {
        if (position.precedes(boundary.rightTop()) && position.follows(boundary.leftBottom())){
            return 0;
        }
        if (position.getY()>boundary.rightTop().getY() || position.getY()<boundary.leftBottom().getY()){
            return 1;
        }
        return 2;
    }

    public Animal strongestAnimal(Vector2d position){
        List<Animal> currentAnimals = animalsAt(position);
        Animal strongest = currentAnimals.get(0);
        for (Animal animal : currentAnimals){
            if (strongest.getEnergy()<animal.getEnergy()){
                strongest = animal;
            }
            else if (strongest.getEnergy() == animal.getEnergy()){
                if(strongest.g)
            }
        }
    }

    public void eatPlant(Vector2d position)




    public void simulationNextTurn(){

    }
    /*
    public List<Animal> getDominantAnimals(List<Animal> animalsOnTheField){
        List<Animal> result = new ArrayList<>();
        Collections.sort(animalsOnTheField);
        Iterator<Animal> iterator = animalsOnTheField.iterator();
        Animal previous = null;
        Animal animal = null;
        while (iterator.hasNext()){
            previous = animal;
            animal = iterator.next();
            if (previous!=null && previous.getEnergy()==animal.getEnergy()
                    && previous.getChildrenCount()==animal.getChildrenCount()
                    && previous.getAgeInSimulationTurns()==animal.getAgeInSimulationTurns()){
            }
            if (previous!=null){
                result.add(previous);
            }

        }
        return null;
    }*/

    public void reproduce(List<Animal> animalsOnTheField){
        Collections.sort(animalsOnTheField);
        Iterator<Animal> iterator = animalsOnTheField.iterator();
        while(iterator.hasNext()){
            Animal currentAnimal1 = iterator.next();
            Animal currentAnimal2 = iterator.next();
            if (currentAnimal1.getEnergy()>requiredReproductionEnergyCount
            && currentAnimal2.getEnergy()>requiredReproductionEnergyCount){
                Animal childAnimal = new Animal(currentAnimal1.getPosition(),
                        GenerateGenom.generateReproductionGenome(currentAnimal1.getEnergy(),
                                currentAnimal2.getEnergy(), currentAnimal1.getGenom(),
                                currentAnimal2.getGenom()),2*reproductionEnergyCost);
                place(childAnimal);
                currentAnimal1.reproduce(reproductionEnergyCost, requiredReproductionEnergyCount);
                currentAnimal2.reproduce(reproductionEnergyCost, requiredReproductionEnergyCount);
            }
        }
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }
}
