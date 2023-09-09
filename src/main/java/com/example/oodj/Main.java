package com.example.oodj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Sign_in.fxml"));
        Scene scene = new javafx.scene.Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);

        Window.allow_drag(stage, root);

        String image = this.getClass().getResource("/assets/aspire_rounded_logo.png").toExternalForm();
        stage.getIcons().add(new Image(image));
        stage.setTitle("Aspire");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}