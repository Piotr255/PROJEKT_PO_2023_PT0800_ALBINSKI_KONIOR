package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.SimulationApp;
import main.SimulationWindow;
import model.*;
import model.exceptions.SimulationWindowCreationException;

import java.io.IOException;

public class SimulationPresenter {
    @FXML
    private Label testLabel;
    @FXML
    private GridPane mapGrid;
    private Simulation simulation = null;

    public void setSimulation(Simulation simulation){
        if (this.simulation==null){
            this.simulation = simulation;
        }
    }
    public void drawMap(AbstractWorldMap abstractWorldMap){
        int rows = abstractWorldMap.getMapWidth();
        int cols = abstractWorldMap.getMapHeight();
        for (int i=1; i<rows+2; i++){
            for (int j=1; j<cols+2; j++){
                Label label = new Label("*");
                int xCoord = rows-i-2;
                int yCoord = cols-j-2;


                mapGrid.add(label,j,i);
            }
        }
    }
    public void mapChanged(){

    }
}


