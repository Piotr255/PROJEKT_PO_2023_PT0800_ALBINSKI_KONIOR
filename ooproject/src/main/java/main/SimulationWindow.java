package main;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
        configureStage(viewRoot);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(simulation, 2, 2, TimeUnit.SECONDS);

    }

    private void configureStage(BorderPane viewRoot){
        Stage stage = new Stage();
        Scene scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Simulation Window");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
        stage.show();
    }

}
