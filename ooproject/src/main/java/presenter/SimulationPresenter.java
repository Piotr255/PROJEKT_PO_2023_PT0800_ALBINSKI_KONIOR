package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SimulationPresenter {
    @FXML
    private Label testLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label animalCountLabel;
    @FXML
    private Label plantCountLabel;
    @FXML
    private Label emptyFieldCountLabel;
    @FXML
    private Label mostPopularGenotypesLabel;
    @FXML
    private Label averageEnergyLabel;
    @FXML
    private Label lifeExpectancyLabel;
    @FXML
    private Label averageChildCountLabel;
    private Simulation simulation = null;

    public void setSimulation(Simulation simulation){
        if (this.simulation==null){
            this.simulation = simulation;
        }
    }
    public void drawMap(BaseWorldMap baseWorldMap){
        int emptyFieldCount = 0; //Do wyświetlania statystyk
        Boundary boundary = baseWorldMap.getCurrentBounds();
        int rows = boundary.rightTop().getY() - boundary.leftBottom().getY() + 1;
        int cols = boundary.rightTop().getX() - boundary.leftBottom().getX() + 1;
        for (int i=-1; i<rows+1; i++){
            for (int j=-1; j<cols+1; j++){
                Label label = new Label();
                int xCoord = j;
                int yCoord = rows-i;

                final int fi = i+1;
                final int fj = j+1;
                Vector2d position = new Vector2d(fi,fj);
                if (i==-1 && j==-1){
                    label.setText("y/x");
                }
                else if (i==-1) {
                    label.setText(String.valueOf(xCoord));
                }
                else if (j==-1){
                    label.setText(String.valueOf(yCoord));
                }
                else if (!baseWorldMap.animalsAt(position).isEmpty()){
                    label.setText(baseWorldMap.animalsAt(position)
                            .get(1).getOrientation().toString());
                }
                else if (baseWorldMap.isPlantAt(position)){
                    label.setText("*");
                }
                else{
                    emptyFieldCount+=1;
                }

                Platform.runLater(() -> {
                    mapGrid.add(label,fj,fi);
                });

            }
        }
        drawStats(baseWorldMap, emptyFieldCount);
    }
    public void drawStats(BaseWorldMap baseWorldMap, int emptyFieldCount){ //Wywoływane w drawMap()
        List<List<Animal>> animals = baseWorldMap.getAnimals();
        int animalCount = 0;
        for (int i=0; i<animals.size(); i++){
            animalCount += animals.get(i).size();
        }
        animalCountLabel.setText(String.valueOf(animalCount));
        List<Plant> plants = baseWorldMap.getPlants();
        plantCountLabel.setText(String.valueOf(plants.size()));
        emptyFieldCountLabel.setText(String.valueOf(emptyFieldCount));
    }

    public void mapChanged(){

    }
}


