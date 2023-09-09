package com.example.oodj;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Manager_management_customer_details implements Initializable {

    private boolean panel_menu_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    private ObservableList<Appointment_table_data> raw_table_data = FXCollections.observableArrayList();
    private ObservableList<Appointment_table_data> clean_table_data = FXCollections.observableArrayList();

    @FXML
    private TableView<Appointment_table_data> table;

    @FXML
    private TableColumn<Appointment_table_data, String> column_date;

    @FXML
    private TableColumn<Appointment_table_data, String> column_id;

    @FXML
    private TableColumn<Appointment_table_data, String> column_status;

    @FXML
    private TableColumn<Appointment_table_data, String> column_title;

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
    private Button btn_update;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_customer_ic;

    @FXML
    private Label lbl_customer_id;

    @FXML
    private Label lbl_customer_name;

    @FXML
    private Label lbl_error_email_address;

    @FXML
    private Label lbl_error_internet;

    @FXML
    private Label lbl_error_phone_number;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private TextField txt_customer_email_address;

    @FXML
    private TextField txt_customer_phone_number;

    @FXML
    private TextField txt_search;

    @FXML
    void btn_menu_appointment_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment, "Manager_appointment.fxml");
    }

    @FXML
    void btn_menu_management_clicked(MouseEvent event) {
        change_scene(btn_menu_management,"Manager_management.fxml");
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
    void btn_update_clicked(MouseEvent event) {

        lbl_error_phone_number.setVisible(false);
        lbl_error_email_address.setVisible(false);

        String customer_phone_number = txt_customer_phone_number.getText();
        boolean valid_phone_number = User.validate_phone_number(customer_phone_number);

        // check blank
        if (customer_phone_number.isEmpty()) {
            lbl_error_phone_number.setText("**Cannot be leave blank");
            lbl_error_phone_number.setVisible(true);

        } else {

            if(valid_phone_number){

                email_address_validation();

            } else {
                lbl_error_phone_number.setText("**Invalid phone number");
                lbl_error_phone_number.setVisible(true);

            }

        }

    }

    private void email_address_validation(){

        String customer_email_address = txt_customer_email_address.getText();
        boolean valid_email_address = User.validate_email_address(customer_email_address);

        // check blank
        if (customer_email_address.isEmpty()) {
            lbl_error_email_address.setText("**Cannot be leave blank");
            lbl_error_email_address.setVisible(true);

        } else {

            if(valid_email_address){

                String customer_id = lbl_customer_id.getText().toLowerCase();
                String customer_phone_number = txt_customer_phone_number.getText();

                Customer customer = new Customer();
                customer.edit_phone_number_and_email_address(customer_id, customer_phone_number, customer_email_address);

                Activity_log.new_activity("update profile of " + customer_id);

                call_popup_window(btn_update, "Popup_update_success.fxml");

            } else {
                lbl_error_email_address.setText("**Invalid email address");
                lbl_error_email_address.setVisible(true);

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

        // import selected customer profile
        import_selected_customer_profile();

        // setup table
        import_data_to_table();
        reverse_table_record();
        setup_table_selection_event();

        setup_search_bar();

    }

    private void import_selected_customer_profile(){

        String customer_id = Customer.get_id();

        Customer customer = new Customer();
        customer.find_customer_with_id(customer_id);

        lbl_customer_name.setText(customer.get_name().toUpperCase());
        lbl_customer_id.setText(customer.get_id().toUpperCase());
        lbl_customer_ic.setText(customer.get_ic());
        txt_customer_phone_number.setText(customer.get_phone_number());

        txt_customer_email_address.setText(customer.get_email_address());

    }

    private void import_data_to_table() {

        String customer_id = Customer.get_id();

        Appointment appointment = new Appointment();

        Collection<Appointment_table_data> list = null;
        try {
            list = Files.readAllLines(new File(appointment.get_file_path()).toPath())
                    .stream()
                    .map(line -> {
                        String[] details = line.split(",");

                        Appointment_table_data appointment_table_data = new Appointment_table_data();

                            if (details[2].equals(customer_id)){

                                appointment_table_data.setID(details[0].toUpperCase());
                                appointment_table_data.set_title(details[5]);
                                appointment_table_data.set_date(details[6]);

                                // format status
                                String formatted_status = Identifier.identify_appointment_status(details[11]);

                                appointment_table_data.set_status(formatted_status);

                            }

                        return appointment_table_data;

                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        raw_table_data = FXCollections.observableArrayList(list);

        column_id.setCellValueFactory(data -> data.getValue().id_property());
        column_title.setCellValueFactory(data -> data.getValue().title_property());
        column_date.setCellValueFactory(data -> data.getValue().date_property());
        column_status.setCellValueFactory(data -> data.getValue().status_property());

        remove_null_row();
    }

    private void remove_null_row(){

        for (Appointment_table_data appointment_data : raw_table_data) {
            if(appointment_data.getID() != null)
                clean_table_data.add(appointment_data);
        }

        table.setItems(clean_table_data);

        // Clear unused data
        raw_table_data = null;
    }

    private void reverse_table_record(){

        column_id.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().setAll(column_id);
    }

    private void setup_table_selection_event(){
        table.setRowFactory(tv -> {
            TableRow<Appointment_table_data> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Appointment_table_data rowData = row.getItem();

                    Appointment appointment = new Appointment(rowData.getID().toLowerCase());
                    change_scene(btn_update, "Manager_appointment_details.fxml");
                }
            });
            return row ;
        });
    }

    private void setup_search_bar(){
        // Search bar function
        txt_search.textProperty().addListener((obs, oldText, newText) -> {

            String keyword = txt_search.getText().toLowerCase();
            if (keyword.equals("")) {
                table.setItems(clean_table_data);
            } else {
                ObservableList<Appointment_table_data> filteredData = FXCollections.observableArrayList();
                for (Appointment_table_data appointment_data : clean_table_data) {
                    if(appointment_data.getID().toLowerCase().contains(keyword) || appointment_data.get_title().toLowerCase().contains(keyword))
                        filteredData.add(appointment_data);
                }

                table.setItems(filteredData);
            }
        });
    }

}
