package com.example.oodj;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer implements Database {

    private static Scanner scanner;

    private boolean exist = false;

    private static String id;
    private static String email_address;
    private static String name;
    private static String ic;
    private static String phone_number;

    public Customer(){

    }

    public Customer(String input_id){
        this.id = input_id;
    }

    public void registration(String name, String ic, String phone_number, String email_address){

        int number_id = 1;

        // Get last id in manager database
        try {
            BufferedReader reader = new BufferedReader(new FileReader(customer_filepath));
            while (reader.readLine() != null) number_id++;
            reader.close();

        } catch (Exception ex){

        }

        // fit number id into full form id
        String id = "cu" + ("000000" + number_id).substring(String.valueOf(number_id).length());
        this.set_id(id);

        //add new manager
        String newLine =
                id + ","
                        + email_address + ","
                        + name + ","
                        + ic + ","
                        + phone_number;

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(customer_filepath, true));
            pw.println(newLine);
            pw.close();

        }
        catch (Exception e) {

        }

    }

    public void find_customer_with_id(String id) {
        String temp_id = "";
        String temp_email_address = "";
        String temp_name = "";
        String temp_ic = "";
        String temp_phone_number = "";

        try {
            scanner = new Scanner(new File(customer_filepath));
            scanner.useDelimiter("[,\n]");

            while(scanner.hasNext() && !exist) {

                temp_id = scanner.next();
                temp_email_address = scanner.next();
                temp_name = scanner.next();
                temp_ic = scanner.next();
                temp_phone_number = scanner.next();

                if(temp_id.trim().equals(id)) {

                    exist = true;

                    this.set_id(temp_id);
                    this.set_email_address(temp_email_address);
                    this.set_name(temp_name);
                    this.set_ic(temp_ic);
                    this.set_phone_number(temp_phone_number.trim());

                }

            }
            scanner.close();


        } catch (Exception e) {
            exist = false;
        }

    }

    public void find_customer_with_ic(String ic) {

        String temp_ic = "";

        try {
            scanner = new Scanner (new File(customer_filepath));
            scanner.useDelimiter("[,\n]");

            while(scanner.hasNext() && !exist) {

                temp_ic = scanner.next();

                if(temp_ic.trim().equals(ic)) {

                    exist = true;

                }

            }
            scanner.close();


        } catch (Exception e) {
            exist = false;
        }

    }

    public void edit_phone_number_and_email_address(String id, String new_phone_number, String new_email_address) {

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(customer_filepath)){
            scanner = new Scanner (fr);
            String line;
            String[] lineArr;

            while((line = scanner.nextLine())!=null) {
                lineArr = line.split(",");

                if(lineArr[0].equals(id)) {
                    tempArray.add(
                            lineArr[0] + "," +
                                    new_email_address + "," +
                                    lineArr[2] + "," +
                                    lineArr[3] + "," +
                                    new_phone_number
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

        try(PrintWriter pr = new PrintWriter(customer_filepath)){
            for (String str : tempArray) {
                pr.println(str);
            }

            pr.flush();
        }

        catch(Exception e) {

        }

    }

    // Setter
    public static void set_id(String input_id){
        id = input_id;
    }

    public void set_email_address(String input_email_address){
        email_address = input_email_address;
    }

    public void set_name(String input_name){
        name = input_name;
    }

    public void set_ic(String input_ic){
        ic = input_ic;
    }

    public void set_phone_number(String input_phone_number){
        phone_number = input_phone_number;
    }

    // Getter
    public static String get_id(){return id;}

    public String get_email_address(){
        return email_address;
    }

    public String get_name(){
        return name;
    }

    public String get_ic(){
        return ic;
    }

    public String get_phone_number(){
        return phone_number;
    }

    public boolean get_exist(){
        return exist;
    }

    @Override
    public String get_file_path() {
        return customer_filepath;
    }
}
