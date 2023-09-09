package com.example.oodj;

interface Database {

    String activity_log_filepath = "src/main/database/activity_log.txt";
    String appointment_filepath = "src/main/database/appointment.txt";
    String customer_filepath = "src/main/database/customer.txt";
    String feedback_filepath = "src/main/database/feedback.txt";
    String manager_filepath = "src/main/database/manager.txt";
    String payment_filepath = "src/main/database/payment.txt";
    String technician_filepath = "src/main/database/technician.txt";

    String get_file_path();

}
