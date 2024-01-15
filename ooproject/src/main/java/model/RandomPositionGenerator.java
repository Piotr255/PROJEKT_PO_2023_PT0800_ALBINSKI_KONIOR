// do poprawienia na nowy model
package model;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private long seed; //= 12345L;
    private Random rand;// = new Random(seed);
    private int grassCount;
    private Vector2d[] Vector2dTab;

    private int size;
    public RandomPositionGenerator(boolean [][] preferedFields, int grassCount, long seed) {
        this.grassCount = grassCount;
        size = maxWidth*maxHeight;
        Vector2dTab = new Vector2d[size];
        this.seed = seed;
        rand = new Random(seed);
        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++){
                Vector2dTab[i*maxWidth+j] = new Vector2d(i,j);
            }
        }

    }

    @NotNull
    @Override
    public Iterator<Vector2d> iterator() {
        return new CustomIterator();
    }


    public class CustomIterator implements Iterator<Vector2d>{

        @Override
        public boolean hasNext() {
            return grassCount>0;
        }

        @Override
        public Vector2d next() {
            int vector2dRand = rand.nextInt(size);
            Vector2d vector2dRandVal = Vector2dTab[vector2dRand];
            grassCount--;
            size--;
            Vector2dTab[vector2dRand] = Vector2dTab[size];
            Vector2dTab[size] = vector2dRandVal;
            return vector2dRandVal;
        }
    }

}