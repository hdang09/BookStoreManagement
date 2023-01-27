/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstoremanagement;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Input {

    Scanner sc = new Scanner(System.in);
    List<Publisher> publisherList = PublisherManagement.readFile();
    boolean wrong;

    public String publisherId(String message) {
        String idRegex = "[P]{1}\\d{5}";
        String id;

        do {
            wrong = false;
            System.out.print(message);
            id = sc.next();

            if (Pattern.matches(idRegex, id)) {
                for (Publisher pub : publisherList) {
                    if (pub.getId().equals(id)) {
                        System.err.println("Publisher’s id is not allowed to duplicate");
                        wrong = true;
                        break;
                    }
                }
            } else {
                wrong = true;
                System.err.println("Publisher’s Id has pattern “Pxxxxx”, with xxxxx is five digits");
            }

            if (!wrong) {
                return id;
            }
        } while (wrong);
        return "";
    }

    public String publisherName(String message) {
        String nameRegex = ".{5,30}";
        String name;
        do {
            wrong = false;
            System.out.print(message);
            name = sc.next();

            if (Pattern.matches(nameRegex, name)) {
                return name;
            } else {
                wrong = true;
                System.err.println("Publisher’s Name is a string and has a length from 5 to 30 characters.");
            }
        } while (wrong);
        return "";
    }

    public String publisherPhone(String message) {
        String phoneRegex = "\\d{10,12}";
        String phone;
        do {
            wrong = false;
            System.out.print(message);
            phone = sc.next();

            if (Pattern.matches(phoneRegex, phone)) {
                return phone;
            } else {
                wrong = true;
                System.err.println("The phone is a number string that has a length from 10 to 12");
            }
        } while (wrong);
        return "";
    }

}
