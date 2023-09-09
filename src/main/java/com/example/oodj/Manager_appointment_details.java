package com.example.oodj;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class Manager_appointment_details implements Initializable {

    private boolean panel_menu_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    @FXML
    private Button btn_abort;

    @FXML
    private Button btn_menu_appointment;

    @FXML
    private Button btn_menu_management;

    @FXML
    private Button btn_menu_profile;

    @FXML
    private Button btn_menu_sign_out;

    @FXML
    private Button btn_open_menu;

    @FXML
    private Button btn_summary;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_appointment_appliance;

    @FXML
    private Label lbl_appointment_customer;

    @FXML
    private Label lbl_appointment_date;

    @FXML
    private Label lbl_appointment_id;

    @FXML
    private Label lbl_appointment_manager;

    @FXML
    private Label lbl_appointment_status;

    @FXML
    private Label lbl_appointment_technician;

    @FXML
    private Label lbl_appointment_time;

    @FXML
    private Label lbl_appointment_title;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private TextArea txt_description;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // import appointment details
        import_appointment_details();

    }

    @FXML
    void btn_menu_appointment_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment,"Manager_appointment.fxml");
    }

    @FXML
    void btn_menu_management_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment, "Manager_management.fxml");
    }

    @FXML
    void btn_menu_profile_clicked(MouseEvent event) {
        change_scene(btn_menu_profile, "Manager_profile.fxml");
    }

    @FXML
    void btn_menu_sign_out_clicked(MouseEvent event) {
        Activity_log.new_activity("sign out");
        change_scene(btn_menu_sign_out, "Sign_in.fxml");
    }

    @FXML
    void btn_open_menu_clicked(MouseEvent event) {
        if(panel_menu_expanded){
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(panel_menu);

            slide.setToX(-260);
            slide.play();

            panel_menu.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {

                img_expand_menu.setImage(img_hamburger_menu);
                img_expand_menu.setFitHeight(46);
                img_expand_menu.setFitWidth(46);

                panel_menu_expanded = false;
            });

        } else {

            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(panel_menu);

            slide.setToX(0);
            slide.play();

            panel_menu.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)->{

                img_expand_menu.setImage(img_close_menu);
                img_expand_menu.setFitHeight(25);
                img_expand_menu.setFitWidth(25);

                panel_menu_expanded = true;
            });

        }
    }

    @FXML
    void btn_summary_clicked(MouseEvent event) {

        change_scene(btn_summary, "Manager_appointment_summary.fxml");

    }

    @FXML
    void btn_abort_clicked(MouseEvent event) {

        String appointment_id = Appointment.get_id();

        Appointment appointment = new Appointment();
        appointment.abort_appointment(appointment_id);

        Activity_log.new_activity("abort appointment " + appointment_id);

        call_popup_window(btn_abort, "Popup_abort_success.fxml");

        lbl_appointment_status.setText("Cancelled");
        btn_abort.setVisible(false);
    }

    private void change_scene(Button button, String page){
        Window window = new Window();
        try {
            window.change_scene(button, page);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private void import_appointment_details() {

        String appointment_id = Appointment.get_id();

        Appointment appointment = new Appointment();
        appointment.find_appointment_with_id(appointment_id);

        lbl_appointment_id.setText(appointment.get_id().toUpperCase());

        // format status
        String appointment_status = Identifier.identify_appointment_status(appointment.get_status());
        lbl_appointment_status.setText(appointment_status);

        // disable abort button if status = cancelled (1) or = completed (2)
        if (appointment_status.equals("Cancelled") || appointment_status.equals("Completed")){
            btn_abort.setVisible(false);
        }

        // find customer
        Customer customer = new Customer();
        customer.find_customer_with_id(appointment.get_customer_id());
        lbl_appointment_customer.setText(customer.get_name().toUpperCase() + " (" + customer.get_id().toUpperCase() + ")");

        // find technician
        Technician technician = new Technician();
        technician.find_user_with_id(appointment.get_technician_id());
        lbl_appointment_technician.setText(technician.get_name().toUpperCase() + " (" + technician.get_id().toUpperCase() + ")" );

        // find manager
        Manager manager = new Manager();
        manager.find_user_with_id(appointment.get_manager_id());
        lbl_appointment_manager.setText(manager.get_name().toUpperCase() + " (" + manager.get_id().toUpperCase() + ")");

        lbl_appointment_title.setText(appointment.get_title());
        lbl_appointment_date.setText(appointment.get_date());

        // format time
        String formatted_time = "NA";
        try {
            formatted_time = Clock.date_time_formatter(appointment.get_time(), "HH:mm", "h:mm a");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lbl_appointment_time.setText(formatted_time);

        lbl_appointment_appliance.setText(appointment.get_appliance().toUpperCase() + " (" + appointment.get_number_appliance() + ")" );
        txt_description.setText(appointment.get_description());

    }
}
