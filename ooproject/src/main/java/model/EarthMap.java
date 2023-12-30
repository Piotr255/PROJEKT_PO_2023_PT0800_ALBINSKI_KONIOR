package model;

public class EarthMap extends AbstractWorldMap{
    private final Boundary boundary;

    public EarthMap(int width, int height){
        boundary = new Boundary(new Vector2d(0,0),new Vector2d(width-1,height-1));
    }
    @Override
    public boolean canMoveTo(Vector2d position) {

        return position.precedes(boundary.rightTop()) && position.follows(boundary.leftBottom());
    }

    /*@Override
    public WorldElement objectAt(Vector2d position) {
        return null;
    }*/

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }
}
