package model;

import model.util.GenerateGenom;

import java.util.*;

public class EarthMap extends BaseWorldMap {
    private final Boundary boundary;
    private boolean[][] preferedFields;
    private int startingEnergyCount;
    private int requiredReproductionEnergyCount;
    private int reproductionEnergyCost;
    private int minimumMutationCount;
    private int maximumMutationCount;

    public EarthMap(int width, int height, int startingEnergyCount, int requiredReproductionEnergyCount,
                    int reproductionEnergyCost, int minimumMutationCount, int maximumMutationCount){
        boundary = new Boundary(new Vector2d(0,0),new Vector2d(width-1,height-1));
        this.startingEnergyCount = startingEnergyCount;
    }




    public void startGrass (int grassNumber){ // do edycji do nowych zmiennych
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(preferedFields, grassNumber, 12345L);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition)); // jak w symulacji w liście te trawy umieścić?
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
        List<Animal> drawAnimals = new LinkedList<>();
        Animal strongest = currentAnimals.get(0);
        for (int i = 1; i<currentAnimals.size(); i++){
            if (strongest.getEnergy()<currentAnimals.get(i).getEnergy()){
                strongest = currentAnimals.get(i);
            }
            else if (strongest.getEnergy() == currentAnimals.get(i).getEnergy()){
                if(strongest.getDays() < currentAnimals.get(i).getDays()){
                    strongest = currentAnimals.get(i);
                }
                else if (strongest.getDays() == currentAnimals.get(i).getDays()){
                    if (strongest.getChildrenCount() < currentAnimals.get(i).getChildrenCount()){
                        strongest = currentAnimals.get(i);
                    }
                    else if (strongest.getChildrenCount() == currentAnimals.get(i).getChildrenCount()){
                        if (drawAnimals.isEmpty()){
                            drawAnimals.add(strongest);
                        }
                        drawAnimals.add(currentAnimals.get(i));
                    }

                }
            }
        }
        if (drawAnimals.isEmpty()){
            return strongest;
        }
        else {
            Random random = new Random();
            int randomIndex = random.nextInt(drawAnimals.size());
            return drawAnimals.get(randomIndex);
        }


    }

    public void eatPlant(Vector2d position){
        Animal animal = strongestAnimal(position);
        animal.eating();
    }




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
                                currentAnimal2.getGenom(), minimumMutationCount, maximumMutationCount),2*reproductionEnergyCost);
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
