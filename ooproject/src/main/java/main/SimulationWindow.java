package main;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
import model.exceptions.SimulationWindowCreationException;
import presenter.ConfigurationsPresenter;
import presenter.SimulationPresenter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationWindow {

    public void start(Configurations configurations) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        SimulationPresenter simulationPresenter = new SimulationPresenter();
        loader.setController(simulationPresenter);
        BorderPane viewRoot = loader.load();
        Simulation simulation = configurations.configureSimulation(simulationPresenter);
        configureStage(viewRoot, simulation);
        SimulationEngine.submitSimulation(simulation);
    }

    private void configureStage(BorderPane viewRoot, Simulation simulation){
        Stage stage = new Stage();
        Scene scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Simulation Window");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                SimulationEngine.deleteSimulation(simulation);
            }
        });

        stage.show();
    }

}
