package com.example.oodj;

public class Identifier {

    // 0 = Manager
    // 1 = Technician
    // 2 = NA

    private static char identity_user = 2;

    public static String identify_user_gender(char gender){
        String final_gender;

        if (gender == 0){
            final_gender = "Female";

        } else {
            final_gender = "Male";
        }

        return final_gender;
    }


    public static void identify_user_with_email_address(String user_email_address){

        // user is manager?
        Manager manager = new Manager();
        manager.find_user_with_email_address(user_email_address);
        boolean exist = manager.get_exist();

        if (exist) {

            identity_user = 0;


        } else {

            // user is technician?
            Technician technician = new Technician();
            technician.find_user_with_email_address(user_email_address);
            exist = technician.get_exist();

            if (exist) {
                identity_user = 1;


            } else {
                identity_user = 2;

            }

        }
    }

    public static void identify_user_with_id(String user_id){

        // user is manager?
        Manager manager = new Manager();
        manager.find_user_with_id(user_id);
        boolean exist = manager.get_exist();

        if (exist) {

            identity_user = 0;


        } else {

            // user is technician?
            Technician technician = new Technician();
            technician.find_user_with_id(user_id);
            exist = technician.get_exist();

            if (exist) {
                identity_user = 1;


            } else {
                identity_user = 2;

            }

        }

    }

    public static char get_identity_of_user(){
        return identity_user;
    }



    public static String identify_appointment_status(String input_appointment_status){

        String appointment_status = "na";

        if(input_appointment_status.equals("0")){

            appointment_status = "Pending";

        } else if (input_appointment_status.equals("1")){

            appointment_status = "Cancelled";

        } else if (input_appointment_status.equals("2")){

            appointment_status = "Completed";

        }

        return appointment_status;

    }



}
