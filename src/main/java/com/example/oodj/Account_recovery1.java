package com.example.oodj;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Account_recovery1 {

    @FXML
    private Button btn_back;

    @FXML
    private ImageView btn_close;

    @FXML
    private Button btn_learn_more;

    @FXML
    private Button btn_proceed;

    @FXML
    private Label lbl_error_email_address;

    @FXML
    private TextField txt_email_address;

    @FXML
    private TextField txt_unmask_password;

    @FXML
    void btn_back_clicked(MouseEvent event) {
        change_scene(btn_back, "Sign_in.fxml");
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

        boolean valid_email_address; // false value
        String user_email_address = txt_email_address.getText().trim();

        //check blank
        if (user_email_address.isEmpty()) {
            lbl_error_email_address.setText("**Cannot be leave blank");
            lbl_error_email_address.setVisible(true);

        } else {
            valid_email_address = User.validate_email_address(user_email_address);

            //validate
            if (valid_email_address) {

                Identifier.identify_user_with_email_address(user_email_address);
                char user = Identifier.get_identity_of_user();

                // if user not available
                if (user == 2) {
                    lbl_error_email_address.setText("**Email address does not exist, please try again");
                    lbl_error_email_address.setVisible(true);

                } else {
                    generate_and_send_code_to_user();

                }

            } else {
                lbl_error_email_address.setText("**Invalid email address");
                lbl_error_email_address.setVisible(true);
            }
        }

    }

    private void generate_and_send_code_to_user() {
        String actual_code = Internet.generate_code();
        Internet.set_code(actual_code);

        boolean internet_connection_available = Internet.check_internet_connection();

        if (internet_connection_available) {

            try {
                Internet.send_code(User.get_email_address(), actual_code);
            } catch (Exception e) {
                e.printStackTrace();
            }

            change_scene(btn_proceed,"Account_recovery2.fxml" );

        } else {
            lbl_error_email_address.setText("**Internet connection problem, please try again");
            lbl_error_email_address.setVisible(true);
        }
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

