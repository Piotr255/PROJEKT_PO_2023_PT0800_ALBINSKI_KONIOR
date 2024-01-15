package model.util;

import java.util.Arrays;
import java.util.Random;

public class GenerateGenom {

    public static int[] generateReproductionGenome(int energy1, int energy2, int[] genome1, int[] genome2,
                                                   int minimumMutationCount, int maximumMutationCount){

        Random random = new Random();
        int genomeLength = genome1.length;
        boolean leftPartOfGenomeFromGenome1 = random.nextBoolean();
        int[] leftPartGenome;
        int[] rightPartGenome;
        int[] reproductionGenome = new int[genomeLength];
        int leftEnergy;
        int rightEnergy;
        if (leftPartOfGenomeFromGenome1){
            leftPartGenome = genome1;
            rightPartGenome = genome2;
            leftEnergy = energy1;
            rightEnergy = energy2;
        }
        else{
            leftPartGenome = genome2;
            rightPartGenome = genome1;
            leftEnergy = energy2;
            rightEnergy = energy1;
        }
        int genomeBorder = (int)(((double) leftEnergy / (double) rightEnergy)*((double)genomeLength));
        for (int i=0; i<genomeBorder; i++){
            reproductionGenome[i] = leftPartGenome[i];
        }
        for (int i=0; i<genomeLength-genomeBorder; i++){
            reproductionGenome[i+genomeBorder] = rightPartGenome[i];
        }
        int mutationCount = random.nextInt(maximumMutationCount-minimumMutationCount+1)+minimumMutationCount;
        for (int i=0; i<mutationCount; i++){
            int randomIndex = random.nextInt(genomeLength);
            reproductionGenome[randomIndex] = random.nextInt(8);
        }
        return reproductionGenome;
    }

}
