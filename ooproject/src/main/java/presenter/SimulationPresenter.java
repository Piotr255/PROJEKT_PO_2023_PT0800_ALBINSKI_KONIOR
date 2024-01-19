package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

import java.util.*;
import java.util.stream.Collectors;

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

    //Labelki do statystyk sledzonego zwierzaka
    @FXML
    private Label singleStatsTitleLabel;
    @FXML
    private Label singleGenomeLabel;
    @FXML
    private Label singleActivatedGenomePartLabel;
    @FXML
    private Label singleEnergyLabel;
    @FXML
    private Label singleConsumedPlantsCountLabel;
    @FXML
    private Label singleChildrenCountLabel;
    @FXML
    private Label singleDescendantsCountLabel;
    @FXML
    private Label singleLifeLengthCountLabel;
    @FXML
    private Label singleDeathDayLabel;
    @FXML
    private VBox singleStatsContainer;
    @FXML
    private Button stopFollowAnimalButton;
    @FXML
    private Label emptyFieldCountLabel2;
    @FXML
    private Button showOnPauseInfoButton;

    private final int CELL_WIDTH=40;
    private final int CELL_HEIGHT=40;

    private Simulation simulation = null;

    private boolean shouldFollowSingleAnimalStats = false;

    public void setSimulation(Simulation simulation){
        if (this.simulation==null){
            this.simulation = simulation;
        }
    }

    @FXML
    private void initialize(){
        initializePauseButton(true);
        singleStatsContainer.setVisible(false);
        stopFollowAnimalButton.setOnAction((event) -> {
            singleStatsTitleLabel.setText("Kliknij na zwierzaka, aby sledzić jego statystyki:");
            singleStatsContainer.setVisible(false);
            shouldFollowSingleAnimalStats = false;
        });
        showOnPauseInfoButton.setOnAction((event) -> {
            drawMap(simulation.getSimulationMap(), true, true);
        });
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
            drawMap(simulation.getSimulationMap(), true, false);
        }
        else{
            simulation.unpauseSimulation();
            initializePauseButton(true);
            drawMap(simulation.getSimulationMap(),true,false);
        }

    }

    /*private void addClickListenersAfterPause(){
        Map<Vector2d, List<Animal>> animals = simulation.getSimulationMap().getAnimals();
        Iterator<Vector2d> iterator = animals.keySet().iterator();
        while (iterator.hasNext()){
            Vector2d occupiedPosition = iterator.next();

        }
    }*/



    public void drawMap(EarthMap earthMap, boolean isPaused, boolean showOnPausedInfo){
        clearGrid();

        int emptyFieldCount = 0;
        Boundary boundary = earthMap.getCurrentBounds();
        int rows = boundary.rightTop().getY() - boundary.leftBottom().getY() + 1;
        int cols = boundary.rightTop().getX() - boundary.leftBottom().getX() + 1;
        Platform.runLater(() -> {
            for (int i = -1; i < cols; i++) {
                ColumnConstraints columnConstraints = new ColumnConstraints(CELL_WIDTH);
                //columnConstraints.setHalignment(HPos.CENTER);
                mapGrid.getColumnConstraints().add(columnConstraints);
            }
            for (int i = -1; i < rows; i++) {
                RowConstraints rowConstraints = new RowConstraints(CELL_HEIGHT);
                //rowConstraints.setValignment(VPos.CENTER);
                mapGrid.getRowConstraints().add(rowConstraints);
            }
        });


        /*cell.setStyle("-fx-border-color: black; -fx-alignment: center;");
        mapGrid.add(cell, j, i);*/
        //mapGrid.setAlignment(Pos.CENTER);
        Circle circle = new Circle(10); //po prostu inicjalizacja
        int xMin = boundary.leftBottom().getX();
        int xMax = boundary.rightTop().getX();
        int yMax = boundary.rightTop().getY();
        int yMin = boundary.leftBottom().getY();
        for (int i=0; i<=rows; i++){
            for (int j=0; j<=cols; j++){
                boolean animalPresent=false;
                Label label = new Label();
                label.setAlignment(Pos.CENTER);
                label.setTextAlignment(TextAlignment.CENTER);
                //label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                //cell.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                StackPane cell = new StackPane();
                cell.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-border-color: black; -fx-alignment: center;");
                //cell.setPrefSize(CELL_WIDTH,CELL_HEIGHT);
                //cell.getStyleClass().add("grid-cell");
                //GridPane.setHalignment(label, HPos.CENTER);
                //GridPane.setValignment(label, VPos.CENTER);
                final int fi = i;
                final int fj = j;
                Vector2d position = new Vector2d(xMin + j - 1, yMax - i + 1);
                if (i==0 && j==0){
                    label.setText("y/x");
                    cell.getChildren().add(label);
                }
                else if (j == 0) {
                    label.setText(String.valueOf(yMax - i + 1));
                    cell.getChildren().add(label);
                }
                else if (i==0){
                    label.setText(String.valueOf(xMin + j - 1));
                    cell.getChildren().add(label);
                }
                else if (earthMap.animalsAt(position) != null && !earthMap.animalsAt(position).isEmpty()){
                    Animal strongestAnimal = earthMap.strongestAnimal(position);
                    int strongestAnimalEnergy = strongestAnimal.getEnergy();
                    circle = new Circle(10);
                    Color color = Color.hsb(120, 0.5, 0.75);
                    if (strongestAnimalEnergy>earthMap.getConfigurations().getRequiredReproductionEnergyCount()){
                        color = Color.hsb(30, 1, 1);
                    }
                    if (strongestAnimal.equals(FollowedAnimal.getFollowedAnimal())){
                        color = Color.hsb(70,1,1);
                    }
                    if (showOnPausedInfo && strongestAnimal.isHasMostPopularGenom()){
                        color = Color.hsb(180, 1, 1);
                    }
                    if (showOnPausedInfo && simulation.getSimulationMap().getPreferedFields()[xMin + j - 1][yMax - i + 1]>0){
                        cell.setStyle("-fx-background-color: yellow");
                    }
                    circle.setFill(color);
                    cell.getChildren().add(circle);
                    animalPresent=true;
                    //get z najw. priorytetem
                }
                else if (earthMap.isPlantAt(position)){
                    label.setText("*");
                    cell.getChildren().add(label);
                }
                else{
                    emptyFieldCount+=1;
                }
                final boolean fiAnimalPresent = animalPresent;
                final Circle fiCircle = circle;
                Platform.runLater(() -> {
                    if (!fiAnimalPresent){
                        mapGrid.add(cell,fj,fi);
                    }
                    else{
                        mapGrid.add(cell,fj,fi);
                        if (isPaused && simulation.getSimulationPaused()){
                            System.out.println("Ustawiono action dla kolka");
                            fiCircle.setOnMouseClicked((event) -> {
                                FollowedAnimal.setFollowedAnimal(earthMap.strongestAnimal(position));
                                System.out.println("Kliknieto w zwierzaka");
                                shouldFollowSingleAnimalStats = true;
                                drawMap(earthMap, true, false);
                            });
                        }
                    }
                });

            }
        }
        drawStats(earthMap, emptyFieldCount);
        if (shouldFollowSingleAnimalStats){
            followAnimalStats();
        }
    }

    private void followAnimalStats(){
        Platform.runLater(() -> {
            singleStatsTitleLabel.setText("Statystyki wybranego zwierzaka: ");
            singleStatsContainer.setVisible(true);
            Animal followedAnimal = FollowedAnimal.getFollowedAnimal();
            String genomAsString = Arrays.stream(followedAnimal.getGenom())
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining());
            singleGenomeLabel.setText(genomAsString);
            singleActivatedGenomePartLabel.setText(String.valueOf(followedAnimal.getCurrentGenom()));
            singleEnergyLabel.setText(String.valueOf(followedAnimal.getEnergy()));
            singleConsumedPlantsCountLabel.setText(String.valueOf(followedAnimal.getEatenPlantsCount()));
            singleChildrenCountLabel.setText(String.valueOf(followedAnimal.getChildrenCount()));
            singleDescendantsCountLabel.setText(String.valueOf(followedAnimal.countAllDescendants()));
            singleLifeLengthCountLabel.setText(String.valueOf(followedAnimal.getDays()));
            if (followedAnimal.getDeathDay()!=null){
                singleDeathDayLabel.setText(String.valueOf(followedAnimal.getDeathDay()));
            }
            else{
                singleDeathDayLabel.setText("Zwierze zyje");
            }
        });


    }

    public void drawStats(EarthMap earthMap, int emptyFieldCount){ //Wywoływane w drawMap()
        int animalsCount = simulation.animalsCount();
        int plantsCount = simulation.plantsCount();
        Platform.runLater(() -> {
            animalCountLabel.setText(String.valueOf(animalsCount));
            plantCountLabel.setText(String.valueOf(plantsCount));
            emptyFieldCountLabel.setText(String.valueOf(emptyFieldCount));
            emptyFieldCountLabel2.setText(String.valueOf(simulation.freePositionsNumber()));
            mostPopularGenotypesLabel.setText(simulation.getMostPopularGenom());
            averageEnergyLabel.setText(String.valueOf(simulation.averageEnergyLevel()));
            lifeExpectancyLabel.setText(String.valueOf(simulation.averageAgeOfLive()));
            averageChildCountLabel.setText(String.valueOf(simulation.averageChildrenCount()));
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


