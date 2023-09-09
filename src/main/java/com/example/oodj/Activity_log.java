package com.example.oodj;

import java.io.*;

public class Activity_log implements Database {

    private static int id = 0;
    private static String user_id = "na";
    private static String user_email_address = "na";
    private static String user_name = "na";
    private static String date = "na";
    private static String time = "na";
    private static String description = "na";

    public static void new_activity(String input_description){

        description = input_description;

        // Get last id in activity
        try {
            BufferedReader reader = new BufferedReader(new FileReader(activity_log_filepath));
            while (reader.readLine() != null) id++;
            reader.close();

        } catch (Exception ex){

        }

        // Get current date for sign in
        date = Clock.get_current_date_time("dd/MM/yyyy");

        // Get current time for sign in
        time = Clock.get_current_date_time("HH:mm:ss");

        //add new activity
        String newLine =
                id + ","
                        + user_id + ","
                        + user_email_address + ","
                        + user_name + ","
                        + date + ","
                        + time + ","
                        + description;

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(activity_log_filepath, true));
            pw.println(newLine);
            pw.close();

        }
        catch (Exception e) {

        }

        // reset id
        id = 0;
    }

    public static void new_activity(String input_user_id,  String input_email_address, String input_name, String input_description){

        user_id = input_user_id;
        user_email_address = input_email_address;
        user_name = input_name;
        description = input_description;

        // Get last id in activity
        try {
            BufferedReader reader = new BufferedReader(new FileReader(activity_log_filepath));
            while (reader.readLine() != null) id++;
            reader.close();

        } catch (Exception ex){

        }

        // Get current date for sign in
        date = Clock.get_current_date_time("dd/MM/yyyy");

        // Get current time for sign in
        time = Clock.get_current_date_time("HH:mm:ss");

        //add new activity
        String newLine =
                id + ","
                        + user_id + ","
                        + user_email_address + ","
                        + user_name + ","
                        + date + ","
                        + time + ","
                        + description;

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(activity_log_filepath, true));
            pw.println(newLine);
            pw.close();

        }
        catch (Exception e) {

        }

        // reset id
        id = 0;
    }

    public static String get_current_user_id(){
        return user_id;
    }

    @Override
    public String get_file_path() {
        return activity_log_filepath;
    }

}


