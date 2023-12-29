package presenter;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimulationPresenter{
    @FXML
    private StackPane configurationsRoot;
    @FXML
    private TextField mapHeightTextField;
    @FXML
    private TextField mapWidthTextField;
    @FXML
    private TextField startingPlantsCountTextField;
    @FXML
    private TextField energyFromSinglePlantTextField;
    @FXML
    private TextField everydayGrowingPlantsCountTextField;
    @FXML
    private TextField startingAnimalCountTextField;
    @FXML
    private TextField startingEnergyCountTextField;
    @FXML
    private TextField requiredReproductionEnergyCountTextField;
    @FXML
    private TextField reproductionEnergyCostTextField;
    @FXML
    private TextField minimumMutationCountTextField;
    @FXML
    private TextField maximumMutationCountTextField;
    @FXML
    private TextField genomLengthTextField;
    @FXML
    private ComboBox<String> plantsGrowthVariantComboxBox;
    @FXML
    private ComboBox<String> genomReadVariantComboxBox;
    @FXML
    private VBox exceptionsVBox;
    @FXML
    private Button startSimulationButton;

    public void setAllValidations(){
        Set<Node> textFields = configurationsRoot.lookupAll(".text-field");
        for (Node node : textFields){
            if (node instanceof TextField textField){
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
            }
        }
    }

    public void printException(String message){
        Label label = new Label(message);
        exceptionsVBox.getChildren().add(label);
    }

    //Metoda wyzwalająca ze wzorca Observer
    public void onSimulationStartClicked(){
        clearExceptions();
        boolean validationResult = false;
        try {
            validationResult = validateProgramInput(Integer.parseInt(mapHeightTextField.getText()),
                    Integer.parseInt(mapWidthTextField.getText()),
                    Integer.parseInt(startingPlantsCountTextField.getText()),
                    Integer.parseInt(everydayGrowingPlantsCountTextField.getText()));
        }catch(IllegalArgumentException e){
            OtherConfigurationsException.throwException(this);
        }
    }

    private boolean validateProgramInput(int mapHeight, int mapWidth, int startingPlantsCount, int everydayGrowingPlantsCount) {
        if (mapHeightTextField.getText().isBlank() || mapWidthTextField.getText().isBlank() ||
                startingPlantsCountTextField.getText().isBlank() || energyFromSinglePlantTextField.getText().isBlank() ||
                everydayGrowingPlantsCountTextField.getText().isBlank() || startingEnergyCountTextField.getText().isBlank() ||
                requiredReproductionEnergyCountTextField.getText().isBlank() || reproductionEnergyCostTextField.getText().isBlank() ||
                minimumMutationCountTextField.getText().isBlank() || maximumMutationCountTextField.getText().isBlank() ||
                genomLengthTextField.getText().isBlank() || plantsGrowthVariantComboxBox.getValue().isBlank() ||
                genomReadVariantComboxBox.getValue().isBlank() || startingAnimalCountTextField.getText().isBlank()){
            EmptyTextFieldsException.throwException(this);
            return false;
        }
        if (mapHeight<5 || mapHeight>15 || mapWidth<5 || mapWidth>15){
            InvalidMapDimensionsException.throwException(this);
            return false;
        }
        if (startingPlantsCount > mapHeight*mapWidth){
            TooManyStartingPlantsException.throwException(this);
            return false;
        }
        if (everydayGrowingPlantsCount > mapHeight*mapWidth){
            TooManyEverydayGrowingPlantsCountException.throwException(this);
            return false;
        }
        return true;
    }

    private void clearExceptions(){
        exceptionsVBox.getChildren().clear();
    }
}
