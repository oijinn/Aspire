package com.example.oodj;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Manager_management_report implements Initializable {

    private boolean panel_menu_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    private String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] years = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};

    @FXML
    private ChoiceBox<String> cbox_month;

    @FXML
    private ChoiceBox<String> cbox_year;

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
    private Button btn_print;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_report_title;

    @FXML
    private Label lbl_total_amount;

    @FXML
    private Label lbl_total_appliance;

    @FXML
    private Label lbl_total_appointment;

    @FXML
    private Label lbl_total_cancelled;

    @FXML
    private Label lbl_total_change;

    @FXML
    private Label lbl_total_completed;

    @FXML
    private Label lbl_total_large_appliance;

    @FXML
    private Label lbl_total_pending;

    @FXML
    private Label lbl_total_price;

    @FXML
    private Label lbl_total_received;

    @FXML
    private Label lbl_total_service_tax;

    @FXML
    private Label lbl_total_small_appliance;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private AnchorPane panel_report;

    @FXML
    void btn_menu_appointment_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment, "Manager_appointment.fxml");
    }

    @FXML
    void btn_menu_management_clicked(MouseEvent event) {
        change_scene(btn_menu_management, "Manager_management.fxml");
    }

    @FXML
    void btn_menu_profile_clicked(MouseEvent event) {
        change_scene(btn_menu_profile, "Manager_profile.fxml");
    }

    @FXML
    void btn_menu_sign_out_clicked(MouseEvent event) {
        Activity_log.new_activity( "sign out");
        change_scene(btn_menu_sign_out,"Sign_in.fxml");
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
    void btn_print_clicked(MouseEvent event) {

        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0, 0, 0, 0);

            boolean success = printerJob.printPage(pageLayout, panel_report);
            if (success) {
                printerJob.endJob();
                Activity_log.new_activity( "generate report " + lbl_report_title.getText().toLowerCase());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // choice box includes interaction with report generation
        setup_choice_box();

    }

    private void setup_choice_box(){

        cbox_year.getItems().addAll(years);
        cbox_month.getItems().addAll(months);

        cbox_year.setOnAction(event -> {
            cbox_month.setDisable(false);
            cbox_month.getSelectionModel().clearSelection();
            btn_print.setVisible(false);

            reset_report();
        });

        cbox_month.setOnAction(event -> {

            btn_print.setVisible(true);

            String selected_year = cbox_year.getValue();
            String selected_month = cbox_month.getValue();

            if(selected_month != null){

                switch (Integer.parseInt(selected_month)){
                    case 1:
                        lbl_report_title.setText("January report (" + selected_year + ")");
                        break;
                    case 2:
                        lbl_report_title.setText("February report (" + selected_year + ")");
                        break;
                    case 3:
                        lbl_report_title.setText("March report (" + selected_year + ")");
                        break;
                    case 4:
                        lbl_report_title.setText("April report (" + selected_year + ")");
                        break;
                    case 5:
                        lbl_report_title.setText("May report (" + selected_year + ")");
                        break;
                    case 6:
                        lbl_report_title.setText("June report (" + selected_year + ")");
                        break;
                    case 7:
                        lbl_report_title.setText("July report (" + selected_year + ")");
                        break;
                    case 8:
                        lbl_report_title.setText("August report (" + selected_year + ")");
                        break;
                    case 9:
                        lbl_report_title.setText("September report (" + selected_year + ")");
                        break;
                    case 10:
                        lbl_report_title.setText("October report (" + selected_year + ")");
                        break;
                    case 11:
                        lbl_report_title.setText("November report (" + selected_year + ")");
                        break;
                    case 12:
                        lbl_report_title.setText("December report (" + selected_year + ")");
                        break;
                }

                Appointment appointment = new Appointment();

                // collect details for report generation
                int[] appointment_details = appointment.find_appointment_details_with_month_year(selected_month, selected_year);

                lbl_total_appointment.setText(String.valueOf(appointment_details[0]));
                lbl_total_pending.setText(String.valueOf(appointment_details[1]));
                lbl_total_completed.setText(String.valueOf(appointment_details[2]));
                lbl_total_cancelled.setText(String.valueOf(appointment_details[3]));

                lbl_total_appliance.setText(String.valueOf(appointment_details[4]));
                lbl_total_small_appliance.setText(String.valueOf(appointment_details[5]));
                lbl_total_large_appliance.setText(String.valueOf(appointment_details[6]));

                // collect feedback id
                String[] feedback_id = appointment.find_feedback_id_with_month_year(selected_month, selected_year);
                Feedback feedback = new Feedback();
                String[] payment_id = feedback.find_payment_id_with_feedback_id(feedback_id);

                    // find payment details using payment id
                Payment payment = new Payment();
                double[] feedback_details = payment.find_payment_details_with_id(payment_id);
                lbl_total_price.setText(String.valueOf(feedback_details[0]));
                lbl_total_service_tax.setText(String.valueOf(feedback_details[1]));
                lbl_total_amount.setText(String.valueOf(feedback_details[2]));
                lbl_total_received.setText(String.valueOf(feedback_details[3]));
                lbl_total_change.setText(String.valueOf(feedback_details[4]));
            }

        });

    }

    private void reset_report(){

        lbl_report_title.setText("--");
        lbl_total_appointment.setText("--");
        lbl_total_pending.setText("--");
        lbl_total_completed.setText("--");
        lbl_total_cancelled.setText("--");

        lbl_total_appliance.setText("--");
        lbl_total_small_appliance.setText("--");
        lbl_total_large_appliance.setText("--");

        lbl_total_price.setText("--");
        lbl_total_service_tax.setText("--");
        lbl_total_amount.setText("--");
        lbl_total_received.setText("--");
        lbl_total_change.setText("--");

    }


}
