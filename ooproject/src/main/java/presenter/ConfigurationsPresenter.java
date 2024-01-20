package presenter;
import java.io.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.SimulationEngine;
import main.SimulationWindow;
import model.Configurations;
import model.exceptions.*;
import model.util.AnimalBehaviorVariant;
import model.util.PlantsGrowthVariant;

import javax.swing.*;
import java.util.*;

public class ConfigurationsPresenter {
    @FXML
    private TextField configurationNameTextField;
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
    private TextField genomeLengthTextField;
    @FXML
    private ComboBox<PlantsGrowthVariant> plantsGrowthVariantComboxBox;
    @FXML
    private ComboBox<AnimalBehaviorVariant> animalBehaviorVariantComboBox;
    @FXML
    private VBox exceptionsVBox;
    @FXML
    private Button startSimulationButton;
    @FXML
    private Button saveConfigurationButton;
    @FXML
    private ComboBox<String> chooseConfigurationComboBox;
    Map<String, Configurations> myConfigurations = new HashMap<>();
    
    private SimulationEngine simulationEngine = new SimulationEngine();
    public void InitializeConfigurations(){
        Set<Node> allTextFields = configurationsRoot.lookupAll(".text-field");
        Set<Node> textFields = new HashSet<>(allTextFields);
        textFields.removeIf(node -> node.equals(configurationNameTextField));
        for (Node node : textFields){
            if (node instanceof TextField textField){
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
            }
        }
        plantsGrowthVariantComboxBox.getItems().addAll(PlantsGrowthVariant.values());
        animalBehaviorVariantComboBox.getItems().addAll(AnimalBehaviorVariant.values());
        loadConfigurationNames();
    }

