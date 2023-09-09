package com.example.oodj;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Popup_email_verification {

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_proceed;

    @FXML
    private Button btn_resend;

    @FXML
    private TextField txt_code;

    @FXML
    private Label lbl_error_code;

    @FXML
    void btn_cancel_clicked(MouseEvent event) {

        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();

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

                String user_id = User.get_id();
                String user_password = User.get_password();
                String user_phone_number = User.get_phone_number();

                Identifier.identify_user_with_id(user_id);
                char user = Identifier.get_identity_of_user();

                if (user == 0 ){

                    Manager manager = new Manager();
                    manager.edit_password_and_phone_number(user_id, user_password,user_phone_number);
                    Activity_log.new_activity("update profile of " + User.get_id());

                } else if (user == 1) {

                    Technician technician = new Technician();
                    technician.edit_password_and_phone_number(user_id, user_password,user_phone_number);
                    Activity_log.new_activity("update profile");
                }

                call_popup_window(btn_proceed, "Popup_update_success.fxml");

                Stage stage = (Stage) btn_cancel.getScene().getWindow();
                stage.close();

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

    private void call_popup_window(Button button, String page){

        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(button.getScene().getWindow());
        stage.initStyle(StageStyle.UNDECORATED);
        Window.allow_drag(stage, root);

        stage.showAndWait();

    }

}
