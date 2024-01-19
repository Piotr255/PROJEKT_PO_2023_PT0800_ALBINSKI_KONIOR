package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
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

    private final int CELL_WIDTH=15;
    private final int CELL_HEIGHT=15;

    private Simulation simulation = null;

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

        Circle circle = new Circle(5);
        int xMin = boundary.leftBottom().getX();
        int xMax = boundary.rightTop().getX();
        int yMax = boundary.rightTop().getY();
        int yMin = boundary.leftBottom().getY();
        for (int i=0; i<=rows; i++){
            for (int j=0; j<=cols; j++){
                boolean animalPresent=false;
                Label label = new Label();
                Pane cell = new Pane();
                cell.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
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
                    if (showOnPausedInfo && strongestAnimal.isHasMostPopularGenom()){
                        color = Color.hsb(180, 1, 1);
                    }
                    if (showOnPausedInfo && simulation.getSimulationMap().getPreferedFields()[xMin + j - 1][yMax - i + 1]>0){
                        cell.setStyle("-fx-background-color: yellow");
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
                                System.out.println("Kliknieto w zwierzaka");
                                followAnimalStats();
                            });
                        }
                    }
                });

            }
        }
        drawStats(earthMap, emptyFieldCount);
    }

    private void followAnimalStats(){
        singleStatsTitleLabel.setText("Statystyki wybranego zwierzaka: ");
        singleStatsContainer.setVisible(true);
        Animal followedAnimal = FollowedAnimal.getFollowedAnimal();
        String genomAsString = Arrays.stream(followedAnimal.getGenom())
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        singleGenomeLabel.setText(genomAsString);
        singleActivatedGenomePartLabel.setText(String.valueOf(followedAnimal.getCurrentGenom()));
        singleEnergyLabel.setText(String.valueOf(followedAnimal.getEnergy()));
        singleConsumedPlantsCountLabel.setText("");
        singleChildrenCountLabel.setText(String.valueOf(followedAnimal.getChildrenCount()));
        singleDescendantsCountLabel.setText("");
        singleLifeLengthCountLabel.setText("");
        if (followedAnimal.getDeathDay()!=null){
            singleDeathDayLabel.setText("");
        }
        else{
            singleDeathDayLabel.setText("");
        }

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


