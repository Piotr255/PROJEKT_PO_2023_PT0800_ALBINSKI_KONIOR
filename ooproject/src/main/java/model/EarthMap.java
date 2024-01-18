package model;

import model.util.GenerateGenom;
import model.util.PlantsGrowthVariant;

import java.util.*;

public class EarthMap implements WorldMap {
    private final Boundary boundary;
    private int[][] preferedFields;

    private final Configurations configurations;
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected Map<Vector2d,Plant> plants = new HashMap<>();
    private List<MapChangeListener> observers = new ArrayList<>();
    protected Map<String, Integer > genomMap = new HashMap<>();

    public Map<Vector2d, List<Animal>> getAnimals(){
        return Collections.unmodifiableMap(animals);
    }


    public EarthMap(Configurations configurations){
        this.configurations = configurations;
        boundary = new Boundary(new Vector2d(0,0),new Vector2d(configurations.getMapWidth()-1,configurations.getMapHeight()-1));
        this.preferedFields = new int[boundary.rightTop().getY() + 1][boundary.rightTop().getX() + 1];
        if (configurations.getPlantsGrowthVariant() != PlantsGrowthVariant.CREEPING_JUNGLE) {
            setPreferedFields();
            }
        }
    }

    public void setPreferedFields(){
        int rows = boundary.rightTop().getY() + 1;
        int cols = boundary.rightTop().getX() + 1;
        for(int i = 0; i<rows ; i++){
            for(int j = 0; j<cols; j++){
                if (i>0.375*rows && i<0.625*rows){
                    preferedFields[j][i] = 1;
                }
            }
        }

    }

    public void addPreferedFieldInJungle(Plant plant){
        Vector2d position = plant.getPosition();
        Vector2d position1 = position.add(MapDirection.NORTH.toUnitVector());
        Vector2d position2 = position.add(MapDirection.EAST.toUnitVector());
        Vector2d position3 = position.add(MapDirection.SOUTH.toUnitVector());
        Vector2d position4 = position.add(MapDirection.WEST.toUnitVector());
        if(position1.precedes(boundary.rightTop()) && position1.follows(boundary.leftBottom())){
            preferedFields[position1.getX()][position1.getY()]++;
        }
        if(position2.precedes(boundary.rightTop()) && position2.follows(boundary.leftBottom())){
            preferedFields[position2.getX()][position2.getY()]++;
        }
        if(position3.precedes(boundary.rightTop()) && position3.follows(boundary.leftBottom())){
            preferedFields[position3.getX()][position3.getY()]++;
        }
        if(position4.precedes(boundary.rightTop()) && position4.follows(boundary.leftBottom())){
            preferedFields[position4.getX()][position4.getY()]++;
        }
    }
    public void deletePreferedFieldInJungle(Plant plant){
        Vector2d position = plant.getPosition();
        Vector2d position1 = position.add(MapDirection.NORTH.toUnitVector());
        Vector2d position2 = position.add(MapDirection.EAST.toUnitVector());
        Vector2d position3 = position.add(MapDirection.SOUTH.toUnitVector());
        Vector2d position4 = position.add(MapDirection.WEST.toUnitVector());
        if(position1.precedes(boundary.rightTop()) && position1.follows(boundary.leftBottom())){
            preferedFields[position1.getX()][position1.getY()] = Math.max(0,preferedFields[position1.getX()][position1.getY()]-1);
        }
        if(position2.precedes(boundary.rightTop()) && position2.follows(boundary.leftBottom())){
            preferedFields[position2.getX()][position2.getY()] = Math.max(0,preferedFields[position2.getX()][position2.getY()]-1);
        }
        if(position3.precedes(boundary.rightTop()) && position3.follows(boundary.leftBottom())){
            preferedFields[position3.getX()][position3.getY()] = Math.max(0,preferedFields[position3.getX()][position3.getY()]-1);
        }
        if(position4.precedes(boundary.rightTop()) && position4.follows(boundary.leftBottom())){
            preferedFields[position4.getX()][position4.getY()] = Math.max(0,preferedFields[position4.getX()][position4.getY()]-1);
        }
        
    }
    
    /*public List<List<Animal>> getAnimals(){
        return ((List<List<Animal>>) animals.values());
    }
    public List<Plant> getPlants(){
        return ((List<Plant>) plants.values());
    }*/

    public void place(Animal animal) {
        if (animals.get(animal.getPosition())==null) {
            animals.put(animal.getPosition(), new ArrayList<Animal>());
        }
        animals.get(animal.getPosition()).add(animal);
        mapChanged("Zwierze zostalo postawione na" + animal.getPosition());
    }

    public void turn(Animal animal, int direction){
        animal.turn(direction);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return !animals.get(position).isEmpty();
    }

    @Override
    public List<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    public void addObserver(MapChangeListener observer){
        observers.add(observer);
    }
    public void removeObserver(MapChangeListener observer){
        observers.remove(observer);
    }

    public void mapChanged(String message){
        System.out.println(observers);
        for(MapChangeListener observer : observers){
            observer.mapChanged(this,message);
        }
    }

    @Override
    public void removeAnimal(Animal animal){
        animals.get(animal.getPosition()).remove(animal);
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if (animals.get(animal.getPosition()).contains(animal)){
            animals.get(animal.getPosition()).remove(animal);
            animal.move(direction,this);
            //animals.get(animal.getPosition()).add(animal);
            place(animal);
            mapChanged("Zwierze poruszylo sie na " + animal.getPosition());
        }
    }

    public boolean isPlantAt(Vector2d position){
        return plants.get(position)!=null;
    }


    public Vector2d addPlant(Vector2d position, int energy){
        if (!isPlantAt(position)) {
            System.out.println("Dziala");
            Plant plant = new Plant(position, energy);
            plants.put(position, plant);
            return position;
        }
        return null;
    }




    public void updateGenomMap(String genom, boolean add){
        if(add){
            if(genomMap.containsKey(genom)){
                int currentValue = genomMap.get(genom);
                genomMap.put(genom, currentValue + 1);
            }
            else{
                genomMap.put(genom, 1);
            }
        }
        else{
            int currentValue = genomMap.get(genom);
            if (currentValue - 1 > 0){
                genomMap.put(genom, currentValue - 1);
            }
            else{
                genomMap.remove(genom);
            }

        }

    }

    public String getStrongestGenom() {
        String strongestKey = null;
        Integer strongestValue = -1;
        for (Map.Entry<String, Integer> entry : genomMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value > strongestValue){
                strongestKey = key;
                strongestValue = value;

            }
        }
        return strongestKey;
    }


    public List<Plant> startGrass (int grassNumber, boolean prefered){ // do edycji do nowych zmiennych
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(preferedFields, grassNumber, prefered);
        List<Plant> temporaryPlantList = new LinkedList<>();
        for (Vector2d grassPosition : randomPositionGenerator) {
            System.out.println("pozycja wylosowana");
            System.out.println(grassPosition);
            Plant plant = new Plant(grassPosition,configurations.getEnergyFromSinglePlant());
            if (addPlant(plant.getPosition(),configurations.getEnergyFromSinglePlant()) != null) {
                temporaryPlantList.add(plant);
            }
        }
        return temporaryPlantList;
    }

    public List<Animal> generateAnimals(long seed){
        AnimalGenerator animalGenerator = new AnimalGenerator(configurations.getStartingAnimalCount(),seed,configurations.getMapHeight(),
                configurations.getMapWidth(),configurations.getStartingEnergyCount(),configurations.getGenomeLength());
        List<Animal> temporaryAnimals = animalGenerator.generateAnimals();
        for (Animal animal : temporaryAnimals){
            place(animal);
        }
        return temporaryAnimals;
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
        plants.remove(animal.getPosition());
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
        Collections.sort(animalsOnTheField, Collections.reverseOrder());
        Iterator<Animal> iterator = animalsOnTheField.iterator();
        while(iterator.hasNext()){
            Animal currentAnimal1 = iterator.next();
            if (iterator.hasNext()) {
                Animal currentAnimal2 = iterator.next();
                if (currentAnimal1.getEnergy() > configurations.getRequiredReproductionEnergyCount()
                        && currentAnimal2.getEnergy() > configurations.getRequiredReproductionEnergyCount()) {
                    Animal childAnimal = new Animal(currentAnimal1.getPosition(),
                            GenerateGenom.generateReproductionGenome(currentAnimal1.getEnergy(),
                                    currentAnimal2.getEnergy(), currentAnimal1.getGenom(),
                                    currentAnimal2.getGenom(), configurations.getMinimumMutationCount(), configurations.getMaximumMutationCount()),
                            2 * configurations.getReproductionEnergyCost());
                    place(childAnimal);
                    currentAnimal1.reproduce(configurations.getReproductionEnergyCost(), configurations.getRequiredReproductionEnergyCount());
                    currentAnimal2.reproduce(configurations.getReproductionEnergyCost(), configurations.getRequiredReproductionEnergyCount());
                    currentAnimal1.addChild(childAnimal);
                    currentAnimal2.addChild(childAnimal);

                }
            }
        }
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }

    public Configurations getConfigurations() {
        return configurations;
    }
}
