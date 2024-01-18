package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import model.*;
import javafx.scene.paint.Color;

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
    public void drawMap(EarthMap earthMap){
        int emptyFieldCount = 0; //Do wyświetlania statystyk
        Boundary boundary = earthMap.getCurrentBounds();
        int rows = boundary.rightTop().getY() - boundary.leftBottom().getY() + 1;
        int cols = boundary.rightTop().getX() - boundary.leftBottom().getX() + 1;
        Circle circle = new Circle(5);
        for (int i=-1; i<rows+1; i++){
            for (int j=-1; j<cols+1; j++){
                boolean animalPresent=false;
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
                else if (!earthMap.animalsAt(position).isEmpty()){
                    circle = new Circle(5);
                    Color color = Color.hsb(120, 0.5, 0.75);
                    animalPresent=true;
                    //get z najw. priorytetem
                }
                else if (earthMap.isPlantAt(position)){
                    label.setText("*");
                }
                else{
                    emptyFieldCount+=1;
                }
                final boolean fiAnimalPresent = animalPresent;
                final Circle fiCircle = circle;
                Platform.runLater(() -> {
                    if (!fiAnimalPresent){
                        mapGrid.add(label,fj,fi);
                    }
                    else{
                        mapGrid.add(fiCircle,fj,fi);
                    }
                });

            }
        }
        drawStats(earthMap, emptyFieldCount);
    }
    public void drawStats(EarthMap earthMap, int emptyFieldCount){ //Wywoływane w drawMap()
        int animalsCount = simulation.animalsCount();
        int plantsCount = simulation.plantsCount();
        animalCountLabel.setText(String.valueOf(animalsCount));
        plantCountLabel.setText(String.valueOf(plantsCount));
        emptyFieldCountLabel.setText(String.valueOf(emptyFieldCount));
    }

    public void mapChanged(){

    }
}


