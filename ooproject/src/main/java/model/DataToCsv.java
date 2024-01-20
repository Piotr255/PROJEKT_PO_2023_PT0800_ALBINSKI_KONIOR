package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataToCsv {
    private static int animalsCount;
    private static int plantsCount;
    private static int freePositionsNumber;
    private static String getMostPopularGenom;
    private static float averageEnergyLevel;
    private static float averageAgeOfLive;
    private static float averageChildrenCount;

    public static void setStats(int animalsCount, int plantsCount, int freePositionsNumber,
                                String getMostPopularGenom, float averageEnergyLevel,
                                float averageAgeOfLive, float averageChildrenCount){
        DataToCsv.animalsCount = animalsCount;
        DataToCsv.plantsCount = plantsCount;
        DataToCsv.freePositionsNumber = freePositionsNumber;
        DataToCsv.getMostPopularGenom = getMostPopularGenom;
        DataToCsv.averageEnergyLevel = averageEnergyLevel;
        DataToCsv.averageAgeOfLive = averageAgeOfLive;
        DataToCsv.averageChildrenCount = averageChildrenCount;
    }


    public static void writeStatsToCsv(String filePath) {
        File csvFile = new File(filePath);
        boolean isNewFile = !csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true); PrintWriter out = new PrintWriter(fw)) {
            if (isNewFile) {
                out.println("Animals Count;Plants Count;Free Positions Number;Most Popular Genom;Average Energy Level;Average Age Of Live;Average Children Count");
            }
            out.println(animalsCount + ";" + plantsCount + ";" + freePositionsNumber + ";"
                    + getMostPopularGenom + ";" + averageEnergyLevel + ";"
                    + averageAgeOfLive + ";" + averageChildrenCount);
        } catch (IOException e) {
            System.out.println("problemu z zapisem do pliku");
        }
    }




}

