package com.example.oodj;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.ResourceBundle;

public class Manager_management_registration implements Initializable {

    private boolean panel_menu_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    private String[] user = {"Customer", "Technician", "Manager"};
    private char identity = 2;  // 0 = manager, 1 = technician, 2 = customer

    @FXML
    private ChoiceBox<String> cbox_register_for;

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
    private Button btn_sign_up;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_error_email_address;

    @FXML
    private Label lbl_error_ic;

    @FXML
    private Label lbl_error_name;

    @FXML
    private Label lbl_password;

    @FXML
    private Label lbl_error_password;

    @FXML
    private Label lbl_error_phone_number;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private TextField txt_email_address;

    @FXML
    private TextField txt_ic;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_phone_number;

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
    void btn_sign_up_clicked(MouseEvent event) {

        lbl_error_name.setVisible(false);
        lbl_error_ic.setVisible(false);
        lbl_error_phone_number.setVisible(false);
        lbl_error_email_address.setVisible(false);
        lbl_error_password.setVisible(false);

        start_validation();

    }

    private void start_validation(){  // name validation

        String name = txt_name.getText();

        // check blank
        if(name.isEmpty()){
            lbl_error_name.setText("**Cannot be leave blank");
            lbl_error_name.setVisible(true);

        } else {

            boolean valid_name = User.validate_name(name);

            if(valid_name){

                ic_validation();

            } else {
                lbl_error_name.setText("**Invalid name");
                lbl_error_name.setVisible(true);
            }

        }

    }

    private void ic_validation(){

        String ic = txt_ic.getText();

        // check blank
        if(ic.isEmpty()){
            lbl_error_ic.setText("**Cannot be leave blank");
            lbl_error_ic.setVisible(true);

        } else {

            boolean valid_ic = User.validate_ic(ic);

            if(valid_ic){

                if(identity == 2){

                    Customer customer = new Customer();
                    customer.find_customer_with_ic(ic);
                    boolean ic_exist = customer.get_exist();

                    if (ic_exist){

                        lbl_error_ic.setText("**No. identity card exist in customer database");
                        lbl_error_ic.setVisible(true);

                    } else {
                        phone_number_validation();
                    }

                } else if (identity == 1){

                    Technician technician = new Technician();
                    technician.find_user_with_ic(ic);
                    boolean ic_exist = technician.get_exist();

                    if (ic_exist){
                        lbl_error_ic.setText("**No. identity card exist in technician database");
                        lbl_error_ic.setVisible(true);

                    } else {
                        phone_number_validation();
                    }

                } else if (identity == 0){

                    Manager manager = new Manager();
                    manager.find_user_with_ic(ic);
                    boolean ic_exist = manager.get_exist();

                    if (ic_exist){
                        lbl_error_ic.setText("**No. identity card exist in manager database");
                        lbl_error_ic.setVisible(true);
                    } else {
                        phone_number_validation();
                    }

                }

            } else {
                lbl_error_ic.setText("**Invalid no. identity card");
                lbl_error_ic.setVisible(true);
            }

        }

    }

    private void phone_number_validation(){

        String phone_number = txt_phone_number.getText();

        // check blank
        if(phone_number.isEmpty()){
            lbl_error_phone_number.setText("**Cannot be leave blank");
            lbl_error_phone_number.setVisible(true);

        } else {

            boolean valid_phone_number = User.validate_phone_number(phone_number);

            if(valid_phone_number){

                email_address_validation();

            } else {
                lbl_error_phone_number.setText("**Invalid phone number");
                lbl_error_phone_number.setVisible(true);
            }

        }

    }

    private void email_address_validation(){

        String email_address = txt_email_address.getText();

        // check blank
        if(email_address.isEmpty()){
            lbl_error_email_address.setText("**Cannot be leave blank");
            lbl_error_email_address.setVisible(true);

        } else {

            boolean valid_email_address = User.validate_email_address(email_address);

            if(valid_email_address){

                if(identity == 2){
                    String name = txt_name.getText().toLowerCase();
                    String ic = txt_ic.getText();
                    String phone_number = txt_phone_number.getText();

                    Customer customer = new Customer();
                    customer.registration(name, ic, phone_number,email_address);
                    Activity_log.new_activity("sign up account for " + Customer.get_id());
                    call_popup_window(btn_sign_up, "Popup_sign_up_success.fxml");

                    reset();

                } else if (identity == 1 || identity == 0) {

                    Technician technician = new Technician();
                    technician.find_user_with_email_address(email_address);
                    boolean email_address_exist = technician.get_exist();

                    if (email_address_exist){
                        lbl_error_email_address.setText("**Email address exist in technician database");
                        lbl_error_email_address.setVisible(true);

                    } else {

                        Manager manager = new Manager();
                        manager.find_user_with_email_address(email_address);
                        email_address_exist = manager.get_exist();

                        if (email_address_exist){
                            lbl_error_email_address.setText("**Email address exist in manager database");
                            lbl_error_email_address.setVisible(true);

                        } else {
                            password_validation();
                        }
                    }

                }

            } else {
                lbl_error_email_address.setText("**Invalid email address");
                lbl_error_email_address.setVisible(true);
            }

        }

    }

    private void password_validation() {

        String password = txt_password.getText();

        // check blank
        if (password.isEmpty()){
            lbl_error_password.setText("**Cannot be leave blank");
            lbl_error_password.setVisible(true);

        } else {

            boolean valid_password = User.validate_password(password);

            //validate
            if (valid_password){

                String name = txt_name.getText().toLowerCase();
                String ic = txt_ic.getText();
                String phone_number = txt_phone_number.getText();
                String email_address = txt_email_address.getText();

                if (identity == 1) {
                    Technician technician = new Technician();
                    technician.registration(name, ic, phone_number,email_address, password);
                    Activity_log.new_activity("sign up account for " + User.get_id());
                    call_popup_window(btn_sign_up, "Popup_sign_up_success.fxml");

                    reset();

               } else if (identity == 0) {
                   Manager manager = new Manager();
                   manager.registration(name, ic, phone_number,email_address, password);
                   Activity_log.new_activity("sign up account for " + User.get_id());
                   call_popup_window(btn_sign_up, "Popup_sign_up_success.fxml");

                    reset();
               }

            } else {
                lbl_error_password.setText("**Invalid password(Contain 8 characters above with numbers, No spaces allowed)");
                lbl_error_password.setVisible(true);
            }

        }

    }

    private void reset(){
        txt_name.clear();
        txt_ic.clear();
        txt_phone_number.clear();
        txt_email_address.clear();
        txt_password.clear();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // setup choice box
        setup_choice_box();

    }

    private void setup_choice_box(){

        cbox_register_for.getItems().addAll(user);
        cbox_register_for.getSelectionModel().selectFirst();

        cbox_register_for.setOnAction(event -> {

            lbl_error_password.setVisible(false);

            if(cbox_register_for.getValue().toLowerCase().equals("customer")){

                identity = 2;
                lbl_password.setVisible(false);
                txt_password.setVisible(false);

            } else if (cbox_register_for.getValue().toLowerCase().equals("technician")){

                identity = 1;
                lbl_password.setVisible(true);
                txt_password.setVisible(true);


            } else if (cbox_register_for.getValue().toLowerCase().equals("manager")){

                identity = 0;
                lbl_password.setVisible(true);
                txt_password.setVisible(true);

            }

        });

    }


}
