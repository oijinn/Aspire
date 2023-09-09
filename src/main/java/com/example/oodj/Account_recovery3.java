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

public class Account_recovery3 {

    private boolean mask_password = true;
    private boolean mask_confirm_password = true;
    private Image img_mask = new Image(getClass().getResource("/assets/closed_eye.png").toExternalForm());;
    private Image img_unmask = new Image(getClass().getResource("/assets/eye.png").toExternalForm());;

    @FXML
    private Button btn_back;

    @FXML
    private ImageView btn_close;

    @FXML
    private Button btn_learn_more;

    @FXML
    private Button btn_proceed;

    @FXML
    private ImageView btn_unmask;

    @FXML
    private ImageView btn_unmask_confirm_password;

    @FXML
    private Label lbl_error_confirm_password;

    @FXML
    private Label lbl_error_password;

    @FXML
    private PasswordField txt_mask_confirm_password;

    @FXML
    private PasswordField txt_mask_password;

    @FXML
    private TextField txt_unmask_confirm_password;

    @FXML
    private TextField txt_unmask_password;

    @FXML
    private TextField txt_unmask_password1;

    @FXML
    void btn_back_clicked(MouseEvent event) {
        change_scene(btn_back,"Sign_in.fxml" );
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

        lbl_error_password.setVisible(false);
        lbl_error_confirm_password.setVisible(false);

        if (mask_password) {
            String user_password = txt_mask_password.getText();
            password_validation(user_password);

        } else {
            String user_password = txt_unmask_password.getText();
            password_validation(user_password);
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

    @FXML
    void btn_unmask_confirm_password_clicked(MouseEvent event) {

        if (mask_confirm_password) {
            btn_unmask_confirm_password.setImage(img_mask);
            txt_unmask_confirm_password.setText(txt_mask_confirm_password.getText());
            txt_unmask_confirm_password.setVisible(true);
            txt_mask_confirm_password.setVisible(false);

            mask_confirm_password = false;

        } else {
            btn_unmask_confirm_password.setImage(img_unmask);
            txt_mask_confirm_password.setText(txt_unmask_confirm_password.getText());
            txt_unmask_confirm_password.setVisible(false);
            txt_mask_confirm_password.setVisible(true);

            mask_confirm_password = true;
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

    private void password_validation(String user_password){
        boolean valid_password = User.validate_password(user_password);

        //validate
        if (valid_password){
            lbl_error_password.setVisible(false);

            if (mask_confirm_password) {
                String user_confirm_password = txt_mask_confirm_password.getText();

                confirm_password_validation(user_confirm_password, user_password);

            } else {
                String user_confirm_password = txt_unmask_confirm_password.getText();

                confirm_password_validation(user_confirm_password, user_password);
            }

        } else {
            lbl_error_password.setText("**Invalid password(Contain 8 characters above with numbers, No spaces allowed)");
            lbl_error_password.setVisible(true);
        }
    }

    private void confirm_password_validation(String user_confirm_password, String user_password){
        //confirm password is same?
        if (user_confirm_password.equals(user_password)){

            change_password(user_confirm_password);


        } else {
            lbl_error_confirm_password.setText("Password is not the same, please try again");
            lbl_error_confirm_password.setVisible(true);
        }
    }

    private void change_password(String new_password){

        String user_id = User.get_id();
        String user_email_address = User.get_email_address();

        Identifier.identify_user_with_email_address(user_email_address);
        char user = Identifier.get_identity_of_user();

        // if user is manager
        if (user == 0) {

            Manager manager = new Manager();
            manager.edit_password(user_id, new_password);

            Activity_log.new_activity(User.get_id(), User.get_email_address(), User.get_name(), "reset password");
            change_scene(btn_proceed,"Account_recovery4.fxml" );

        } else if (user == 1) { //if user is technician

            Technician technician = new Technician();
            technician.edit_password(user_id, new_password);

            Activity_log.new_activity(User.get_id(), User.get_email_address(), User.get_name(), "reset password");
            change_scene(btn_proceed,"Account_recovery4.fxml" );

        }
    }


}
