package com.example.oodj;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends User implements Database{

    private static Scanner scanner;
    private boolean exist = false;

    public Manager(){

    }

    public Manager(String input_id){
        super.set_id(input_id);
    }

    public Manager(String input_id, String input_password, String input_phone_number){

        super.set_id(input_id);
        super.set_password(input_password);
        super.set_phone_number(input_phone_number);
    }

    @Override
    public void registration(String name, String ic, String phone_number, String email_address, String password){

        int number_id = 1;

        // Get last id in manager database
        try {
            BufferedReader reader = new BufferedReader(new FileReader(manager_filepath));
            while (reader.readLine() != null) number_id++;
            reader.close();

        } catch (Exception ex){

        }

        // fit number id into full form id
        String id = "ma" + ("000000" + number_id).substring(String.valueOf(number_id).length());
        super.set_id(id);

        //add new manager
        String newLine =
                id + ","
                        + email_address + ","
                        + password + ","
                        + name + ","
                        + ic + ","
                        + phone_number;

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(manager_filepath, true));
            pw.println(newLine);
            pw.close();

        }
        catch (Exception e) {

        }

    }

    @Override
    public void find_user_with_email_address(String email_address){

        String temp_id = "";
        String temp_email_address = "";
        String temp_password = "";
        String temp_name = "";
        String temp_ic = "";
        String temp_phone_number = "";

        try {
            scanner = new Scanner (new File(manager_filepath));
            scanner.useDelimiter("[,\n]");

            while(scanner.hasNext() && !exist) {

                temp_id = scanner.next();
                temp_email_address = scanner.next();
                temp_password = scanner.next();
                temp_name = scanner.next();
                temp_ic = scanner.next();
                temp_phone_number = scanner.next();

                if(temp_email_address.trim().equals(email_address)) {

                    exist = true;

                    super.set_id(temp_id);
                    super.set_email_address(temp_email_address);
                    super.set_password(temp_password);
                    super.set_name(temp_name);
                    super.set_ic(temp_ic);
                    super.set_phone_number(temp_phone_number.trim());

                }

            }
            scanner.close();


        } catch (Exception e) {
            exist = false;
        }

    }

    @Override
    public void find_user_with_id(String id) {
        String temp_id = "";
        String temp_email_address = "";
        String temp_password = "";
        String temp_name = "";
        String temp_ic = "";
        String temp_phone_number = "";

        try {
            scanner = new Scanner (new File(manager_filepath));
            scanner.useDelimiter("[,\n]");

            while(scanner.hasNext() && !exist) {

                temp_id = scanner.next();
                temp_email_address = scanner.next();
                temp_password = scanner.next();
                temp_name = scanner.next();
                temp_ic = scanner.next();
                temp_phone_number = scanner.next();

                if(temp_id.trim().equals(id)) {

                    exist = true;

                    super.set_id(temp_id);
                    super.set_email_address(temp_email_address);
                    super.set_password(temp_password);
                    super.set_name(temp_name);
                    super.set_ic(temp_ic);
                    super.set_phone_number(temp_phone_number.trim());

                }

            }
            scanner.close();


        } catch (Exception e) {
            exist = false;
        }

    }

    @Override
    public void find_user_with_ic(String ic) {

        String temp_ic = "";

        try {
            scanner = new Scanner (new File(manager_filepath));
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

    @Override
    public void edit_password(String id, String new_password) {

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(manager_filepath)){
            scanner = new Scanner (fr);
            String line;
            String[] lineArr;

            while((line = scanner.nextLine())!=null) {
                lineArr = line.split(",");

                if(lineArr[0].equals(id)) {
                    tempArray.add(
                            lineArr[0] + "," +
                                    lineArr[1] + "," +
                                    new_password + "," +
                                    lineArr[3] + "," +
                                    lineArr[4] + "," +
                                    lineArr[5]
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

        try(PrintWriter pr = new PrintWriter(manager_filepath)){
            for (String str : tempArray) {
                pr.println(str);
            }

            pr.flush();
        }

        catch(Exception e) {

        }

    }


    @Override
    public void edit_password_and_phone_number(String id, String new_password, String new_phone_number) {

        ArrayList<String> tempArray = new ArrayList<>();

        try (FileReader fr = new FileReader(manager_filepath)){
            scanner = new Scanner (fr);
            String line;
            String[] lineArr;

            while((line = scanner.nextLine())!=null) {
                lineArr = line.split(",");

                if(lineArr[0].equals(id)) {
                    tempArray.add(
                            lineArr[0] + "," +
                                    lineArr[1] + "," +
                                    new_password + "," +
                                    lineArr[3] + "," +
                                    lineArr[4] + "," +
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

        try(PrintWriter pr = new PrintWriter(manager_filepath)){
            for (String str : tempArray) {
                pr.println(str);
            }

            pr.flush();
        }

        catch(Exception e) {

        }

    }


    public boolean get_exist(){
        return exist;
    }

    @Override
    public String get_file_path() {
        return manager_filepath;
    }
}
