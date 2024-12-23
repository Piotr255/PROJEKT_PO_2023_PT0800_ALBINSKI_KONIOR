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
    protected Map<String, Integer > genomMap = new HashMap<>();


    public EarthMap(Configurations configurations){
        this.configurations = configurations;
        boundary = new Boundary(new Vector2d(0,0),new Vector2d(configurations.getMapWidth()-1,configurations.getMapHeight()-1));
        this.preferedFields = new int[boundary.rightTop().getX() + 1][boundary.rightTop().getY() + 1];
        if (configurations.getPlantsGrowthVariant() == PlantsGrowthVariant.FORESTED_EQUATORS) {
            setPreferedFields();
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

    public void place(Animal animal) {
        if (animals.get(animal.getPosition())==null) {
            animals.put(animal.getPosition(), new ArrayList<Animal>());
        }
        animals.get(animal.getPosition()).add(animal);
    }

    public void turn(Animal animal, int direction){
        animal.turn(direction);
    }

    @Override
    public List<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }


    @Override
    public void removeAnimal(Animal animal){
        animals.get(animal.getPosition()).remove(animal);
        updateGenomMap(convertGenom(animal.getGenom()),false);
        if (animals.get(animal.getPosition()).isEmpty()){
            animals.remove(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if (animals.get(animal.getPosition()).contains(animal)){
            animals.get(animal.getPosition()).remove(animal);
            animal.move(direction,this);
            place(animal);
        }
    }

    public boolean isPlantAt(Vector2d position){
        return plants.get(position)!=null;
    }


    public Vector2d addPlant(Vector2d position, int energy){
        if (!isPlantAt(position)) {
            Plant plant = new Plant(position);
            plants.put(position, plant);
            if (configurations.getPlantsGrowthVariant() == PlantsGrowthVariant.CREEPING_JUNGLE){
                addPreferedFieldInJungle(plant);
            }
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


    public List<Plant> startGrass (int grassNumber, boolean prefered){
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(preferedFields, grassNumber, prefered);
        List<Plant> temporaryPlantList = new LinkedList<>();
        for (Vector2d grassPosition : randomPositionGenerator) {
            Plant plant = new Plant(grassPosition);
            if (addPlant(plant.getPosition(),configurations.getEnergyFromSinglePlant()) != null) {
                temporaryPlantList.add(plant);
            }
        }
        return temporaryPlantList;
    }

    private String convertGenom (int[] genom){
        StringBuilder builder = new StringBuilder();
        for (int value : genom) {
            builder.append(value);
        }
        return builder.toString();
    }
    public List<Animal> generateAnimals(long seed){
        AnimalGenerator animalGenerator = new AnimalGenerator(configurations.getStartingAnimalCount(),seed,configurations.getMapHeight(),
                configurations.getMapWidth(),
                configurations.getStartingEnergyCount(),configurations.getGenomeLength(),configurations.getEnergyFromSinglePlant());
        List<Animal> temporaryAnimals = animalGenerator.generateAnimals();
        for (Animal animal : temporaryAnimals){
            place(animal);
            updateGenomMap(convertGenom(animal.getGenom()),true);
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


    public List<Animal> reproduce(List<Animal> animalsOnTheField){
        Random random = new Random();
        Collections.sort(animalsOnTheField, Collections.reverseOrder());
        int iterator1 = 0;
        int iterator2 = 1;
        List<Animal> childrenToAdd = new ArrayList<>();
        while(iterator1<animalsOnTheField.size() && iterator2<animalsOnTheField.size()){
            Animal currentAnimal1 = animalsOnTheField.get(iterator1);
            Animal currentAnimal2 = animalsOnTheField.get(iterator2);
            if (currentAnimal1.getEnergy() > configurations.getRequiredReproductionEnergyCount()
                    && currentAnimal2.getEnergy() > configurations.getRequiredReproductionEnergyCount()) {
                int currentGenom = random.nextInt(configurations.getGenomeLength());
                int randomDirection = random.nextInt(MapDirection.values().length);
                MapDirection mapDirection = MapDirection.randomMapDirection(randomDirection);
                Animal childAnimal = new Animal(currentAnimal1.getPosition(),
                        GenerateGenom.generateReproductionGenome(currentAnimal1.getEnergy(),
                                currentAnimal2.getEnergy(), currentAnimal1.getGenom(),
                                currentAnimal2.getGenom(), configurations.getMinimumMutationCount(), configurations.getMaximumMutationCount()),
                        2 * configurations.getReproductionEnergyCost(), configurations.getEnergyFromSinglePlant()
                        ,currentGenom,mapDirection);
                childrenToAdd.add(childAnimal);
                currentAnimal1.reproduce(configurations.getReproductionEnergyCost(), childAnimal);
                currentAnimal2.reproduce(configurations.getReproductionEnergyCost(), childAnimal);
                currentAnimal1.addChild(childAnimal);
                currentAnimal2.addChild(childAnimal);

            }
            iterator1+=2;
            iterator2+=2;

        }
        for (Animal child : childrenToAdd){
            place(child);
            updateGenomMap(convertGenom(child.getGenom()),true);
        }
        return childrenToAdd;
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }

    public Configurations getConfigurations() {
        return configurations;
    }

    public Map<Vector2d, Plant> getPlants() {
        return Collections.unmodifiableMap(plants);
    }

    public Map<Vector2d, List<Animal>> getAnimals(){
        return Collections.unmodifiableMap(animals);
    }

    public int[][] getPreferedFields(){
        return preferedFields;
    }

    public void setPreferedFields(){
        int rows = boundary.rightTop().getY() + 1;
        int cols = boundary.rightTop().getX() + 1;
        for(int i = 0; i<rows ; i++){
            for(int j = 0; j<cols; j++){
                if (i>=0.4*rows && i<=0.6*rows){
                    preferedFields[j][i] = 1;
                }
            }
        }

    }
}
