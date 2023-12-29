package model;

public class Plant implements WorldElement {

    private final Vector2d position;

    private final int energy;

    public Plant(Vector2d position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
    public int getEnergy() {
        return energy;
    }

}
