package com.example.oodj;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Manager_management implements Initializable {

    private boolean panel_menu_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    private ObservableList<Customer_table_data> customer_table_data = FXCollections.observableArrayList();
    private ObservableList<Technician_table_data> technician_table_data = FXCollections.observableArrayList();
    private ObservableList<Manager_table_data> manager_table_data = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer_table_data> table_customer;

    @FXML
    private TableView<Technician_table_data> table_technician;

    @FXML
    private TableView<Manager_table_data> table_manager;


    @FXML
    private TableColumn<Customer_table_data, String> column_customer_ic;

    @FXML
    private TableColumn<Customer_table_data, String> column_customer_id;

    @FXML
    private TableColumn<Customer_table_data, String> column_customer_name;


    @FXML
    private TableColumn<Technician_table_data, String> column_technician_ic;

    @FXML
    private TableColumn<Technician_table_data, String> column_technician_id;

    @FXML
    private TableColumn<Technician_table_data, String> column_technician_name;


    @FXML
    private TableColumn<Manager_table_data, String> column_manager_ic;

    @FXML
    private TableColumn<Manager_table_data, String> column_manager_id;

    @FXML
    private TableColumn<Manager_table_data, String> column_manager_name;

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
    private Button btn_registration;

    @FXML
    private Button btn_report;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_date_time;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private TextField txt_customer_search;

    @FXML
    private TextField txt_manager_search;

    @FXML
    private TextField txt_technician_search;

    @FXML
    void btn_menu_appointment_clicked(MouseEvent event) {
        change_scene(btn_menu_appointment,"Manager_appointment.fxml");
    }

    @FXML
    void btn_menu_management_clicked(MouseEvent event) {

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
    void btn_registration_clicked(MouseEvent event) {
        change_scene(btn_registration, "Manager_management_registration.fxml");
    }

    @FXML
    void btn_report_clicked(MouseEvent event) {
        change_scene(btn_report, "Manager_management_report.fxml");
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

        // setup clock
        Clock.real_time_clock(lbl_date_time, "EEE, d MMM yyyy h:mm:ss a");

        // setup table
        import_customer_data_to_table();
        import_technician_data_to_table();
        import_manager_data_to_table();

        reverse_table_record();
        setup_table_selection_event();

        // setup search bar
        setup_search_bar();
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

    private void import_technician_data_to_table() {

        Technician technician = new Technician();

        Collection<Technician_table_data> list = null;
        try {
            list = Files.readAllLines(new File(technician.get_file_path()).toPath())
                    .stream()
                    .map(line -> {
                        String[] details = line.split(",");

                        Technician_table_data technician_table_data = new Technician_table_data();

                        technician_table_data.setID(details[0].toUpperCase());
                        technician_table_data.setIc(details[4]);
                        technician_table_data.setName(details[3].toUpperCase());

                        return technician_table_data;

                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        technician_table_data = FXCollections.observableArrayList(list);

        column_technician_id.setCellValueFactory(data -> data.getValue().id_property());
        column_technician_ic.setCellValueFactory(data -> data.getValue().ic_property());
        column_technician_name.setCellValueFactory(data -> data.getValue().name_property());

        table_technician.setItems(technician_table_data);
    }

    private void import_manager_data_to_table() {

        Manager manager = new Manager();

        Collection<Manager_table_data> list = null;
        try {
            list = Files.readAllLines(new File(manager.get_file_path()).toPath())
                    .stream()
                    .map(line -> {
                        String[] details = line.split(",");

                        Manager_table_data manager_table_data = new Manager_table_data();

                        manager_table_data.setID(details[0].toUpperCase());
                        manager_table_data.setIc(details[4]);
                        manager_table_data.setName(details[3].toUpperCase());

                        return manager_table_data;

                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        manager_table_data = FXCollections.observableArrayList(list);

        column_manager_id.setCellValueFactory(data -> data.getValue().id_property());
        column_manager_ic.setCellValueFactory(data -> data.getValue().ic_property());
        column_manager_name.setCellValueFactory(data -> data.getValue().name_property());

        table_manager.setItems(manager_table_data);
    }

    private void reverse_table_record(){

        column_customer_id.setSortType(TableColumn.SortType.DESCENDING);
        table_customer.getSortOrder().setAll(column_customer_id);

        column_technician_id.setSortType(TableColumn.SortType.DESCENDING);
        table_technician.getSortOrder().setAll(column_technician_id);

        column_manager_id.setSortType(TableColumn.SortType.DESCENDING);
        table_manager.getSortOrder().setAll(column_manager_id);
    }

    private void setup_table_selection_event(){
        table_customer.setRowFactory(tv -> {
            TableRow<Customer_table_data> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Customer_table_data rowData = row.getItem();

                    Customer customer = new Customer(rowData.getID().toLowerCase());
                    change_scene(btn_registration, "Manager_management_customer_details.fxml");
                }
            });
            return row ;
        });

        table_technician.setRowFactory(tv -> {
            TableRow<Technician_table_data> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Technician_table_data rowData = row.getItem();

                    Technician technician = new Technician(rowData.getID().toLowerCase());
                    change_scene(btn_registration, "Manager_management_user_details.fxml");
                }
            });
            return row ;
        });

        table_manager.setRowFactory(tv -> {
            TableRow<Manager_table_data> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Manager_table_data rowData = row.getItem();

                    Manager manager = new Manager(rowData.getID().toLowerCase());
                    change_scene(btn_registration, "Manager_management_user_details.fxml");
                }
            });
            return row ;
        });
    }

    private void setup_search_bar(){

        // Customer search bar function
        txt_customer_search.textProperty().addListener((obs, oldText, newText) -> {

            String keyword = txt_customer_search.getText().toLowerCase();
            if (keyword.equals("")) {
                table_customer.setItems(customer_table_data);

            } else {

                ObservableList<Customer_table_data> filteredData = FXCollections.observableArrayList();

                for (Customer_table_data customer_data : customer_table_data) {
                    if(customer_data.getID().toLowerCase().contains(keyword) || customer_data.get_ic().contains(keyword) || customer_data.get_name().toLowerCase().contains(keyword))

                        filteredData.add(customer_data);
                }

                table_customer.setItems(filteredData);
            }
        });

        // Technician search bar function
        txt_technician_search.textProperty().addListener((obs, oldText, newText) -> {

            String keyword = txt_technician_search.getText().toLowerCase();
            if (keyword.equals("")) {
                table_technician.setItems(technician_table_data);

            } else {

                ObservableList<Technician_table_data> filteredData = FXCollections.observableArrayList();

                for (Technician_table_data technician_data : technician_table_data) {
                    if(technician_data.getID().toLowerCase().contains(keyword) || technician_data.getIc().contains(keyword) || technician_data.getName().toLowerCase().contains(keyword))

                        filteredData.add(technician_data);
                }

                table_technician.setItems(filteredData);
            }
        });

        // Manager search bar function
        txt_manager_search.textProperty().addListener((obs, oldText, newText) -> {

            String keyword = txt_manager_search.getText().toLowerCase();
            if (keyword.equals("")) {
                table_manager.setItems(manager_table_data);

            } else {

                ObservableList<Manager_table_data> filteredData = FXCollections.observableArrayList();

                for (Manager_table_data manager_data : manager_table_data) {
                    if(manager_data.getID().toLowerCase().contains(keyword) || manager_data.getIc().contains(keyword) || manager_data.getName().toLowerCase().contains(keyword))

                        filteredData.add(manager_data);
                }

                table_manager.setItems(filteredData);
            }
        });

    }

}
