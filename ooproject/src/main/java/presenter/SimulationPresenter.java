package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
        clearGrid();

        int emptyFieldCount = 0; //Do wyświetlania statystyk
        Boundary boundary = earthMap.getCurrentBounds();
        int rows = boundary.rightTop().getY() - boundary.leftBottom().getY() + 1;
        int cols = boundary.rightTop().getX() - boundary.leftBottom().getX() + 1;
        Platform.runLater(() -> {
            for (int i = 0; i <= cols; i++) {
                mapGrid.getColumnConstraints().add(new ColumnConstraints(15));
            }
            for (int i = 0; i <= rows; i++) {
                mapGrid.getRowConstraints().add(new RowConstraints(15));
            }
        });

        Circle circle = new Circle(5);
        int xMin = boundary.leftBottom().getX();
        int xMax = boundary.rightTop().getX();
        int yMax = boundary.rightTop().getY();
        int yMin = boundary.leftBottom().getY();
        for (int i=0; i<=rows; i++){
            for (int j=0; j<=cols; j++){
                boolean animalPresent=false;
                Label label = new Label();

                final int fi = i;
                final int fj = j;
                Vector2d position = new Vector2d(xMin + j - 1, yMax - i + 1);
                if (i==0 && j==0){
                    label.setText("y/x");
                }
                else if (j == 0) {
                    label.setText(String.valueOf(yMax - i + 1));
                }
                else if (i==0){
                    label.setText(String.valueOf(xMin + j - 1));
                }
                else if (earthMap.animalsAt(position) != null && !earthMap.animalsAt(position).isEmpty()){
                    circle = new Circle(5);
                    Color color = Color.hsb(120, 0.5, 0.75);
                    circle.setFill(color);

                    animalPresent=true;
                    //get z najw. priorytetem
                }
                else if (earthMap.isPlantAt(position)){
                    label.setText("*");
                    System.out.println("Trawa dodana");
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
        Platform.runLater(() -> {
            animalCountLabel.setText(String.valueOf(animalsCount));
            plantCountLabel.setText(String.valueOf(plantsCount));
            emptyFieldCountLabel.setText(String.valueOf(emptyFieldCount));
        });

    }

    private void clearGrid(){
         Platform.runLater(() -> {
            mapGrid.getChildren().clear();
            mapGrid.getColumnConstraints().clear();
            mapGrid.getRowConstraints().clear();
         });

    }




    public void mapChanged(){

    }
}


