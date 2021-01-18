package com.toh.us.CivilizaSim.Display;

import com.toh.us.CivilizaSim.Display.Score.ScoreController;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Building;
import com.toh.us.CivilizaSim.GameObjects.Buildings.BuildingName;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Cost;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural.Church;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Cultural.Seminary;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Scientific.Academy;
import com.toh.us.CivilizaSim.GameObjects.Buildings.Scientific.University;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivAction;
import com.toh.us.CivilizaSim.GameObjects.Civ.CivActions;
import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Simulate.GenerateCivilizations;
import com.toh.us.CivilizaSim.GameObjects.Simulate.Simulation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Button buttonMakeMove;

    @FXML
    private Button buttonWheat;

    @FXML
    private Button buttonClay;

    @FXML
    private Button buttonWood;

    @FXML
    private Button buttonIron;

    @FXML
    private Button buttonGold;

    @FXML
    private VBox vboxPlayerBuildings;

    @FXML
    private Label labelKnowledgeAmount;

    @FXML
    private Label labelFaithAmount;

    @FXML
    private Label labelCivilianCount;

    @FXML
    private Label labelSoldierCount;

    @FXML
    private Label labelScholarCount;

    @FXML
    private Label labelPriestCount;

    @FXML
    private Label labelYear;

    private Stage mainStage;

    private Simulation simulation;

    private Civilization playerCivilization;

    private List<Civilization> civilizations;

    private  List<RadioButton> allBuildingRadioButtons = new ArrayList<>();

    @FXML
    private void initialize() {
        playerCivilization = new Civilization("Player");

        debug();

        addListeners();

        initializeActionGraphics();

        initializeBuildingGUI();

        updatePlayerGUI();

        GenerateCivilizations generateCivilizations = new GenerateCivilizations();

        civilizations = generateCivilizations.getCivilizations();
        addLogMessage("Generated Opposing Civilizations: ");
        for (Civilization civ: civilizations) {
            addLogMessage("\t" + civ.getName());
        }
        civilizations.add(playerCivilization);

        textFieldNumberOfMoves.setText(String.valueOf(1));

        simulation = new Simulation(this, civilizations, playerCivilization);
        simulation.updateResourceGUI();
    }

    private void debug() {
        Academy academy = new Academy();
        academy.setLevel(25);
        University university = new University();
        university.setLevel(25);
        Church church = new Church();
        church.setLevel(25);
        Seminary seminary = new Seminary();
        seminary.setLevel(25);
        playerCivilization.getBuildings().put(BuildingName.ACADEMY, academy);
        playerCivilization.getBuildings().put(BuildingName.UNIVERSITY, university);
        playerCivilization.getBuildings().put(BuildingName.CHURCH, church);
        playerCivilization.getBuildings().put(BuildingName.SEMINARY, seminary);

        playerCivilization.getWarehouse().getClay().setAmount(1000);
        playerCivilization.getWarehouse().getIron().setAmount(1000);
        playerCivilization.getWarehouse().getWood().setAmount(1000);
        playerCivilization.getWarehouse().getWheat().setAmount(1000);
        playerCivilization.getWarehouse().getGold().setAmount(1000);
    }

    private void initializeActionGraphics() {
        rbAttack.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/toh/us/img/attack.png"), 32, 32, false, false)));
        rbDefend.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/toh/us/img/defend.png"), 32, 32, false, false)));
        rbProduce.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/toh/us/img/produce.png"), 32, 32, false, false)));
        rbTrade.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/toh/us/img/trade.png"), 32, 32, false, false)));
        rbTrain.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/toh/us/img/train.png"), 32, 32, false, false)));
        rbBuild.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/toh/us/img/build.png"), 32, 32, false, false)));
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
            else {
                rbAttack.setSelected(true);
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
            else {
                rbAttack.setSelected(true);
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
            else {
                rbAttack.setSelected(true);
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
            else {
                rbAttack.setSelected(true);
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
            else {
                rbAttack.setSelected(true);
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
            else {
                rbAttack.setSelected(true);
            }
        });

        titledPaneBuildings.setOnMouseClicked(actionEvent -> {
            if (titledPaneBuildings.isExpanded()) {
                rbDefend.setSelected(false);
                rbAttack.setSelected(false);
                rbProduce.setSelected(false);
                rbTrade.setSelected(false);
                rbTrain.setSelected(false);
                rbBuild.setSelected(true);
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
        int numMoves = 3;
        if (!textFieldNumberOfMoves.getText().equals("")) {
            numMoves = Integer.parseInt(textFieldNumberOfMoves.getText());
        }
        simulation.setNumRounds(numMoves);
        simulation.setPlayerAction(getAction());

        if (!textFieldPlayerCivName.getText().equals("")) {
            playerCivilization.setName(textFieldPlayerCivName.getText());
        } else {
            playerCivilization.setName("Player");
        }
        simulation.start();

        //Increment Year
        String[] year = labelYear.getText().split(": ");
        int yearInt = Integer.parseInt(year[1]);
        yearInt++;
        labelYear.setText("Year: " + yearInt);
    }

    @FXML
    private void checkScore() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/com/toh/us/app/scorePopup.fxml"));
        // initializing the controller
        ScoreController popupController = new ScoreController();
        popupController.setScore(simulation.getScore());
        loader.setController(popupController);
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            // this is the popup stage
            Stage popupStage = new Stage();
            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
            popupController.setStage(popupStage);

            popupStage.initOwner(mainStage);

            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void updatePlayerGUI() {
        HashMap<BuildingName, Building> buildingHashMap = playerCivilization.getBuildings();
        VBox vbox = this.getVboxPlayerBuildings();

        if (!vbox.getChildren().isEmpty()) {
            Platform.runLater(() -> vbox.getChildren().clear());
        }
        for (BuildingName buildingName : buildingHashMap.keySet()) {
            Building building = buildingHashMap.get(buildingName);
            Cost cost = building.getCost();
            Label label = new Label(buildingName.toString() + " - Level " + building.getLevel());
            Tooltip tooltip = new Tooltip();
            tooltip.setWrapText(true);
            tooltip.setText("Cost to Upgrade:\n" +
                    "Wheat: " + cost.getWheat().getAmount() + "\n" +
                    "Clay: " + cost.getClay().getAmount() + "\n" +
                    "Wood: " + cost.getWood().getAmount() + "\n" +
                    "Iron: " + cost.getIron().getAmount() + "\n" +
                    "Gold: " + cost.getGold().getAmount());
            label.setTooltip(tooltip);
            Platform.runLater(() -> vbox.getChildren().add(label));
        }
    }

    public String getPlayerCivilizationName() {
        return playerCivilization.getName();
    }

    public void addLogMessage(String message) {
        System.out.println(message);
        Platform.runLater(() -> textAreaLeft.appendText(message + "\n"));
    }

    public Button getButtonWheat() {
        return buttonWheat;
    }

    public Button getButtonClay() {
        return buttonClay;
    }

    public Button getButtonWood() {
        return buttonWood;
    }

    public Button getButtonIron() {
        return buttonIron;
    }

    public Button getButtonGold() {
        return buttonGold;
    }

    public VBox getVboxPlayerBuildings() {
        return vboxPlayerBuildings;
    }

    public Label getLabelCivilianCount() {
        return labelCivilianCount;
    }

    public Label getLabelSoldierCount() {
        return labelSoldierCount;
    }

    public Label getLabelScholarCount() {
        return labelScholarCount;
    }

    public Label getLabelKnowledgeAmount() {
        return labelKnowledgeAmount;
    }

    public Label getLabelFaithAmount() {
        return labelFaithAmount;
    }

    public Label getLabelPriestCount() {
        return labelPriestCount;
    }
}
