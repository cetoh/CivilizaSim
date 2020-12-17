package com.toh.us.CivilizaSim.Display;

import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Simulate.GenerateCivilizations;
import com.toh.us.CivilizaSim.GameObjects.Simulate.Simulation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Button buttonRunSim;

    private Stage mainStage;

    @FXML
    private void initialize() {

    }

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    private void runSim() {
        GenerateCivilizations generateCivilizations = new GenerateCivilizations(this);

        List<Civilization> civilizations = generateCivilizations.getCivilizations();

        Simulation simulation = new Simulation(this, civilizations);
        simulation.start();
    }

    public void addLogMessage(String message) {
        System.out.println(message);
        Platform.runLater(() -> textAreaLeft.appendText(message + "\n"));
    }

}
