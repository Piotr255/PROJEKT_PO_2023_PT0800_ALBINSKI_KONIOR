package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.*;

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
    public void drawMap(BaseWorldMap baseWorldMap){
        Boundary boundary = baseWorldMap.getCurrentBounds();
        int rows = boundary.rightTop().getY() - boundary.leftBottom().getY() + 1;
        int cols = boundary.rightTop().getX() - boundary.leftBottom().getX() + 1;
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


