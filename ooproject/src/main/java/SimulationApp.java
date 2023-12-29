import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presenter.SimulationPresenter;

import java.io.IOException;

public class SimulationApp extends Application {

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("configurator.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        Platform.runLater(presenter::setAllValidations);
        configureStage(primaryStage, viewRoot);
        primaryStage.show();

    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

}
