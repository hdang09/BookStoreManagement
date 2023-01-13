package bookstoremanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.IDN;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class PublisherManagement {

    public static Scanner sc = new Scanner(System.in);
    public static String filePath = "src\\Publisher.dat";
    public static List<Publisher> publisherList = PublisherManagement.readFile();
    private static Publisher publisher = new Publisher();

    public static void menu() {
        System.out.println("1.1. Create a Publisher");
        System.out.println("1.2. Delete the Publisher");
        System.out.println("1.3. Save the Publishers list to file");
        System.out.println("1.4. Print the Publisher list from the file");
        System.out.println("Others. Go back to main menu");

        int choice = sc.nextInt();
        switch (choice) {
            case 1 ->
                PublisherManagement.create();
            case 2 ->
                PublisherManagement.delete();
            case 3 ->
                PublisherManagement.saveToFile();
            case 4 ->
                PublisherManagement.printFromFile();
            default ->
                Main.menu();
        }

    }

    public static boolean validate(String ID, String name, String phone) {

        return true;
    }

    public static void create() {
        boolean wrong;
        String idRegex = "[P]{1}\\d{5}";
        String nameRegex = ".{5,30}";
        String phoneRegex = "\\d{10,12}";

        // Validate ID
        do {
            System.out.println("Publisher's ID: ");
            String id = sc.next();
            for (Publisher pub : publisherList) {
                if (pub.getId().equals(id)) {
                    System.out.println("Publisher’s id is not allowed to duplicate");
                    wrong = true;
//                    break;
                }
            }
            if (Pattern.matches(idRegex, id)) {
                wrong = false;
                publisher.setId(id);
            } else {
                wrong = true;
                System.out.println("Publisher’s Id has pattern “Pxxxxx”, with xxxxx is five digits");
            }

        } while (wrong);

        // Validate name
        do {
            System.out.println("Publisher's name: ");
            String name = sc.next();
            if (Pattern.matches(nameRegex, name)) {
                wrong = false;
                publisher.setName(name);
            } else {
                System.out.println("Publisher’s Name is a string and has a length from 5 to 30 characters.");
                wrong = true;
            }
        } while (wrong);

        // Validate phone
        do {
            System.out.println("Publisher's phone: ");
            String phone = sc.next();
            if (!Pattern.matches(phoneRegex, phone)) {
                wrong = false;
                publisher.setPhone(phone);
            } else {
                wrong = true;
                System.out.println("The phone is a number string that has a length from 10 to 12");

            }
        } while (wrong);

        PublisherManagement.menu();
    }

    public static void delete() {
        System.out.println("Enter publisher's ID: ");
        String id = sc.next();

        boolean isDelete = false;
        for (Publisher pub : publisherList) {
            if (pub.getId().equals(id)) {
                isDelete = publisherList.remove(pub);
            }
        }

        if (isDelete) {
            System.out.println("Delete succesfully");
        } else {
            System.out.println("Publisher’s Id does not exist");
        }

        PublisherManagement.saveToFile();
        PublisherManagement.menu();
    }

    public static void saveToFile() {
        System.out.println(publisher);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            ObjectOutputStream obj = new ObjectOutputStream(fos);

            for (Publisher pu : publisherList) {
                obj.writeObject(pu);
            }

            obj.writeObject(publisher);
            publisherList.add(publisher);
            System.out.println("Write to file successfully");
            fos.close();
            obj.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        PublisherManagement.menu();
    }

    public static List<Publisher> readFile() {
        List<Publisher> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            try (ObjectInputStream obj = new ObjectInputStream(fis)) {
                boolean hasNext = true;
                while (hasNext) {
                    if (fis.available() != 0) {
                        Publisher pu = (Publisher) obj.readObject();
                        list.add(pu);
                    } else {
                        hasNext = false;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
            return list;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
            return list;
        }
        return list;
    }

    public static void printFromFile() {
        Collections.sort(publisherList);
        publisherList.forEach(System.out::println);
        PublisherManagement.menu();
    }
}
