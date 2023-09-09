package com.example.oodj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class User {

    private static String id;
    private static String email_address;
    private static String password;
    private static String name;
    private static String ic;
    private static String phone_number;

    // validation
        // for email address
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate_email_address(String email_address){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email_address);
        return matcher.find();
    }

        // for password
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{8,}$",  Pattern.CASE_INSENSITIVE);

    public static boolean validate_password(String password){
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

        // for name
    public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[a-zA-Z][a-zA-Z ]+[a-zA-Z]$",  Pattern.CASE_INSENSITIVE);

    public static boolean validate_name(String ic){
        Matcher matcher = VALID_NAME_REGEX.matcher(ic);
        return matcher.find();
    }

        // for ic
    public static final Pattern VALID_IC_REGEX = Pattern.compile("^[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[0-9]{6}$",  Pattern.CASE_INSENSITIVE);

    public static boolean validate_ic(String ic){
        Matcher matcher = VALID_IC_REGEX.matcher(ic);
        return matcher.find();
    }

        // for phone number
    public static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^(\\+?6?01)[02-46-9]-*[0-9]{7}$|^(\\+?6?01)[1]-*[0-9]{8}$",  Pattern.CASE_INSENSITIVE);

    public static boolean validate_phone_number(String phone_number){
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phone_number);
        return matcher.find();
    }

    //registration
    public abstract void registration(String name, String ic, String phone_number, String email_address, String password);

    //check
    public abstract void find_user_with_email_address(String email_address);
    public abstract void find_user_with_id(String id);
    public abstract void find_user_with_ic(String ic);

    //edit
    public abstract void edit_password(String id, String new_password);
    public abstract void edit_password_and_phone_number(String id, String new_password, String new_phone_number);


    // Setter
    public static void set_id(String input_id){
        id = input_id;
    }

    public static void set_email_address(String input_email_address){
        email_address = input_email_address;
    }

    public static void set_password(String input_password){
        password = input_password;
    }

    public static void set_name(String input_name){
        name = input_name;
    }

    public static void set_ic(String input_ic){
        ic = input_ic;
    }

    public static void set_phone_number(String input_phone_number){
        phone_number = input_phone_number;
    }

    // Getter

    public static String get_id(){return id;}

    public static String get_email_address(){
        return email_address;
    }

    public static String get_password(){
        return password;
    }

    public static String get_name(){
        return name;
    }

    public static String get_ic(){
        return ic;
    }

    public static String get_phone_number(){
        return phone_number;
    }

}
