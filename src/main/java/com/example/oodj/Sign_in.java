package com.example.oodj;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Sign_in {

    private boolean mask_password = true;
    private Image img_mask = new Image(getClass().getResource("/assets/closed_eye.png").toExternalForm());;
    private Image img_unmask = new Image(getClass().getResource("/assets/eye.png").toExternalForm());;

    @FXML
    private ImageView btn_close;

    @FXML
    private Button btn_forgot_password;

    @FXML
    private Button btn_learn_more;

    @FXML
    private Button btn_sign_in;

    @FXML
    private ImageView btn_unmask;

    @FXML
    private Label lbl_error_email_address;

    @FXML
    private Label lbl_error_password;

    @FXML
    private TextField txt_email_address;

    @FXML
    private PasswordField txt_mask_password;

    @FXML
    private TextField txt_unmask_password;

    @FXML
    void btn_close_clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void btn_forgot_password_clicked(MouseEvent event) {
        change_scene(btn_forgot_password, "Account_recovery1.fxml");
    }

    @FXML
    void btn_learn_more_clicked(MouseEvent event) throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start msedge https://www.youtube.com/channel/UCgibVUyX_h98SL7QngGJaOA"});
    }

    @FXML
    void btn_sign_in_clicked(MouseEvent event) {
        lbl_error_email_address.setVisible(false);
        lbl_error_password.setVisible(false);

        boolean valid_email_address = false;
        String user_email_address = txt_email_address.getText().trim();
        String user_password = txt_mask_password.getText();

        if (!mask_password) {
            user_password = txt_unmask_password.getText();
        }

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

                } else if (user == 1){ // User is technician

                    password_validation(user, user_password);

                } else if ( user == 0 ){ // User is manager

                    password_validation(user, user_password);

                }

            } else {
                lbl_error_email_address.setText("**Invalid email address");
                lbl_error_email_address.setVisible(true);
            }
        }
    }

    @FXML
    void btn_unmask_clicked(MouseEvent event) {
        if (mask_password) {
            btn_unmask.setImage(img_mask);
            txt_unmask_password.setText(txt_mask_password.getText());
            txt_unmask_password.setVisible(true);
            txt_mask_password.setVisible(false);

            mask_password = false;

        } else {
            btn_unmask.setImage(img_unmask);
            txt_mask_password.setText(txt_unmask_password.getText());
            txt_unmask_password.setVisible(false);
            txt_mask_password.setVisible(true);

            mask_password = true;
        }

    }

    private void password_validation(char user, String user_password){

        if (user_password.isEmpty()){
            lbl_error_password.setText("**Cannot be leave blank");
            lbl_error_password.setVisible(true);

        } else {
            if (user_password.equals(User.get_password())) {

                if(user == 0){

                    // add activity log
                    Activity_log.new_activity(User.get_id(), User.get_email_address(), User.get_name(), "sign in");

                    // go to manager homepage
                    change_scene(btn_sign_in, "Manager_appointment.fxml");

                } else if (user == 1){

                    // add activity log
                    Activity_log.new_activity(User.get_id(), User.get_email_address(), User.get_name(), "sign in");

                    // go to technician homepage
                    change_scene(btn_sign_in, "Technician_appointment.fxml");

                }

            } else {
                lbl_error_password.setText("**Invalid password");
                lbl_error_password.setVisible(true);
            }

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
