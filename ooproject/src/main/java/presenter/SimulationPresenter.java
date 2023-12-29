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
import model.Configurations;
import model.MapChangeListener;
import model.Simulation;
import model.WorldMap;
import model.exceptions.SimulationWindowCreationException;

import java.io.IOException;

public class SimulationPresenter implements MapChangeListener {
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
    public void drawID(String id){
        Platform.runLater(() -> {
            testLabel.setText(id);
        });

    }

    public void mapChanged(WorldMap worldMap, String message){
        drawID("cos");
    }

    public void onTestButtonClicked(){

    }
    public void notifyStart(){

    }
}


