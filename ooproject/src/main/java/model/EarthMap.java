package model;

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

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }
}
