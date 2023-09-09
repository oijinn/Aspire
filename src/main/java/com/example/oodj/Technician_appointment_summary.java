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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Technician_appointment_summary implements Initializable {

    private boolean panel_menu_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    private String selected_photo;
    private boolean photo_uploaded = false;

    @FXML
    private Button btn_complete;

    @FXML
    private Button btn_menu_appointment;

    @FXML
    private Button btn_menu_profile;

    @FXML
    private Button btn_menu_sign_out;

    @FXML
    private Button btn_open_menu;

    @FXML
    private Button btn_upload_photo;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_appointment_appliance;

    @FXML
    private Label lbl_appointment_date;

    @FXML
    private Label lbl_appointment_id;

    @FXML
    private Label lbl_appointment_status;

    @FXML
    private Label lbl_appointment_time;

    @FXML
    private Label lbl_appointment_title;

    @FXML
    private Label lbl_change;

    @FXML
    private Label lbl_completed_by;

    @FXML
    private Label lbl_error_feedback;

    @FXML
    private Label lbl_error_payment;

    @FXML
    private Label lbl_photo;

    @FXML
    private Label lbl_price;

    @FXML
    private Label lbl_service_tax;

    @FXML
    private Label lbl_total;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private TextArea txt_description;

    @FXML
    private TextArea txt_feedback;

    @FXML
    private TextField txt_received;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        import_all_details();
        setup_received_text_field();
    }

    @FXML
    void btn_menu_appointment_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment,"Technician_appointment.fxml");
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
    void btn_upload_photo_clicked(MouseEvent event) {

        FileChooser file_chooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        file_chooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        File file = file_chooser.showOpenDialog(null);

        if (file != null){

            selected_photo = file.getAbsolutePath();
            lbl_photo.setText(file.getName());
            photo_uploaded = true;
        }

    }

    @FXML
    void btn_complete_clicked(MouseEvent event) {

        lbl_error_feedback.setVisible(false);
        lbl_error_payment.setVisible(false);

        // check photo
        if (photo_uploaded) {

            feedback_validation();

        } else {

            lbl_error_feedback.setText("**Photo is not uploaded");
            lbl_error_feedback.setVisible(true);

        }


    }

    private void feedback_validation(){

        String customer_feedback = txt_feedback.getText();
        boolean valid_feedback = Feedback.validate_feedback(customer_feedback);

        // check blank
        if (customer_feedback.isEmpty()) {
            lbl_error_feedback.setText("**Cannot be leave blank");
            lbl_error_feedback.setVisible(true);

        } else {

            if(valid_feedback){

                String appointment_id = Appointment.get_id();
                String current_date_time = Clock.get_current_date_time("dd/MM/yyyy h:mm a");

                // complete appointment
                Appointment appointment = new Appointment();
                appointment.find_appointment_with_id(appointment_id);
                appointment.complete_appointment(appointment_id);

                // complete feedback
                Feedback feedback = new Feedback();
                feedback.find_feedback_with_id(appointment.get_feedback_id());
                feedback.complete_feedback(appointment.get_feedback_id(), customer_feedback);

                // complete payment
                Payment payment = new Payment();
                double received = Double.parseDouble(txt_received.getText());
                double change = Double.parseDouble(lbl_change.getText());
                payment.complete_payment(feedback.get_payment_id(), received, change);

                Activity_log.new_activity("complete appointment " + appointment_id.toLowerCase());

                boolean success = send_receipt_to_customer(appointment.get_customer_id(), current_date_time, customer_feedback);

                if(success){
                    lbl_appointment_status.setText("Completed");
                    lbl_completed_by.setText(current_date_time);
                    lbl_photo.setVisible(false);
                    btn_upload_photo.setVisible(false);
                    btn_complete.setVisible(false);
                    txt_feedback.setDisable(true);
                    txt_received.setDisable(true);

                    call_popup_window(btn_complete, "Popup_appointment_completed.fxml");
                }

            } else {
                lbl_error_feedback.setText("**Invalid feedback (No numbers, special characters)");
                lbl_error_feedback.setVisible(true);

            }

        }

    }

    private boolean send_receipt_to_customer(String customer_id, String current_date_time, String customer_feedback){

        String appointment_id = lbl_appointment_id.getText();
        String appointment_status = "Completed";
        String appointment_title = lbl_appointment_title.getText();
        String appointment_date = lbl_appointment_date.getText();
        String appointment_time = lbl_appointment_time.getText();
        String appointment_appliance = lbl_appointment_appliance.getText();
        String appointment_description = txt_description.getText();
        String [] appointment_details = {appointment_id, appointment_status, appointment_title, appointment_date, appointment_time, appointment_appliance, appointment_description};

        String [] feedback_details = {current_date_time, customer_feedback};

        String payment_price = lbl_price.getText();
        String payment_service_tax = lbl_service_tax.getText();
        String payment_total = lbl_total.getText();
        String payment_received = txt_received.getText();
        String payment_change = lbl_change.getText();
        String [] payment_details = {payment_price, payment_service_tax, payment_total, payment_received, payment_change};

        boolean internet_connection_available = Internet.check_internet_connection();

        if (internet_connection_available) {

            try {

                // find customer email
                Customer customer = new Customer();
                customer.find_customer_with_id(customer_id);

                Internet.send_receipt(customer.get_email_address(), appointment_details, feedback_details, payment_details, selected_photo);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;

        } else {
            lbl_error_payment.setText("**Internet connection problem, please try again");
            lbl_error_payment.setVisible(true);

            return false;
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

    private void import_all_details() {

        String appointment_id = Appointment.get_id();

        Appointment appointment = new Appointment();
        appointment.find_appointment_with_id(appointment_id);

        lbl_appointment_id.setText(appointment.get_id().toUpperCase());

        // format status
        String appointment_status = Identifier.identify_appointment_status(appointment.get_status());
        lbl_appointment_status.setText(appointment_status);

        lbl_appointment_title.setText(appointment.get_title());
        lbl_appointment_date.setText(appointment.get_date());

        // find customer
        Customer customer = new Customer();
        customer.find_customer_with_id(appointment.get_customer_id());

        // format time
        String formatted_appointment_time = "NA";
        try {
            formatted_appointment_time = Clock.date_time_formatter(appointment.get_time(), "HH:mm", "h:mm a");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lbl_appointment_time.setText(formatted_appointment_time);

        lbl_appointment_appliance.setText(appointment.get_appliance().toUpperCase() + " (" + appointment.get_number_appliance() + ")" );
        txt_description.setText(appointment.get_description());


        // import feedback details
        Feedback feedback = new Feedback();
        feedback.find_feedback_with_id(appointment.get_feedback_id());

        if (appointment_status.equals("Completed")){
            // format time
            String formatted_feedback_time = "NA";
            try {
                formatted_feedback_time = Clock.date_time_formatter(feedback.get_time(), "HH:mm", "h:mm a");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            lbl_completed_by.setText(feedback.get_date() + "   " + formatted_feedback_time);
            txt_feedback.setText(feedback.get_feedback());
            txt_feedback.setDisable(true);
            btn_complete.setVisible(false);
            lbl_photo.setVisible(false);
            btn_upload_photo.setVisible(false);

        } else if (appointment_status.equals("Cancelled")){
            lbl_completed_by.setText("--");
            txt_feedback.setText(feedback.get_feedback());
            txt_feedback.setDisable(true);
            btn_complete.setVisible(false);
            lbl_photo.setVisible(false);
            btn_upload_photo.setVisible(false);

        } else {
            lbl_completed_by.setText("--");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(appointment.get_date(), formatter);

            // check if current date reach the appointment date
            if (date.compareTo(LocalDate.now()) > 0){
                btn_complete.setVisible(false);
            }
        }

        // import payment details
        Payment payment = new Payment();
        payment.find_payment_with_id(feedback.get_payment_id());

        lbl_price.setText(String.valueOf(payment.get_price()));

        // calculate service tax
        Double service_tax = payment.calculate_service_tax(payment.get_price());
        lbl_service_tax.setText(String.valueOf(service_tax));

        // calculate total
        Double total = service_tax + payment.get_price();
        lbl_total.setText(String.valueOf(total));

        if (appointment_status.equals("Completed") || appointment_status.equals("Cancelled")){
            txt_received.setText(String.valueOf(payment.get_received()));
            txt_received.setDisable(true);
            lbl_change.setText(String.valueOf(payment.get_change()));
        }

    }

    private void setup_received_text_field() {

        txt_received.textProperty().addListener((observable, oldValue, newValue) -> {

            try{

                double received = Double.parseDouble(txt_received.getText());
                double total = Double.parseDouble(lbl_total.getText());

                double change = received - total;
                lbl_change.setText(String.valueOf(change));

                if (change < 0){
                    lbl_error_payment.setText("**Insufficient payment");
                    lbl_error_payment.setVisible(true);
                    btn_complete.setDisable(true);
                } else {
                    lbl_error_payment.setVisible(false);
                    btn_complete.setDisable(false);
                }

            } catch (Exception e){
                lbl_error_payment.setText("**Please insert valid number");
                lbl_error_payment.setVisible(true);
                btn_complete.setDisable(true);
            }

        });

    }
}
