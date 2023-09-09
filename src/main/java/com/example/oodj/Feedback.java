package com.example.oodj;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Feedback implements Database{

    private static Scanner scanner;
    private boolean exist = false;

    private static String id;
    private static String payment_id;
    private static String feedback;
    private static String date;
    private static String time;

    public Feedback(){
        this.id = "";
        this.payment_id = "";
        this.feedback = "";
        this.date = "";
        this.time = "";
    }

    public void new_feedback(String payment_id) {

        int number_id = 1;

        // Get last id in feedback database
        try {
            BufferedReader reader = new BufferedReader(new FileReader(feedback_filepath));
            while (reader.readLine() != null) number_id++;
            reader.close();

        } catch (Exception ex) {

        }

        // fit number id into full form id
        String id = "fe" + ("000000" + number_id).substring(String.valueOf(number_id).length());
        this.set_id(id);

        //add new feedback
        String newLine =
                id + ","
                + payment_id + ","
                + feedback + ","
                + date + ","
                + time;


        try {
            PrintWriter pw = new PrintWriter(new FileWriter(feedback_filepath, true));
            pw.println(newLine);
            pw.close();

        }
        catch (Exception e) {

        }

    }

    public void complete_feedback(String feedback_id, String customer_feedback){

        // Get current date
        date = Clock.get_current_date_time("dd/MM/yyyy");

        // Get current time
        time = Clock.get_current_date_time("HH:mm");

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(feedback_filepath)){
            scanner = new Scanner (fr);
            String line;
            String[] lineArr;

            while((line = scanner.nextLine())!=null) {
                lineArr = line.split(",");

                if(lineArr[0].equals(feedback_id)) {
                    tempArray.add(
                            lineArr[0] + "," +
                                    lineArr[1] + "," +
                                    customer_feedback + "," +
                                    date + "," +
                                    time
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

        try(PrintWriter pr = new PrintWriter(feedback_filepath)){
            for (String str : tempArray) {
                pr.println(str);
            }

            pr.flush();
        }

        catch(Exception e) {

        }

    }

    public void find_feedback_with_id(String feedback_id) {
        String temp_id = "";
        String temp_payment_id = "";
        String temp_feedback = "";
        String temp_date = "";
        String temp_time = "";

        try {
            scanner = new Scanner(new File(feedback_filepath));
            scanner.useDelimiter("[,\n]");

            while(scanner.hasNext() && !exist) {

                temp_id = scanner.next();
                temp_payment_id = scanner.next();
                temp_feedback  = scanner.next();
                temp_date = scanner.next();
                temp_time = scanner.next();

                if(temp_id.trim().equals(feedback_id)) {

                    exist = true;

                    this.set_id(temp_id);
                    this.set_payment_id(temp_payment_id);
                    this.set_feedback(temp_feedback);
                    this.set_date(temp_date);
                    this.set_time(temp_time);

                }

            }
            scanner.close();


        } catch (Exception e) {
            exist = false;
        }

    }

    public String[] find_payment_id_with_feedback_id(String [] feedback_id){

        String [] payment = {};
        List<String> payment_list = new ArrayList<>(Arrays.asList(payment));

        List<String> feedback_id_list = new ArrayList<>(Arrays.asList(feedback_id));

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(feedback_filepath)) {
            scanner = new Scanner(fr);
            String line;
            String[] lineArr;

            while ((line = scanner.nextLine()) != null) {
                lineArr = line.split(",");

                if (feedback_id_list.contains(lineArr[0])) {

                    payment_list.add(lineArr[1]);

                } else {
                    tempArray.add(line);
                }
            }
            scanner.close();
        } catch (Exception e) {

        }

        payment = payment_list.toArray(payment);
        return payment;
    }

    // for feedback
    public static final Pattern VALID_FEEDBACK_REGEX = Pattern.compile("^[a-zA-Z][a-zA-Z ]+[a-zA-Z]$",  Pattern.CASE_INSENSITIVE);

    public static boolean validate_feedback(String feedback){
        Matcher matcher = VALID_FEEDBACK_REGEX.matcher(feedback);
        return matcher.find();
    }


    // Setter
    public void set_id(String input_id){
        id = input_id;
    }

    public void set_payment_id(String input_payment_id){
        payment_id = input_payment_id;
    }

    public void set_feedback(String input_feedback){
        feedback = input_feedback;
    }

    public void set_date(String input_date){
        date = input_date;
    }

    public void set_time(String input_time){
        time = input_time;
    }


    // Getter
    public String get_id(){return id;}

    public String get_payment_id(){
        return payment_id;
    }

    public String get_feedback(){
        return feedback;
    }

    public String get_date(){
        return date;
    }

    public String get_time(){
        return time;
    }

    @Override
    public String get_file_path() {
        return feedback_filepath;
    }
}
