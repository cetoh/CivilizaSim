package com.toh.us.CivilizaSim.Display.Score;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ScoreController implements Initializable {

    @FXML
    private VBox vboxMain;

    private Stage stage;

    private HashMap<String, HashMap<String, List<String>>> score;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (String civ : score.keySet()) {
            HashMap<String, List<String>> map = score.get(civ);

            TitledPane titledPane = new TitledPane();
            titledPane.setText(civ);

            VBox vboxInner = new VBox();


            // Overall Score
            TitledPane tpScore = new TitledPane();
            tpScore.setText("Overall");

            List<String> scoreList = map.get("Score");
            tpScore.setContent(new Label(scoreList.get(0)));

            // Civilians
            TitledPane tpCivilians = new TitledPane();
            tpCivilians.setText("Civilians");

            List<String> civiliansList = map.get("Civilians");
            VBox civilians = new VBox();
            for (String civilian : civiliansList) {
                civilians.getChildren().add(new Label(civilian));
            }
            tpCivilians.setContent(civilians);
            tpCivilians.setExpanded(false);

            // Soldiers
            TitledPane tpSoldiers = new TitledPane();
            tpSoldiers.setText("Soldiers");

            List<String> soldiersList = map.get("Soldiers");
            VBox soldiers = new VBox();
            for (String soldier : soldiersList) {
                soldiers.getChildren().add(new Label(soldier));
            }
            tpSoldiers.setContent(soldiers);
            tpSoldiers.setExpanded(false);

            // Scholars
            TitledPane tpScholars = new TitledPane();
            tpScholars.setText("Scholars");

            List<String> scholarsList = map.get("Scholars");
            VBox scholars = new VBox();
            for (String scholar : scholarsList) {
                scholars.getChildren().add(new Label(scholar));
            }
            tpScholars.setContent(scholars);
            tpScholars.setExpanded(false);

            // Resources
            TitledPane tpResources = new TitledPane();
            tpResources.setText("Resources");

            List<String> resourcesList = map.get("Resources");
            VBox resources = new VBox();
            for (String resource : resourcesList) {
                resources.getChildren().add(new Label(resource));
            }
            tpResources.setContent(resources);
            tpResources.setExpanded(false);

            // Buildings
            TitledPane tpBuildings = new TitledPane();
            tpBuildings.setText("Buildings");

            List<String> buildingsList = map.get("Buildings");
            VBox buildings = new VBox();
            for (String building : buildingsList) {
                buildings.getChildren().add(new Label(building));
            }
            tpBuildings.setContent(buildings);
            tpBuildings.setExpanded(false);


            vboxInner.getChildren().add(tpScore);
            vboxInner.getChildren().add(tpCivilians);
            vboxInner.getChildren().add(tpSoldiers);
            vboxInner.getChildren().add(tpScholars);
            vboxInner.getChildren().add(tpResources);
            vboxInner.getChildren().add(tpBuildings);
            titledPane.setContent(vboxInner);

            vboxMain.getChildren().add(titledPane);
        }
    }

    /**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }

    public void setScore(HashMap<String, HashMap<String, List<String>>> score) {
        this.score = score;
    }


}
