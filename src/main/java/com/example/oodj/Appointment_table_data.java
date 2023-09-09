package com.example.oodj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment_table_data extends Appointment{

    StringProperty id = new SimpleStringProperty();
    SimpleStringProperty manager_id;
    SimpleStringProperty customer_id;
    SimpleStringProperty technician_id;
    SimpleStringProperty feedback_id;
    StringProperty title = new SimpleStringProperty();
    StringProperty date = new SimpleStringProperty();
    StringProperty time = new SimpleStringProperty();
    SimpleStringProperty description;
    StringProperty status = new SimpleStringProperty();

    // id
    public final StringProperty id_property() {
        return this.id;
    }

    public final java.lang.String getID() {
        return this.id_property().get();
    }

    public final void setID(final java.lang.String id) {
        this.id_property().set(id);
    }


    // manager_id
    public final StringProperty manager_id_property() {
        return this.manager_id;
    }

    @Override
    public final java.lang.String get_manager_id() {
        return this.manager_id_property().get();
    }

    @Override
    public final void set_manager_id(final java.lang.String manager_id) {
        this.manager_id_property().set(manager_id);
    }


    // customer id
    public final StringProperty customer_id_property() {
        return this.customer_id;
    }

    @Override
    public final java.lang.String get_customer_id() {
        return this.customer_id_property().get();
    }

    @Override
    public final void set_customer_id(final java.lang.String customer_id) {
        this.customer_id_property().set(customer_id);
    }


    // technician id
    public final StringProperty technician_id_property() {
        return this.technician_id;
    }

    @Override
    public final java.lang.String get_technician_id() {
        return this.technician_id_property().get();
    }

    @Override
    public final void set_technician_id(final java.lang.String technician_id) {
        this.technician_id_property().set(technician_id);
    }


    // feedback id
    public final StringProperty feedback_id_property() {
        return this.feedback_id;
    }

    @Override
    public final java.lang.String get_feedback_id() {
        return this.feedback_id_property().get();
    }

    @Override
    public final void set_feedback_id(final java.lang.String feedback_id) {
        this.feedback_id_property().set(feedback_id);
    }


    //title
    public final StringProperty title_property() {
        return this.title;
    }

    @Override
    public final java.lang.String get_title() {
        return this.title_property().get();
    }

    @Override
    public final void set_title(final java.lang.String title) {
        this.title_property().set(title);
    }


    // date
    public final StringProperty date_property() {
        return this.date;
    }

    @Override
    public final java.lang.String get_date() {
        return this.date_property().get();
    }

    @Override
    public final void set_date(final java.lang.String date) {
        this.date_property().set(date);
    }


    // time
    public final StringProperty time_property() {
        return this.time;
    }

    @Override
    public final java.lang.String get_time() {
        return this.time_property().get();
    }

    @Override
    public final void set_time(final java.lang.String time) {
        this.time_property().set(time);
    }


    // description
    public final StringProperty description_property() {
        return this.description;
    }

    @Override
    public final java.lang.String get_description() {
        return this.description_property().get();
    }

    @Override
    public final void set_description(final java.lang.String description) {
        this.description_property().set(description);
    }


    // status
    public final StringProperty status_property() {
        return this.status;
    }

    @Override
    public final java.lang.String get_status() {
        return this.status_property().get();
    }

    @Override
    public final void set_status(final java.lang.String status) {
        this.status_property().set(status);
    }


}


