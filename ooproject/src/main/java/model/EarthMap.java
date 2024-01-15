package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EarthMap extends BaseWorldMap {
    private final Boundary boundary;

    public EarthMap(int width, int height){
        boundary = new Boundary(new Vector2d(0,0),new Vector2d(width-1,height-1));

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
    public boolean canMoveTo(Vector2d position) {

        return position.precedes(boundary.rightTop()) && position.follows(boundary.leftBottom());
    }

    public void simulationNextTurn(){

    }

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
    }

    public void reproduce(List<Animal> animalsOnTheField){
        Collections.sort(animalsOnTheField);

    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }
}
