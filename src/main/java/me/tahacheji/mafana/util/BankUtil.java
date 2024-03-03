package me.tahacheji.mafana.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BankUtil {

    public String generateRoutingNumber() {
        Random random = new Random();
        StringBuilder routingNumber = new StringBuilder();

        routingNumber.append(1 + random.nextInt(9));

        for (int i = 0; i < 8; i++) {
            routingNumber.append(random.nextInt(10));
        }

        return routingNumber.toString();
    }

    public String generateSavingsNumber() {
        Random random = new Random();
        StringBuilder savingsNumber = new StringBuilder();

        savingsNumber.append(1 + random.nextInt(9));

        for (int i = 0; i < 11; i++) {
            savingsNumber.append(random.nextInt(10));
        }

        return savingsNumber.toString();
    }

    public String generatePin() {
        Random random = new Random();
        StringBuilder pin = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            pin.append(random.nextInt(10));
        }

        return pin.toString();
    }

    public String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        cardNumber.append(1 + random.nextInt(9));

        for (int i = 1; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }

    public String generateCVV() {
        Random random = new Random();
        StringBuilder csv = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            csv.append(random.nextInt(10));
        }

        return csv.toString();
    }

    public String generateDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return currentDate.format(formatter);
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
        LocalDateTime now = LocalDateTime.now();
        return "[" + dtf.format(now) + "]";
    }

    public int getTime(String date) {
        String[] components = date.replaceAll("[\\[\\]]", "").split("[ /:]");
        if (components.length >= 6) {
            int month = Integer.parseInt(components[0]);
            int day = Integer.parseInt(components[1]);
            int year = Integer.parseInt(components[2]);
            int hour = Integer.parseInt(components[3]);
            int minute = Integer.parseInt(components[4]);
            String amPm = components[5];
            int time = year * 100000000 + month * 1000000 + day * 10000 + hour * 100 + minute;
            if (amPm.equalsIgnoreCase("PM")) {
                hour += 12;
                hour %= 24;
            }
            time += hour * 10000;
            return time;
        } else {
            System.err.println("Invalid date format: " + date);
            return 0;
        }
    }

}
