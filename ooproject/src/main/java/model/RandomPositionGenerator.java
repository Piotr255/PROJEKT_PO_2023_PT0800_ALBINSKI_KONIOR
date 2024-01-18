// do poprawienia na nowy model
package model;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private long seed; //= 12345L;
    private Random rand;// = new Random(seed);
    private int grassCount;
    private Vector2d[] Vector2dTab;

    private int size;
    public RandomPositionGenerator(boolean [][] preferedFields, int grassCount, boolean preferedMode, long seed) {
        this.grassCount = grassCount;
        List<Vector2d> goodvectors = new ArrayList<>();
        int rows = preferedFields.length;
        int columns = preferedFields[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++){
                if (preferedMode == preferedFields[i][j]){
                    goodvectors.add(new Vector2d(i,j));
                }
            }
    }

        size = goodvectors.size();
        Vector2dTab = new Vector2d[size];
        this.seed = seed;
        rand = new Random(seed);
        for (int i = 0; i < size; i++) {
            Vector2dTab[i] = goodvectors.get(i);
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
            return grassCount>0 && size>0;
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