package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.SimulationApp;
import main.SimulationWindow;
import model.*;
import model.exceptions.SimulationWindowCreationException;

import java.io.IOException;

public class SimulationPresenter {
    @FXML
    private Button testButton;
    @FXML
    private Label testLabel;
    private Simulation simulation = null;

    public void setSimulation(Simulation simulation){
        if (this.simulation==null){
            this.simulation = simulation;
        }
    }
    public void drawMap(AbstractWorldMap abstractWorldMap){

    }
    public void mapChanged(){

    }
}


