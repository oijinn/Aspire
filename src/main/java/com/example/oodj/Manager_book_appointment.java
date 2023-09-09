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
import javafx.util.StringConverter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Manager_book_appointment implements Initializable {

    private boolean customer_selected = false;
    private boolean date_selected = false;
    private boolean time_selected = false;
    private boolean technician_selected = false;

    private String selected_customer_id = null;

    private String[] available_time = {"10:00", "12:00", "14:00", "16:00"};
    private String[] appliance = {"Small", "Large"};
    private String[] number_appliance = {"1", "2", "3", "4", "5"};

    private boolean panel_menu_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    private ObservableList<Customer_table_data> customer_table_data = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer_table_data> table_customer;

    @FXML
    private TableColumn<Customer_table_data, String> column_customer_ic;

    @FXML
    private TableColumn<Customer_table_data, String> column_customer_id;

    @FXML
    private TableColumn<Customer_table_data, String> column_customer_name;

    @FXML
    private ChoiceBox<String> cbox_appliance;

    @FXML
    private ChoiceBox<String> cbox_number_appliance;

    @FXML
    private ChoiceBox<String> cbox_time;

    @FXML
    private ChoiceBox<String> cbox_technician;

    @FXML
    private Button btn_book;

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
    private DatePicker date_picker_date;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_error_date;

    @FXML
    private Label lbl_error_description;

    @FXML
    private Label lbl_error_time;

    @FXML
    private Label lbl_error_customer;

    @FXML
    private Label lbl_error_technician;

    @FXML
    private Label lbl_error_title;

    @FXML
    private Label lbl_password;

    @FXML
    private Label lbl_selected_customer;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private TextField txt_customer_search;

    @FXML
    private TextArea txt_description;

    @FXML
    private TextField txt_title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // setup table
        import_customer_data_to_table();

        reverse_table_record();
        setup_table_selection_event();

        // setup search bar
        setup_search_bar();

        // setup date picker
        setup_date_picker();

        // setup time choice box
        setup_time_choice_box();

        // setup appliance choice box
        setup_appliance_choice_box();
    }

    @FXML
    void btn_menu_appointment_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment,"Manager_appointment.fxml");
    }

    @FXML
    void btn_menu_management_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment,"Manager_management.fxml");
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
    void btn_book_clicked(MouseEvent event) {

        lbl_error_customer.setVisible(false);
        lbl_error_date.setVisible(false);
        lbl_error_time.setVisible(false);
        lbl_error_technician.setVisible(false);
        lbl_error_title.setVisible(false);
        lbl_error_description.setVisible(false);

        // check blank
        if (!customer_selected) {
            lbl_error_customer.setText("**Select a customer");
            lbl_error_customer.setVisible(true);

        } else if (!date_selected){
            lbl_error_date.setText("**Pick a date");
            lbl_error_date.setVisible(true);

        } else if (!time_selected){
            lbl_error_time.setText("**Pick a time");
            lbl_error_time.setVisible(true);

        } else if (!technician_selected){
            lbl_error_technician.setText("**Pick a technician");
            lbl_error_technician.setVisible(true);

        } else {

            title_validation();

        }

    }

    private void title_validation(){

        String title = txt_title.getText();

        // check blank
        if(title.isEmpty()){
            lbl_error_title.setText("**Cannot be leave blank");
            lbl_error_title.setVisible(true);

        } else {

            boolean valid_title = Appointment.validate_title(title);

            if(valid_title){

                description_validation();

            } else {
                lbl_error_title.setText("**Invalid title (Numbers, special characters are not allowed)");
                lbl_error_title.setVisible(true);
            }

        }

    }

    private void description_validation(){

        String description = txt_description.getText();

        // check blank
        if(description.isEmpty()){
            lbl_error_description.setText("**Cannot be leave blank");
            lbl_error_description.setVisible(true);

        } else {

            boolean valid_description = Appointment.validate_description(description);

            if(valid_description){

                book_appointment();
                reset();

            } else {
                lbl_error_description.setText("**Invalid description (Numbers, special characters, new line are not allowed)");
                lbl_error_description.setVisible(true);
            }

        }

    }

    private void book_appointment(){

        String appliance = cbox_appliance.getValue().toLowerCase();
        int number_appliance = Integer.parseInt(cbox_number_appliance.getValue());

        // create new payment
        Payment payment = new Payment();
        double price = payment.calculate_price(appliance, number_appliance);
        payment.new_payment(price);

        // create new feedback
        Feedback feedback = new Feedback();
        feedback.new_feedback(payment.get_id());

        // create new appointment
        // get all details
        String manager_id = Activity_log.get_current_user_id();
        String customer_id = selected_customer_id;
        String technician_id = cbox_technician.getValue().toLowerCase();
        String feedback_id = feedback.get_id();
        String title = txt_title.getText();

            // format date of date picker
        String format_date = "dd/MM/yyyy";
        LocalDate selected_date = date_picker_date.getValue();
        String formatted_date = selected_date.format(DateTimeFormatter.ofPattern(format_date));

        String time = cbox_time.getValue();
        String description = txt_description.getText();

        String [] appointment_details = {manager_id, customer_id, technician_id,
                feedback_id, title, formatted_date,
                time, appliance, String.valueOf(number_appliance), description};

        Appointment appointment = new Appointment();
        appointment.new_appointment(appointment_details);

        Activity_log.new_activity("book appointment " + appointment.get_id());
        call_popup_window(btn_book, "Popup_book_appointment_success.fxml");

    }

    private void reset(){

        selected_customer_id = null;
        lbl_selected_customer.setText("--");

        date_picker_date.getEditor().clear();
        cbox_time.getSelectionModel().clearSelection();
        cbox_technician.getSelectionModel().clearSelection();
        txt_title.clear();
        cbox_appliance.getSelectionModel().clearSelection();
        cbox_number_appliance.getSelectionModel().clearSelection();
        txt_description.clear();

        date_picker_date.setDisable(true);
        cbox_time.setDisable(true);
        cbox_technician.setDisable(true);
        txt_title.setDisable(true);
        cbox_appliance.setDisable(true);
        cbox_number_appliance.setDisable(true);
        txt_description.setDisable(true);

        customer_selected = false;
        date_selected = false;
        time_selected = false;
        technician_selected = false;
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

    private void import_customer_data_to_table() {

        Customer customer = new Customer();

        Collection<Customer_table_data> list = null;
        try {
            list = Files.readAllLines(new File(customer.get_file_path()).toPath())
                    .stream()
                    .map(line -> {
                        String[] details = line.split(",");

                        Customer_table_data customer_table_data = new Customer_table_data();

                        customer_table_data.setID(details[0].toUpperCase());
                        customer_table_data.set_ic(details[3]);
                        customer_table_data.set_name(details[2].toUpperCase());

                        return customer_table_data;

                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        customer_table_data = FXCollections.observableArrayList(list);

        column_customer_id.setCellValueFactory(data -> data.getValue().id_property());
        column_customer_ic.setCellValueFactory(data -> data.getValue().ic_property());
        column_customer_name.setCellValueFactory(data -> data.getValue().name_property());

        table_customer.setItems(customer_table_data);
    }

    private void reverse_table_record(){

        column_customer_id.setSortType(TableColumn.SortType.DESCENDING);
        table_customer.getSortOrder().setAll(column_customer_id);

    }

    private void setup_table_selection_event() {
        table_customer.setRowFactory(tv -> {
            TableRow<Customer_table_data> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    Customer_table_data rowData = row.getItem();
                    selected_customer_id = rowData.getID().toLowerCase();

                    lbl_selected_customer.setText(rowData.get_name());

                    date_picker_date.setDisable(false);

                    customer_selected = true;
                    lbl_error_customer.setVisible(false);
                }
            });
            return row;
        });

    }

    private void setup_search_bar() {

        // Customer search bar function
        txt_customer_search.textProperty().addListener((obs, oldText, newText) -> {

            String keyword = txt_customer_search.getText().toLowerCase();
            if (keyword.equals("")) {
                table_customer.setItems(customer_table_data);

            } else {

                ObservableList<Customer_table_data> filteredData = FXCollections.observableArrayList();

                for (Customer_table_data customer_data : customer_table_data) {
                    if (customer_data.getID().toLowerCase().contains(keyword) || customer_data.get_ic().contains(keyword) || customer_data.get_name().toLowerCase().contains(keyword))

                        filteredData.add(customer_data);
                }

                table_customer.setItems(filteredData);
            }
        });

    }

    private void setup_date_picker(){

        // disable past date, saturday and sunday
        date_picker_date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) <= 0 ||
                        date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
        date_picker_date.setEditable(false);

        // change date format
        String format_date = "dd/MM/yyyy";

        date_picker_date.setConverter(new StringConverter<>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format_date);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        // selection event
        date_picker_date.valueProperty().addListener((ov, oldValue, newValue) -> {

            date_selected = true;
            lbl_error_date.setVisible(false);

            cbox_time.setDisable(false);
            cbox_time.getSelectionModel().clearSelection();

            cbox_technician.setDisable(true);
            cbox_technician.getSelectionModel().clearSelection();

            txt_title.setDisable(true);
            cbox_appliance.setDisable(true);
            cbox_number_appliance.setDisable(true);
            txt_description.setDisable(true);

        });

    }

    private void setup_time_choice_box() {

        cbox_time.getItems().addAll(available_time);

        cbox_time.setOnAction(event -> {

            time_selected = true;
            lbl_error_time.setVisible(false);

            Appointment appointment = new Appointment();

            // format date of date picker
            String format_date = "dd/MM/yyyy";
            LocalDate selected_date = date_picker_date.getValue();
            String formatted_date = selected_date.format(DateTimeFormatter.ofPattern(format_date));

            // get time from time choice box
            String selected_time = cbox_time.getValue();

            // find unavailable technician on that date and the time
            String [] unavailable_technician = appointment.find_unavailable_technician_for_selected_date_time(formatted_date, selected_time);

            // find available technician
            String [] available_technician = Technician.filter_these_technician(unavailable_technician);
            ObservableList<String> list = FXCollections.observableArrayList(available_technician);

            // add available technician ID
            cbox_technician.setItems(list);
            cbox_technician.getSelectionModel().selectFirst();
            cbox_technician.setDisable(false);

            String selected_technician = cbox_technician.getValue();

            if (selected_technician == null){
                lbl_error_technician.setText("**There is no available technician for this date and time");
                lbl_error_technician.setVisible(true);
                cbox_technician.setDisable(true);

                txt_title.setDisable(true);
                cbox_appliance.setDisable(true);
                cbox_number_appliance.setDisable(true);
                txt_description.setDisable(true);

                technician_selected = false;

            } else {
                lbl_error_technician.setVisible(false);
                cbox_technician.setDisable(false);

                txt_title.setDisable(false);

                cbox_appliance.setDisable(false);
                cbox_appliance.getSelectionModel().selectFirst();
                cbox_number_appliance.setDisable(false);
                cbox_number_appliance.getSelectionModel().selectFirst();

                txt_description.setDisable(false);

                technician_selected = true;
                lbl_error_technician.setVisible(false);
            }

        });

    }

    private void setup_appliance_choice_box() {

        cbox_appliance.getItems().addAll(appliance);
        cbox_number_appliance.getItems().addAll(number_appliance);

    }

}
