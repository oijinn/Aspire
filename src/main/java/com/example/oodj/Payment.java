package com.example.oodj;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Payment implements Database{

    private static Scanner scanner;
    private boolean exist = false;

    private static String id;
    private static double price;
    private static double received;
    private static double change;

    public Payment(){

    }

    public void new_payment(double price){

        int number_id = 1;

        // Get last id in payment database
        try {
            BufferedReader reader = new BufferedReader(new FileReader(payment_filepath));
            while (reader.readLine() != null) number_id++;
            reader.close();

        } catch (Exception ex){

        }

        // fit number id into full form id
        String id = "pa" + ("000000" + number_id).substring(String.valueOf(number_id).length());
        this.set_id(id);

        //add new payment
        String newLine =
                id + ","
                + price + ","
                + received + ","
                + change;


        try {
            PrintWriter pw = new PrintWriter(new FileWriter(payment_filepath, true));
            pw.println(newLine);
            pw.close();

        }
        catch (Exception e) {

        }
    }

    public void complete_payment(String payment_id, double received, double change){

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(payment_filepath)){
            scanner = new Scanner (fr);
            String line;
            String[] lineArr;

            while((line = scanner.nextLine())!=null) {
                lineArr = line.split(",");

                if(lineArr[0].equals(payment_id)) {
                    tempArray.add(
                            lineArr[0] + "," +
                                    lineArr[1] + "," +
                                    received + "," +
                                    change
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

        try(PrintWriter pr = new PrintWriter(payment_filepath)){
            for (String str : tempArray) {
                pr.println(str);
            }

            pr.flush();
        }

        catch(Exception e) {

        }


    }

    public void find_payment_with_id(String payment_id) {

        String temp_id = "";
        String temp_price = "";
        String temp_received = "";
        String temp_change = "";

        try {
            scanner = new Scanner(new File(payment_filepath));
            scanner.useDelimiter("[,\n]");

            while(scanner.hasNext() && !exist) {

                temp_id = scanner.next();
                temp_price = scanner.next();
                temp_received  = scanner.next();
                temp_change = scanner.next();

                if(temp_id.trim().equals(payment_id)) {

                    exist = true;

                    this.set_id(temp_id);
                    this.set_price(Double.parseDouble(temp_price));
                    this.set_received(Double.parseDouble(temp_received));
                    this.set_change(Double.parseDouble(temp_change));

                }

            }
            scanner.close();


        } catch (Exception e) {
            exist = false;
        }

    }


    public double[] find_payment_details_with_id(String [] payment_id){

        double total_price = 0;
        double total_service_tax = 0;
        double total_amount = 0;
        double total_received = 0;
        double total_change = 0;

        List<String> payment_id_list = new ArrayList<>(Arrays.asList(payment_id));

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(payment_filepath)) {
            scanner = new Scanner(fr);
            String line;
            String[] lineArr;

            while ((line = scanner.nextLine()) != null) {
                lineArr = line.split(",");

                if (payment_id_list.contains(lineArr[0])) {

                    total_price += Double.parseDouble(lineArr[1]);
                    total_received += Double.parseDouble(lineArr[2]);
                    total_change +=  Double.parseDouble(lineArr[3]);

                } else {
                    tempArray.add(line);
                }
            }
            scanner.close();
        } catch (Exception e) {

        }

        // calculate total service tax
        total_service_tax = calculate_service_tax(total_price);

        // calculate total amount
        total_amount = total_price + total_service_tax;

        double [] details = {total_price, total_service_tax, total_amount, total_received,total_change};
        return details;
    }


    public double calculate_price (String appliance, int number_appliance){

        double price = 0;

        if (appliance.equals("small")){

            price += ((number_appliance * 150)) ;

        } else if (appliance.equals("large")){

            price += ((number_appliance * 500)) ;

        }

        return Math.round(price * 100.0) / 100.0;
    }

    public double calculate_service_tax (double price){

        double percentage = 0.15;

        double service_tax = price * percentage;

        return Math.round(service_tax * 100.0) / 100.0;
    }

    // Setter
    public void set_id(String input_id){
        id = input_id;
    }

    public void set_price(double input_price){
        price = input_price;
    }

    public void set_received(double input_received){
        received = input_received;
    }

    public void set_change(double input_change){
        change = input_change;
    }


    // Getter
    public String get_id(){return id;}

    public double get_price(){
        return price;
    }

    public double get_received(){
        return received;
    }

    public double get_change(){
        return change;
    }

    @Override
    public String get_file_path() {
        return payment_filepath;
    }
}
