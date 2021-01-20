package com.toh.us.CivilizaSim.Display.Research;

import com.toh.us.CivilizaSim.Display.Research.view.OverviewController;
import com.toh.us.CivilizaSim.Display.Research.view.ResearchController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ResearchDisplay implements Runnable {

    public Stage primaryStage = new Stage();
    private BorderPane rootLayout;
    public ResearchDisplay() {

    }

    public void initRootLayout() {
        try {
            //
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/com/toh/us/app/research/overview.fxml"));
            rootLayout = loader.load();

            ResearchController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/com/toh/us/app/research/primary.fxml"));
            BorderPane overview = loader.load();

            rootLayout.setCenter(overview);

            OverviewController controller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        primaryStage.setTitle("Research");
        //primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("")));

        initRootLayout();

        showOverview();
    }

    public void exit() {

    }
}
