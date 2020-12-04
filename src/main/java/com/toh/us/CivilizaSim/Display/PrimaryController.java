package com.toh.us.CivilizaSim.Display;

import com.toh.us.CivilizaSim.GameObjects.Civ.Civilization;
import com.toh.us.CivilizaSim.GameObjects.Simulate.GenerateCivilizations;
import com.toh.us.CivilizaSim.GameObjects.Simulate.Simulation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

public class PrimaryController {

    @FXML
    private TextArea textAreaLeft;

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

        List<Civilization> score = generateCivilizations.getCivilizations();

        Simulation simulation = new Simulation(this, score);
        simulation.start();
    }

    public void addLogMessage(String message) {
        Platform.runLater(() -> textAreaLeft.appendText(message + "\n"));
    }

}
