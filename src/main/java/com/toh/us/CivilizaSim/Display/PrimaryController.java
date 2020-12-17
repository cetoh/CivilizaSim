package com.toh.us.CivilizaSim.Display;

import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Simulate.GenerateCivilizations;
import com.toh.us.CivilizaSim.GameObjects.Simulate.Simulation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.List;

public class PrimaryController {

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
    private Button buttonRunSim;

    private Stage mainStage;

    private Simulation simulation;

    private List<Civilization> civilizations;

    @FXML
    private void initialize() {
        addListeners();

        GenerateCivilizations generateCivilizations = new GenerateCivilizations(this);

        civilizations = generateCivilizations.getCivilizations();

        simulation = new Simulation(this, civilizations);
    }

    private void addListeners() {
        // force the field to be numeric only above 0
        textFieldNumberOfMoves.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[1-9]\\d*$")) {
                textFieldNumberOfMoves.setText(newValue.replaceAll("[^1-9]", ""));
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

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    private void runSim() {
        simulation.setNumRounds(Integer.parseInt(textFieldNumberOfMoves.getText()));
        simulation.start();
    }

    public void addLogMessage(String message) {
        System.out.println(message);
        Platform.runLater(() -> textAreaLeft.appendText(message + "\n"));
    }

}
