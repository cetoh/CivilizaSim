package com.toh.us.CivilizaSim.Display;

import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivAction;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivActions;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Simulate.GenerateCivilizations;
import com.toh.us.CivilizaSim.GameObjects.Simulate.Simulation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;;

import java.util.ArrayList;
import java.util.List;

public class PrimaryController {

    @FXML
    private TextField textFieldPlayerCivName;

    @FXML
    private TextArea textAreaLeft;

    @FXML
    private TextField textFieldNumberOfMoves;

    @FXML
    private RadioButton rbAttack;

    @FXML
    private RadioButton rbDefend;

    @FXML
    private RadioButton rbTrain;

    @FXML
    private RadioButton rbTrade;

    @FXML
    private RadioButton rbProduce;

    @FXML
    private RadioButton rbBuild;

    @FXML
    private TitledPane titledPaneBuildings;

    @FXML
    private VBox vboxCultural;

    @FXML
    private VBox vboxEconomic;

    @FXML
    private VBox vboxMilitary;

    @FXML
    private VBox vboxPolitical;

    @FXML
    private VBox vboxScientific;

    @FXML
    private VBox vboxSpecial;

    @FXML
    private Button buttonRunSim;

    private Stage mainStage;

    private Simulation simulation;

    private Civilization playerCivilization;

    private List<Civilization> civilizations;

    private  List<RadioButton> allBuildingRadioButtons = new ArrayList<>();

    @FXML
    private void initialize() {
        playerCivilization = new Civilization("Player", this);

        addListeners();

        initializeBuildingGUI();

        GenerateCivilizations generateCivilizations = new GenerateCivilizations(this);

        civilizations = generateCivilizations.getCivilizations();
        addLogMessage("Generated Opposing Civilizations: ");
        for (Civilization civ: civilizations) {
            addLogMessage("\t" + civ.getName());
        }
        civilizations.add(playerCivilization);

        simulation = new Simulation(this, civilizations);
    }

