package com.example.oodj;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Clock {

    // Get current date or time with format
    public static String get_current_date_time(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();

        return (dateFormat.format(date));
    }

    // Real time clock with format
    public static void real_time_clock(Label label, String format) {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            label.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public static String date_time_formatter(String input, String old_format , String new_format) throws ParseException {

        String result;

        SimpleDateFormat format = new SimpleDateFormat(old_format);
        Date time = format.parse(input);

        format.applyPattern(new_format);
        result = format.format(time);

        return result;
    }

}
