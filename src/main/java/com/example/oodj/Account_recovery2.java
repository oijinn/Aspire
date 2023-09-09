package com.example.oodj;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Account_recovery2 {

    @FXML
    private Button btn_back;

    @FXML
    private ImageView btn_close;

    @FXML
    private Button btn_learn_more;

    @FXML
    private Button btn_proceed;

    @FXML
    private Button btn_resend;

    @FXML
    private Label lbl_error_code;

    @FXML
    private TextField txt_code;

    @FXML
    private TextField txt_unmask_password;

    @FXML
    void btn_back_clicked(MouseEvent event) {
        change_scene(btn_back,"Account_recovery1.fxml" );
    }

    @FXML
    void btn_close_clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void btn_learn_more_clicked(MouseEvent event) throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start msedge https://www.youtube.com/channel/UCgibVUyX_h98SL7QngGJaOA"});
    }

    @FXML
    void btn_proceed_clicked(MouseEvent event) {
        String actual_code = Internet.get_code();
        String input_code = txt_code.getText().trim();

        //check blank
        if (input_code.isEmpty()){
            lbl_error_code.setText("**Cannot be leave blank");
            lbl_error_code.setVisible(true);

        } else {

            if (input_code.equals(actual_code)){
                change_scene(btn_back, "Account_recovery3.fxml");

            } else {
                lbl_error_code.setText("**Wrong code, please try again");
                lbl_error_code.setVisible(true);
            }

        }

    }

    @FXML
    void btn_resend_clicked(MouseEvent event) throws Exception {
        String actual_code = Internet.generate_code();
        Internet.set_code(actual_code);
        Internet.send_code(User.get_email_address(), actual_code);

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
