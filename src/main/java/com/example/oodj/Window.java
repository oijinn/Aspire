package com.example.oodj;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Window {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public Window() {

    }

    public void change_scene(Button button, String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(page));

        Stage stage =(Stage) button.getScene().getWindow();
        Window.allow_drag(stage, root);
        stage.setScene(new Scene(root));

    }

    public static void allow_drag(Stage stage, Parent root){
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
    }

}
