package com.example.oodj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Manager_table_data extends Manager{

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

    public final java.lang.String getEmail_address() {
        return this.email_address_property().get();
    }

    public final void setEmail_address(final java.lang.String email_address) {
        this.email_address_property().set(email_address);
    }

    // name
    public final StringProperty name_property() {
        return this.name;
    }

    public final java.lang.String getName() {
        return this.name_property().get();
    }

    public final void setName(final java.lang.String name) {
        this.name_property().set(name);
    }

    // ic
    public final StringProperty ic_property() {
        return this.ic;
    }

    public final java.lang.String getIc() {
        return this.ic_property().get();
    }

    public final void setIc(final java.lang.String ic) {
        this.ic_property().set(ic);
    }

    // phone number
    public final StringProperty phone_number_property() {
        return this.phone_number;
    }

    public final java.lang.String getPhone_number() {
        return this.phone_number_property().get();
    }

    public final void setPhone_number(final java.lang.String phone_number) {
        this.phone_number_property().set(phone_number);
    }

}
