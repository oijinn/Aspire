package com.example.oodj;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Popup_sign_up_success {

    @FXML
    private Button btn_confirm;

    @FXML
    void btn_confirm_clicked(MouseEvent event) {

        Stage stage = (Stage) btn_confirm.getScene().getWindow();
        stage.close();

    }

}
