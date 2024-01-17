package model;

import model.util.GenerateGenom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalGenerator {
    int animalNumber;
    long seed;
    int rows;
    int columns;
    int x;
    int y;
    Vector2d vector2d;
    int startEnergy;
    public List<Animal> generateAnimals() {
        List<Animal> animals = new ArrayList<>();
        Random random = new Random(seed);

        for (int i = 0; i < animalNumber; i++) {
            Animal animal = generateRandomAnimal(random);

            animals.add(animal);
        }

        return animals;
    }

    private Animal generateRandomAnimal(Random random) {
        x = random.nextInt(rows);
        y = random.nextInt(columns);
        vector2d = new Vector2d(x,y);
        return new Animal(vector2d, GenerateGenom.generateStartGenom(), startEnergy);
    }
}
