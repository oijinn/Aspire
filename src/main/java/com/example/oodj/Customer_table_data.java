package com.example.oodj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer_table_data extends Customer{

    StringProperty id = new SimpleStringProperty();
    StringProperty email_address = new SimpleStringProperty();
    StringProperty name = new SimpleStringProperty();
    StringProperty ic = new SimpleStringProperty();
    StringProperty phone_number = new SimpleStringProperty();

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

    // email address
    public final StringProperty email_address_property() {
        return this.email_address;
    }

    @Override
    public final java.lang.String get_email_address() {
        return this.email_address_property().get();
    }

    @Override
    public final void set_email_address(final java.lang.String email_address) {
        this.email_address_property().set(email_address);
    }

    // name
    public final StringProperty name_property() {
        return this.name;
    }

    @Override
    public final java.lang.String get_name() {
        return this.name_property().get();
    }

    @Override
    public final void set_name(final java.lang.String name) {
        this.name_property().set(name);
    }

    // ic
    public final StringProperty ic_property() {
        return this.ic;
    }

    @Override
    public final java.lang.String get_ic() {
        return this.ic_property().get();
    }

    @Override
    public final void set_ic(final java.lang.String ic) {
        this.ic_property().set(ic);
    }

    // phone number
    public final StringProperty phone_number_property() {
        return this.phone_number;
    }

    @Override
    public final java.lang.String get_phone_number() {
        return this.phone_number_property().get();
    }

    @Override
    public final void set_phone_number(final java.lang.String phone_number) {
        this.phone_number_property().set(phone_number);
    }

}
