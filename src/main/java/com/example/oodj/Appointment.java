package com.example.oodj;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Appointment implements Database {

    private static Scanner scanner;
    private boolean exist = false;

    private static String id;
    private static String manager_id;
    private static String customer_id;
    private static String technician_id;
    private static String feedback_id;
    private static String title;
    private static String date;
    private static String time;
    private static String appliance;
    private static String number_appliance;
    private static String description;
    private static String status;  // 0 = pending, 1 = cancelled, 2 = completed

    public Appointment(){

    }

    public Appointment(String input_id){
        id = input_id;
    }

    public void new_appointment(String [] appointment_details){

        int number_id = 1;

        // Get last id in appointment database
        try {
            BufferedReader reader = new BufferedReader(new FileReader(appointment_filepath));
            while (reader.readLine() != null) number_id++;
            reader.close();

        } catch (Exception ex){

        }

        // fit number id into full form id
        String id = "ap" + ("000000" + number_id).substring(String.valueOf(number_id).length());
        this.set_id(id);

        // new appointment status is pending
        this.set_status("0");

        //add new appointment
        String newLine =
                id + ","
                + appointment_details[0] + ","
                + appointment_details[1] + ","
                + appointment_details[2] + ","
                + appointment_details[3] + ","
                + appointment_details[4] + ","
                + appointment_details[5] + ","
                + appointment_details[6] + ","
                + appointment_details[7] + ","
                + appointment_details[8] + ","
                + appointment_details[9] + ","
                + status;

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(appointment_filepath, true));
            pw.println(newLine);
            pw.close();

        }
        catch (Exception e) {

        }

    }

    public void complete_appointment(String appointment_id){

        String new_status = "2";

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(appointment_filepath)){
            scanner = new Scanner (fr);
            String line;
            String[] lineArr;

            while((line = scanner.nextLine())!=null) {
                lineArr = line.split(",");

                if(lineArr[0].equals(appointment_id)) {
                    tempArray.add(
                            lineArr[0] + "," +
                                    lineArr[1] + "," +
                                    lineArr[2] + "," +
                                    lineArr[3] + "," +
                                    lineArr[4] + "," +
                                    lineArr[5] + "," +
                                    lineArr[6] + "," +
                                    lineArr[7] + "," +
                                    lineArr[8] + "," +
                                    lineArr[9] + "," +
                                    lineArr[10] + "," +
                                    new_status
                    );
                }
                else {
                    tempArray.add(line);
                }
            }
            scanner.close();
        }

        catch (Exception e){

        }

        try(PrintWriter pr = new PrintWriter(appointment_filepath)){
            for (String str : tempArray) {
                pr.println(str);
            }

            pr.flush();
        }

        catch(Exception e) {

        }

    }

    public void find_appointment_with_id(String appointment_id) {
        String temp_id = "";
        String temp_manager_id = "";
        String temp_customer_id = "";
        String temp_technician_id = "";
        String temp_feedback_id = "";
        String temp_title = "";
        String temp_date = "";
        String temp_time = "";
        String temp_appliance = "";
        String temp_number_appliance = "";
        String temp_description = "";
        String temp_status = "";

        try {
            scanner = new Scanner (new File(appointment_filepath));
            scanner.useDelimiter("[,\n]");

            while(scanner.hasNext() && !exist) {

                temp_id = scanner.next();
                temp_manager_id = scanner.next();
                temp_customer_id  = scanner.next();
                temp_technician_id = scanner.next();
                temp_feedback_id = scanner.next();
                temp_title = scanner.next();
                temp_date = scanner.next();
                temp_time = scanner.next();
                temp_appliance = scanner.next();
                temp_number_appliance = scanner.next();
                temp_description = scanner.next();
                temp_status = scanner.next();

                if(temp_id.trim().equals(appointment_id)) {

                    exist = true;

                    this.set_id(temp_id);
                    this.set_manager_id(temp_manager_id);
                    this.set_customer_id(temp_customer_id);
                    this.set_technician_id(temp_technician_id);
                    this.set_feedback_id(temp_feedback_id);
                    this.set_title(temp_title);
                    this.set_date(temp_date);
                    this.set_time(temp_time);
                    this.set_appliance(temp_appliance);
                    this.set_number_appliance(temp_number_appliance);
                    this.set_description(temp_description);
                    this.set_status(temp_status.trim());

                }

            }
            scanner.close();


        } catch (Exception e) {
            exist = false;
        }

    }

    public void abort_appointment(String appointment_id){

        String new_status = "1";

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(appointment_filepath)){
            scanner = new Scanner (fr);
            String line;
            String[] lineArr;

            while((line = scanner.nextLine())!=null) {
                lineArr = line.split(",");

                if(lineArr[0].equals(appointment_id)) {
                    tempArray.add(
                            lineArr[0] + "," +
                                    lineArr[1] + "," +
                                    lineArr[2] + "," +
                                    lineArr[3] + "," +
                                    lineArr[4] + "," +
                                    lineArr[5] + "," +
                                    lineArr[6] + "," +
                                    lineArr[7] + "," +
                                    lineArr[8] + "," +
                                    lineArr[9] + "," +
                                    lineArr[10] + "," +
                                    new_status
                    );
                }
                else {
                    tempArray.add(line);
                }
            }
            scanner.close();
        }

        catch (Exception e){

        }

        try(PrintWriter pr = new PrintWriter(appointment_filepath)){
            for (String str : tempArray) {
                pr.println(str);
            }

            pr.flush();
        }

        catch(Exception e) {

        }

    }

    public String[] find_unavailable_technician_for_selected_date_time(String selected_date, String selected_time) {

        String [] technician = {};
        List<String> technician_list = new ArrayList<>(Arrays.asList(technician));

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(appointment_filepath)) {
            scanner = new Scanner(fr);
            String line;
            String[] lineArr;

            while ((line = scanner.nextLine()) != null) {
                lineArr = line.split(",");

                if (lineArr[6].equals(selected_date) && lineArr[7].equals(selected_time)) {

                    technician_list.add(lineArr[3]);

                } else {
                    tempArray.add(line);
                }
            }
            scanner.close();
        } catch (Exception e) {

        }

        technician = technician_list.toArray(technician);
        return technician;
    }


    public int[] find_appointment_details_with_month_year(String selected_month, String selected_year){

        String selected_month_year = selected_month + "/" + selected_year;

        int total_pending = 0;
        int total_completed = 0;
        int total_cancelled = 0;

        int total_small = 0;
        int total_large = 0;

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(appointment_filepath)) {
            scanner = new Scanner(fr);
            String line;
            String[] lineArr;

            while ((line = scanner.nextLine()) != null) {
                lineArr = line.split(",");

                String substring = lineArr[6].substring(3);

                if (substring.equals(selected_month_year)) {

                    // count appointment with status
                    if(lineArr[11].trim().equals("0")){
                        total_pending += 1;
                    } else if(lineArr[11].trim().equals("1") ){
                        total_cancelled += 1;
                    } else if(lineArr[11].trim().equals("2") ){
                        total_completed += 1;
                    }

                    // count appointment with appliance
                    if(lineArr[8].trim().equals("small")){
                        total_small += Integer.parseInt(lineArr[9]);
                    } else if(lineArr[8].trim().equals("large") ){
                        total_large += Integer.parseInt(lineArr[9]);
                    }

                } else {
                    tempArray.add(line);
                }


            }

            scanner.close();
        } catch (Exception e) {

        }

        // count total appointment
        int total_appointment = total_pending + total_completed + total_cancelled;

        // count total appliance
        int total_appliance = total_small + total_large;

        int [] details = {total_appointment, total_pending, total_completed, total_cancelled, total_appliance, total_small, total_large};
        return details;
    }


    public String[] find_feedback_id_with_month_year(String selected_month, String selected_year) {

        String selected_month_year = selected_month + "/" + selected_year;

        String [] feedback = {};
        List<String> feedback_list = new ArrayList<>(Arrays.asList(feedback));

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(appointment_filepath)) {
            scanner = new Scanner(fr);
            String line;
            String[] lineArr;

            while ((line = scanner.nextLine()) != null) {
                lineArr = line.split(",");

                String substring = lineArr[6].substring(3);

                if (substring.equals(selected_month_year)) {

                    feedback_list.add(lineArr[4]);

                } else {
                    tempArray.add(line);
                }
            }
            scanner.close();
        } catch (Exception e) {

        }

        feedback = feedback_list.toArray(feedback);
        return feedback;
    }


    // validation
        // for title
    public static final Pattern VALID_TITLE_REGEX = Pattern.compile("^[a-zA-Z][a-zA-Z ]+[a-zA-Z]$",  Pattern.CASE_INSENSITIVE);

    public static boolean validate_title(String title){
        Matcher matcher = VALID_TITLE_REGEX.matcher(title);
        return matcher.find();
    }

    // for description
    public static final Pattern VALID_DESCRIPTION_REGEX = Pattern.compile("^[a-zA-Z][a-zA-Z ]+[a-zA-Z]$",  Pattern.CASE_INSENSITIVE);

    public static boolean validate_description(String description){
        Matcher matcher = VALID_DESCRIPTION_REGEX.matcher(description);
        return matcher.find();
    }



    // Setter
    public static void set_id(String input_id){
        id = input_id;
    }

    public void set_manager_id(String input_manager_id){
        manager_id = input_manager_id;
    }

    public void set_customer_id(String input_customer_id){
        customer_id = input_customer_id;
    }

    public void set_technician_id(String input_technician_id){
        technician_id = input_technician_id;
    }

    public void set_feedback_id(String input_feedback_id){
        feedback_id = input_feedback_id;
    }

    public void set_title(String input_title){
        title = input_title;
    }

    public void set_date(String input_date){
        date = input_date;
    }

    public void set_time(String input_time){
        time = input_time;
    }

    public void set_appliance(String input_appliance){
        appliance = input_appliance;
    }

    public void set_number_appliance(String input_number_appliance){
        number_appliance = input_number_appliance;
    }

    public void set_description(String input_description){
        description = input_description;
    }

    public void set_status(String input_status){
        status = input_status;
    }

    // Getter
    public static String get_id(){return id;}

    public String get_manager_id(){
        return manager_id;
    }

    public String get_customer_id(){
        return customer_id;
    }

    public String get_technician_id(){
        return technician_id;
    }

    public String get_feedback_id(){
        return feedback_id;
    }

    public String get_title(){
        return title;
    }

    public String get_date(){
        return date;
    }

    public String get_time(){
        return time;
    }

    public String get_appliance(){
        return appliance;
    }

    public String get_number_appliance(){
        return number_appliance;
    }

    public String get_description(){
        return description;
    }

    public String get_status(){
        return status;
    }

    @Override
    public String get_file_path() {
        return appointment_filepath;
    }

}