    private void addListeners() {
        // force the field to be numeric only above 0
        textFieldNumberOfMoves.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[1-9]\\d*$")) {
                textFieldNumberOfMoves.setText(newValue.replaceAll("[^1-9]", ""));
            }
        });

        textFieldPlayerCivName.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                playerCivilization.setName(textFieldPlayerCivName.getText());
            }
        });

        rbAttack.setSelected(true);

        rbAttack.setOnAction(actionEvent -> {
            if (rbAttack.isSelected()) {
                rbDefend.setSelected(false);
                rbBuild.setSelected(false);
                rbProduce.setSelected(false);
                rbTrade.setSelected(false);
                rbTrain.setSelected(false);
                titledPaneBuildings.setExpanded(false);
            }
        });

        rbDefend.setOnAction(actionEvent -> {
            if (rbDefend.isSelected()) {
                rbAttack.setSelected(false);
                rbBuild.setSelected(false);
                rbProduce.setSelected(false);
                rbTrade.setSelected(false);
                rbTrain.setSelected(false);
                titledPaneBuildings.setExpanded(false);
            }
        });

        rbProduce.setOnAction(actionEvent -> {
            if (rbProduce.isSelected()) {
                rbDefend.setSelected(false);
                rbBuild.setSelected(false);
                rbAttack.setSelected(false);
                rbTrade.setSelected(false);
                rbTrain.setSelected(false);
                titledPaneBuildings.setExpanded(false);
            }
        });

        rbTrade.setOnAction(actionEvent -> {
            if (rbTrade.isSelected()) {
                rbDefend.setSelected(false);
                rbBuild.setSelected(false);
                rbProduce.setSelected(false);
                rbAttack.setSelected(false);
                rbTrain.setSelected(false);
                titledPaneBuildings.setExpanded(false);
            }
        });

        rbTrain.setOnAction(actionEvent -> {
            if (rbTrain.isSelected()) {
                rbDefend.setSelected(false);
                rbBuild.setSelected(false);
                rbProduce.setSelected(false);
                rbTrade.setSelected(false);
                rbAttack.setSelected(false);
                titledPaneBuildings.setExpanded(false);
            }
        });

        rbBuild.setOnAction(actionEvent -> {
            if (rbBuild.isSelected()) {
                rbDefend.setSelected(false);
                rbAttack.setSelected(false);
                rbProduce.setSelected(false);
                rbTrade.setSelected(false);
                rbTrain.setSelected(false);
                titledPaneBuildings.setExpanded(true);
            }
        });
    }

    private void initializeBuildingGUI() {
        List<BuildingName> culturalBuildings = BuildingName.getCulturalBuildings();
        List<BuildingName> economicBuildings = BuildingName.getEconomicBuildings();
        List<BuildingName> militaryBuildings = BuildingName.getMilitaryBuildings();
        List<BuildingName> politicalBuildings = BuildingName.getPoliticalBuildings();
        List<BuildingName> scientificBuildings = BuildingName.getScientificBuildings();
        List<BuildingName> specialBuildings = BuildingName.getSpecialBuildings();

        List<RadioButton> culturalRadioButtons = new ArrayList<>();
        List<RadioButton> economicRadioButtons = new ArrayList<>();
        List<RadioButton> militaryRadioButtons = new ArrayList<>();
        List<RadioButton> politicalRadioButtons = new ArrayList<>();
        List<RadioButton> scientificRadioButtons = new ArrayList<>();
        List<RadioButton> specialRadioButtons = new ArrayList<>();

        for (BuildingName buildingName : culturalBuildings) {
            RadioButton radioButton = new RadioButton(buildingName.toString());
            allBuildingRadioButtons.add(radioButton);
            culturalRadioButtons.add(radioButton);
        }
        for (BuildingName buildingName : economicBuildings) {
            RadioButton radioButton = new RadioButton(buildingName.toString());
            allBuildingRadioButtons.add(radioButton);
            economicRadioButtons.add(radioButton);
        }
        for (BuildingName buildingName : militaryBuildings) {
            RadioButton radioButton = new RadioButton(buildingName.toString());
            allBuildingRadioButtons.add(radioButton);
            militaryRadioButtons.add(radioButton);
        }
        for (BuildingName buildingName : politicalBuildings) {
            RadioButton radioButton = new RadioButton(buildingName.toString());
            allBuildingRadioButtons.add(radioButton);
            politicalRadioButtons.add(radioButton);
        }
        for (BuildingName buildingName : scientificBuildings) {
            RadioButton radioButton = new RadioButton(buildingName.toString());
            allBuildingRadioButtons.add(radioButton);
            scientificRadioButtons.add(radioButton);
        }
        for (BuildingName buildingName : specialBuildings) {
            RadioButton radioButton = new RadioButton(buildingName.toString());
            allBuildingRadioButtons.add(radioButton);
            specialRadioButtons.add(radioButton);
        }

        // Set listener which toggles all the other radio buttons off when one is selected.
        for (RadioButton radioButton : allBuildingRadioButtons) {
            List<RadioButton> otherButtons = new ArrayList<>(allBuildingRadioButtons);
            otherButtons.remove(radioButton);

            //Set action event handler
            radioButton.setOnAction(actionEvent -> {
                if (radioButton.isSelected()) {
                    for (RadioButton otherButton : otherButtons) {
                        otherButton.setSelected(false);
                    }
                }
            });
        }


        // Now actually place in VBoxes
        vboxCultural.getChildren().addAll(culturalRadioButtons);
        vboxEconomic.getChildren().addAll(economicRadioButtons);
        vboxMilitary.getChildren().addAll(militaryRadioButtons);
        vboxPolitical.getChildren().addAll(politicalRadioButtons);
        vboxScientific.getChildren().addAll(scientificRadioButtons);
        vboxSpecial.getChildren().addAll(specialRadioButtons);
    }

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    private void runSim() {
        simulation.setNumRounds(Integer.parseInt(textFieldNumberOfMoves.getText()));
        simulation.setPlayerAction(getAction());
        simulation.start();
    }

    private CivAction getAction() {
        CivAction action = new CivAction();
        if (rbAttack.isSelected()) {
            action.setAction(CivActions.ATTACK);
        }
        else if (rbDefend.isSelected()) {
            action.setAction(CivActions.DEFEND);
        }
        else if (rbTrain.isSelected()) {
            action.setAction(CivActions.TRAIN);
        }
        else if (rbTrade.isSelected()) {
            action.setAction(CivActions.TRADE);
        }
        else if (rbProduce.isSelected()) {
            action.setAction(CivActions.PRODUCE);
        }
        else {
            action.setAction(CivActions.BUILD);
            for (RadioButton radioButton : allBuildingRadioButtons){
                if (radioButton.isSelected()) {
                    action.setBuildingName(BuildingName.valueOf(radioButton.getText()));
                    break;
                }
            }
        }

        return action;
    }

    public void addLogMessage(String message) {
        System.out.println(message);
        Platform.runLater(() -> textAreaLeft.appendText(message + "\n"));
    }

}
