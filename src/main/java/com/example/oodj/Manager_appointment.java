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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Manager_appointment implements Initializable {

    private boolean panel_menu_expanded = false;
    private boolean panel_filter_expanded = false;

    private Image img_hamburger_menu = new Image(getClass().getResource("/assets/hamburger_white.png").toExternalForm());;
    private Image img_close_menu = new Image(getClass().getResource("/assets/close.png").toExternalForm());;

    private Image img_filter_white =  new Image(getClass().getResource("/assets/filter_white.png").toExternalForm());;
    private Image img_filter_grey =  new Image(getClass().getResource("/assets/filter_grey.png").toExternalForm());;

    private ObservableList<Appointment_table_data> raw_table_data = FXCollections.observableArrayList();
    private ObservableList<Appointment_table_data> clean_table_data = FXCollections.observableArrayList();

    @FXML
    private TableView<Appointment_table_data> table;

    @FXML
    private TableColumn<Appointment_table_data, String> column_id;

    @FXML
    private TableColumn<Appointment_table_data, String> column_title;

    @FXML
    private TableColumn<Appointment_table_data, String> column_date;

    @FXML
    private TableColumn<Appointment_table_data, String> column_time;

    @FXML
    private TableColumn<Appointment_table_data, String> column_status;

    @FXML
    private ImageView btn_open_filter;

    @FXML
    private Button btn_filter_all;

    @FXML
    private Button btn_filter_cancelled;

    @FXML
    private Button btn_filter_completed;

    @FXML
    private Button btn_filter_pending;

    @FXML
    private Button btn_menu_appointment;

    @FXML
    private Button btn_menu_management;

    @FXML
    private Button btn_menu_profile;

    @FXML
    private Button btn_menu_sign_out;

    @FXML
    private Button btn_new_appointment;

    @FXML
    private Button btn_new_customer;

    @FXML
    private Button btn_open_menu;

    @FXML
    private ImageView img_expand_menu;

    @FXML
    private Label lbl_date_time;

    @FXML
    private VBox panel_filter;

    @FXML
    private AnchorPane panel_menu;

    @FXML
    private TextField txt_search;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // setup clock
        Clock.real_time_clock(lbl_date_time, "EEE, d MMM yyyy h:mm:ss a");

        // setup table
        import_data_to_table();
        reverse_table_record();
        setup_table_selection_event();

        // setup search bar and filter
        setup_search_bar();
        setup_filter();

    }

    @FXML
    void btn_filter_all_clicked(MouseEvent event) {

    }

    @FXML
    void btn_filter_cancelled_clicked(MouseEvent event) {

    }

    @FXML
    void btn_open_filter_clicked(MouseEvent event) {

        if(panel_filter_expanded){

            panel_filter.setVisible(false);
            btn_open_filter.setImage(img_filter_grey);

            panel_filter_expanded = false;

        } else {

            panel_filter.setVisible(true);
            btn_open_filter.setImage(img_filter_white);

            panel_filter_expanded = true;
        }

    }

    @FXML
    void btn_filter_completed_clicked(MouseEvent event) {

    }

    @FXML
    void btn_filter_pending_clicked(MouseEvent event) {

    }

    @FXML
    void btn_menu_appointment_clicked(MouseEvent event) {

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
        change_scene(btn_menu_sign_out, "Sign_in.fxml");
    }

    @FXML
    void btn_new_appointment_clicked(MouseEvent event) {
        change_scene(btn_new_appointment, "Manager_book_appointment.fxml");
    }

    @FXML
    void btn_new_customer_clicked(MouseEvent event) {
        change_scene(btn_new_customer, "Manager_management_registration.fxml");
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

    private void change_scene(Button button, String page){
        Window window = new Window();
        try {
            window.change_scene(button, page);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void import_data_to_table() {

        Appointment appointment = new Appointment();

        Collection<Appointment_table_data> list = null;
        try {
            list = Files.readAllLines(new File(appointment.get_file_path()).toPath())
                    .stream()
                    .map(line -> {
                        String[] details = line.split(",");

                        Appointment_table_data appointment_table_data = new Appointment_table_data();

                        appointment_table_data.setID(details[0].toUpperCase());
                        appointment_table_data.set_title(details[5]);
                        appointment_table_data.set_date(details[6]);

                        // format time
                        String formatted_time = "NA";

                        try {
                            formatted_time = Clock.date_time_formatter(details[7],"HH:mm", "h:mm a");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        appointment_table_data.set_time(formatted_time);

                        // format status
                        String formatted_status = Identifier.identify_appointment_status(details[11]);

                        appointment_table_data.set_status(formatted_status);

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
        column_time.setCellValueFactory(data -> data.getValue().time_property());
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
                    change_scene(btn_new_appointment, "Manager_appointment_details.fxml");
                }
            });
            return row ;
        });
    }

    private void setup_search_bar(){

        // Search bar function
        txt_search.textProperty().addListener((obs, oldText, newText) -> {

            btn_filter_all.getStyleClass().clear();
            btn_filter_pending.getStyleClass().clear();
            btn_filter_cancelled.getStyleClass().clear();
            btn_filter_completed.getStyleClass().clear();

            btn_filter_all.getStyleClass().add("selected-filter");
            btn_filter_pending.getStyleClass().add("unselected-filter");
            btn_filter_cancelled.getStyleClass().add("unselected-filter");
            btn_filter_completed.getStyleClass().add("unselected-filter");

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

    private void setup_filter(){

        btn_filter_all.getStyleClass().add("selected-filter");

        // filter function
        btn_filter_all.setOnAction(e ->{

            table.setItems(clean_table_data);
            txt_search.clear();

            btn_filter_all.getStyleClass().clear();
            btn_filter_pending.getStyleClass().clear();
            btn_filter_cancelled.getStyleClass().clear();
            btn_filter_completed.getStyleClass().clear();

            btn_filter_all.getStyleClass().add("selected-filter");
            btn_filter_pending.getStyleClass().add("unselected-filter");
            btn_filter_cancelled.getStyleClass().add("unselected-filter");
            btn_filter_completed.getStyleClass().add("unselected-filter");

        });

        btn_filter_pending.setOnAction(e -> {

            ObservableList<Appointment_table_data> filteredData = FXCollections.observableArrayList();
            for (Appointment_table_data appointment_data : clean_table_data) {
                if(appointment_data.get_status().equals("Pending"))
                    filteredData.add(appointment_data);
            }

            table.setItems(filteredData);
            txt_search.clear();

            btn_filter_all.getStyleClass().clear();
            btn_filter_pending.getStyleClass().clear();
            btn_filter_cancelled.getStyleClass().clear();
            btn_filter_completed.getStyleClass().clear();

            btn_filter_all.getStyleClass().add("unselected-filter");
            btn_filter_pending.getStyleClass().add("selected-filter");
            btn_filter_cancelled.getStyleClass().add("unselected-filter");
            btn_filter_completed.getStyleClass().add("unselected-filter");

        });

        btn_filter_cancelled.setOnAction(e -> {

            ObservableList<Appointment_table_data> filteredData = FXCollections.observableArrayList();
            for (Appointment_table_data appointment_data : clean_table_data) {
                if(appointment_data.get_status().equals("Cancelled"))
                    filteredData.add(appointment_data);
            }

            table.setItems(filteredData);
            txt_search.clear();

            btn_filter_all.getStyleClass().clear();
            btn_filter_pending.getStyleClass().clear();
            btn_filter_cancelled.getStyleClass().clear();
            btn_filter_completed.getStyleClass().clear();

            btn_filter_all.getStyleClass().add("unselected-filter");
            btn_filter_pending.getStyleClass().add("unselected-filter");
            btn_filter_cancelled.getStyleClass().add("selected-filter");
            btn_filter_completed.getStyleClass().add("unselected-filter");
        });

        btn_filter_completed.setOnAction(e -> {

            ObservableList<Appointment_table_data> filteredData = FXCollections.observableArrayList();
            for (Appointment_table_data appointment_data : clean_table_data) {
                if(appointment_data.get_status().equals("Completed"))
                    filteredData.add(appointment_data);
            }

            table.setItems(filteredData);
            txt_search.clear();

            btn_filter_all.getStyleClass().clear();
            btn_filter_pending.getStyleClass().clear();
            btn_filter_cancelled.getStyleClass().clear();
            btn_filter_completed.getStyleClass().clear();

            btn_filter_all.getStyleClass().add("unselected-filter");
            btn_filter_pending.getStyleClass().add("unselected-filter");
            btn_filter_cancelled.getStyleClass().add("unselected-filter");
            btn_filter_completed.getStyleClass().add("selected-filter");
        });
    }

}
