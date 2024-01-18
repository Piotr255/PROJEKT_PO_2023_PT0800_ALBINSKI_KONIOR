package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import model.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

import java.util.Iterator;
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
    @FXML
    private Button pauseSimulationButton;

    private Simulation simulation = null;

    public void setSimulation(Simulation simulation){
        if (this.simulation==null){
            this.simulation = simulation;
        }
    }

    @FXML
    private void initialize(){
        initializePauseButton(true);
    }

    private void initializePauseButton(boolean shouldPause){
        if (shouldPause){
            Platform.runLater(() -> {
                pauseSimulationButton.setOnAction((event) -> {
                    pauseSimulation(true);
                });
            });
            pauseSimulationButton.setText("Pauza");
        }
        else{
            Platform.runLater(() -> {
                pauseSimulationButton.setOnAction((event) -> {
                    pauseSimulation(false);
                });
            });
            pauseSimulationButton.setText("Wznow");
        }

    }

    private void pauseSimulation(boolean pause){
        if (pause){
            simulation.pauseSimulation();
            initializePauseButton(false);
        }
        else{
            simulation.unpauseSimulation();
            initializePauseButton(true);
        }

    }

    /*private void addClickListenersAfterPause(){
        Map<Vector2d, List<Animal>> animals = simulation.getSimulationMap().getAnimals();
        Iterator<Vector2d> iterator = animals.keySet().iterator();
        while (iterator.hasNext()){
            Vector2d occupiedPosition = iterator.next();

        }
    }*/



    public void drawMap(EarthMap earthMap, boolean isPaused){
        clearGrid();

        int emptyFieldCount = 0; //Do wyświetlania statystyk
        Boundary boundary = earthMap.getCurrentBounds();
        int rows = boundary.rightTop().getY() - boundary.leftBottom().getY() + 1;
        int cols = boundary.rightTop().getX() - boundary.leftBottom().getX() + 1;
        Platform.runLater(() -> {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(15));
            mapGrid.getRowConstraints().add(new RowConstraints(15));
        });

        /*cell.setStyle("-fx-border-color: black; -fx-alignment: center;");
        mapGrid.add(cell, j, i);*/

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
                    label.setText("y/x ");
                }
                else if (j == 0) {
                    label.setText(String.valueOf(yMax - i + 1));
                }
                else if (i==0){
                    label.setText(String.valueOf(xMin + j - 1));
                }
                else if (earthMap.animalsAt(position) != null && !earthMap.animalsAt(position).isEmpty()){
                    Animal strongestAnimal = earthMap.strongestAnimal(position);
                    int strongestAnimalEnergy = strongestAnimal.getEnergy();
                    circle = new Circle(5);
                    Color color = Color.hsb(120, 0.5, 0.75);
                    if (strongestAnimalEnergy>earthMap.getConfigurations().getRequiredReproductionEnergyCount()){
                        color = Color.hsb(30, 1, 1);
                    }
                    circle.setFill(color);

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
                        if (isPaused && simulation.getSimulationPaused()){
                            fiCircle.setOnMouseClicked((event) -> {
                                FollowedAnimal.setFollowedAnimal(earthMap.strongestAnimal(position));
                            });
                        }
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


