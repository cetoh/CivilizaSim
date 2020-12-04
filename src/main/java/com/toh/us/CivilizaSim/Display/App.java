package com.toh.us.CivilizaSim.Display;

import com.toh.us.CivilizaSim.CivilizaSim;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private double dx;
    private double dy;
    private boolean resizeBottom = false;

    public static void main(String[] args) {launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/com/toh/us/app/primary.fxml"));
        Parent root = loader.load();

        PrimaryController controller = loader.getController();
        controller.setStage(stage);

        setMousePressActions(root,stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(650);

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    private void setMousePressActions(Parent root, Stage stage) {
        root.setOnMousePressed(event  -> {
            if (event.getX() > stage.getWidth() - 10
            && event.getX() < stage.getWidth() + 10
            && event.getY() > stage.getHeight() - 10
            && event.getY() < stage.getHeight() + 10) {
                resizeBottom = true;
                dx = stage.getWidth() - event.getX();
                dy = stage.getHeight() - event.getY();
                ((Node) event.getSource()).setCursor(Cursor.SE_RESIZE);
            } else {
                resizeBottom = false;
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseMoved(event -> {
            if (event.getX() > stage.getWidth() - 10
                    && event.getX() < stage.getWidth() + 10
                    && event.getY() > stage.getHeight() - 10
                    && event.getY() < stage.getHeight() + 10) {
                ((Node) event.getSource()).setCursor(Cursor.SE_RESIZE);
            } else {
                ((Node) event.getSource()).setCursor(Cursor.DEFAULT);
            }
        });

        root.setOnMouseDragged(event -> {
            if (!resizeBottom) {
                stage.setX(event.getSceneX() - xOffset);
                stage.setY(event.getSceneY() - yOffset);
                ((Node) event.getSource()).setCursor(Cursor.MOVE);
            } else {
                if (stage.getWidth() < stage.getMinWidth()) {
                    stage.setWidth(stage.getMinWidth());
                }
                if (stage.getHeight() < stage.getMinHeight()) {
                    stage.setHeight(stage.getMinHeight());
                }

                //Allow stage resize to minimum size and nothing smaller
                if (stage.getWidth() > stage.getMinWidth() && stage.getWidth() < stage.getMaxWidth()) {
                    if (event.getX() + dx > stage.getMinWidth()) {
                        stage.setWidth(event.getX() + dx);
                    }
                    if (event.getY() + dy > stage.getMinWidth()) {
                        stage.setHeight(event.getY() + dy);
                    }
                }
            }
        });

        root.setOnMouseReleased(event -> ((Node) event.getSource()).setCursor(Cursor.DEFAULT));
    }
}
