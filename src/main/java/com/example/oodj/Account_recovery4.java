package com.example.oodj;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Account_recovery4 {

    @FXML
    private ImageView btn_close;

    @FXML
    private Button btn_confirm;

    @FXML
    private Button btn_learn_more;

    @FXML
    void btn_close_clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void btn_confirm_clicked(MouseEvent event) {
        change_scene(btn_confirm,"Sign_in.fxml");
    }

    @FXML
    void btn_learn_more_clicked(MouseEvent event) throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start msedge https://www.youtube.com/channel/UCgibVUyX_h98SL7QngGJaOA"});
    }

    private void change_scene(Button button, String page){
        Window window = new Window();
        try {
            window.change_scene(button, page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