    private void loadConfigurationNames(){
        String fileName = "src/main/resources/save.txt";
        chooseConfigurationComboBox.getItems().clear();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            Configurations configuration = new Configurations();
            int i=0;
            while ((line = reader.readLine()) != null){
                if (line.equals("Next")){
                    configuration = new Configurations();
                    i=0;
                }
                switch(i){
                    case 1:
                        configuration.setConfigurationName(line);
                        break;
                    case 2:
                        configuration.setMapHeight(Integer.parseInt(line));
                        break;
                    case 3:
                        configuration.setMapWidth(Integer.parseInt(line));
                        break;
                    case 4:
                        configuration.setStartingPlantsCount(Integer.parseInt(line));
                        break;
                    case 5:
                        configuration.setEnergyFromSinglePlant(Integer.parseInt(line));
                        break;
                    case 6:
                        configuration.setEverydayGrowingPlantsCount(Integer.parseInt(line));
                        break;
                    case 7:
                        configuration.setStartingAnimalCount(Integer.parseInt(line));
                        break;
                    case 8:
                        configuration.setStartingEnergyCount(Integer.parseInt(line));
                        break;
                    case 9:
                        configuration.setRequiredReproductionEnergyCount(Integer.parseInt(line));
                        break;
                    case 10:
                        configuration.setReproductionEnergyCost(Integer.parseInt(line));
                        break;
                    case 11:
                        configuration.setMinimumMutationCount(Integer.parseInt(line));
                        break;
                    case 12:
                        configuration.setMaximumMutationCount(Integer.parseInt(line));
                        break;
                    case 13:
                        configuration.setGenomeLength(Integer.parseInt(line));
                        break;
                    case 14:
                        configuration.setPlantsGrowthVariant(PlantsGrowthVariant.valueOf(line));
                        break;
                    case 15:
                        configuration.setAnimalBehaviorVariant(AnimalBehaviorVariant.valueOf(line));
                        break;
                    case 16:
                        myConfigurations.put(configuration.getConfigurationName(), configuration);
                        chooseConfigurationComboBox.getItems().add(configuration.getConfigurationName());
                        break;
                }
                i+=1;
            }//end while
            chooseConfigurationComboBox.setOnAction(this::onChooseConfigurationComboBoxAction);
        }catch(IOException e){
            System.out.println("Wystapil blad podczas czytania pliku");
        }
    }

    private void loadConfiguration(String configurationName){
        Configurations configuration = myConfigurations.get(configurationName);
        configurationNameTextField.setText(configuration.getConfigurationName());
        mapHeightTextField.setText(String.valueOf(configuration.getMapHeight()));
        mapWidthTextField.setText(String.valueOf(configuration.getMapWidth()));
        startingPlantsCountTextField.setText(String.valueOf(configuration.getStartingPlantsCount()));
        energyFromSinglePlantTextField.setText(String.valueOf(configuration.getEnergyFromSinglePlant()));
        everydayGrowingPlantsCountTextField.setText(String.valueOf(configuration.getEverydayGrowingPlantsCount()));
        startingAnimalCountTextField.setText(String.valueOf(configuration.getStartingAnimalCount()));
        startingEnergyCountTextField.setText(String.valueOf(configuration.getStartingEnergyCount()));
        requiredReproductionEnergyCountTextField.setText(String.valueOf(configuration.getRequiredReproductionEnergyCount()));
        reproductionEnergyCostTextField.setText(String.valueOf(configuration.getReproductionEnergyCost()));
        minimumMutationCountTextField.setText(String.valueOf(configuration.getMinimumMutationCount()));
        maximumMutationCountTextField.setText(String.valueOf(configuration.getMaximumMutationCount()));
        genomeLengthTextField.setText(String.valueOf(configuration.getGenomeLength()));
        plantsGrowthVariantComboxBox.setValue(configuration.getPlantsGrowthVariant());
        animalBehaviorVariantComboBox.setValue(configuration.getAnimalBehaviorVariant());
    }

    private void onChooseConfigurationComboBoxAction(ActionEvent e) {
        String selectedItem = chooseConfigurationComboBox.getValue();
        loadConfiguration(selectedItem);
    }

    public void printException(String message){
        Label label = new Label(message);
        exceptionsVBox.getChildren().add(label);
    }

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
        if (validationResult){
            Configurations configurations = new Configurations(Integer.parseInt(mapHeightTextField.getText()),
                    Integer.parseInt(mapWidthTextField.getText()),
                    Integer.parseInt(startingPlantsCountTextField.getText()),
                    Integer.parseInt(energyFromSinglePlantTextField.getText()),
                    Integer.parseInt(everydayGrowingPlantsCountTextField.getText()),
                    Integer.parseInt(startingAnimalCountTextField.getText()),
                    Integer.parseInt(startingEnergyCountTextField.getText()),
                    Integer.parseInt(requiredReproductionEnergyCountTextField.getText()),
                    Integer.parseInt(reproductionEnergyCostTextField.getText()),
                    Integer.parseInt(minimumMutationCountTextField.getText()),
                    Integer.parseInt(maximumMutationCountTextField.getText()),
                    Integer.parseInt(genomeLengthTextField.getText()),
                    plantsGrowthVariantComboxBox.getValue(),
                    animalBehaviorVariantComboBox.getValue());
            SimulationWindow simulationWindow = new SimulationWindow();
            try{
                simulationWindow.start(configurations, simulationEngine);
            }catch(IOException e){
                throw new SimulationWindowCreationException();
            }

        }
    }

    private boolean validateProgramInput(int mapHeight, int mapWidth, int startingPlantsCount, int everydayGrowingPlantsCount) {
        if (mapHeightTextField.getText().isBlank() || mapWidthTextField.getText().isBlank() ||
                startingPlantsCountTextField.getText().isBlank() || energyFromSinglePlantTextField.getText().isBlank() ||
                everydayGrowingPlantsCountTextField.getText().isBlank() || startingEnergyCountTextField.getText().isBlank() ||
                requiredReproductionEnergyCountTextField.getText().isBlank() || reproductionEnergyCostTextField.getText().isBlank() ||
                minimumMutationCountTextField.getText().isBlank() || maximumMutationCountTextField.getText().isBlank() ||
                genomeLengthTextField.getText().isBlank() || startingAnimalCountTextField.getText().isBlank() ||
                plantsGrowthVariantComboxBox.getValue()==null || animalBehaviorVariantComboBox.getValue()==null){
            EmptyFieldsException.throwException(this);
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

    @FXML
    private void onConfigurationSaveClicked(){
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
        if (myConfigurations.get(configurationNameTextField.getText())!=null){
            validationResult=false;
            AlreadyExistingConfigurationNameException.throwException(this);
        }
        if (validationResult){
            try{
                File file = new File("src/main/resources/save.txt");
                if (!(file.exists() && !file.isDirectory())){
                    if(file.createNewFile()){
                        System.out.println("Utworzono plik");
                    }
                    else{
                        System.out.println("Nie utworzono pliku. Wystapil blad.");
                    }
                }
                try(FileWriter writer = new FileWriter(file, true)) {
                    writer.append("\n");
                    writer.append("Next");
                    writer.append("\n");
                    writer.append(configurationNameTextField.getText());
                    writer.append("\n");
                    writer.append(mapHeightTextField.getText());
                    writer.append("\n");
                    writer.append(mapWidthTextField.getText());
                    writer.append("\n");
                    writer.append(startingPlantsCountTextField.getText());
                    writer.append("\n");
                    writer.append(energyFromSinglePlantTextField.getText());
                    writer.append("\n");
                    writer.append(everydayGrowingPlantsCountTextField.getText());
                    writer.append("\n");
                    writer.append(startingAnimalCountTextField.getText());
                    writer.append("\n");
                    writer.append(startingEnergyCountTextField.getText());
                    writer.append("\n");
                    writer.append(requiredReproductionEnergyCountTextField.getText());
                    writer.append("\n");
                    writer.append(reproductionEnergyCostTextField.getText());
                    writer.append("\n");
                    writer.append(minimumMutationCountTextField.getText());
                    writer.append("\n");
                    writer.append(maximumMutationCountTextField.getText());
                    writer.append("\n");
                    writer.append(genomeLengthTextField.getText());
                    writer.append("\n");
                    writer.append(plantsGrowthVariantComboxBox.getValue().toString());
                    writer.append("\n");
                    writer.append(animalBehaviorVariantComboBox.getValue().toString());
                    writer.append("\n");
                    writer.append("\n");
                }catch(Exception e){
                    System.out.println("nie udalo sie zrobic FileWritera");
                }
            }catch(IOException e){
                System.out.println("Nie utworzono pliku. Wystapil blad.");
            }
            loadConfigurationNames();
        }

    }

}
