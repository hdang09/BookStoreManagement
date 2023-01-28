package bookstoremanagement;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublisherManagement {
    public static Scanner sc = new Scanner(System.in);
    public static String filePath = "src\\data\\Publisher.dat";
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

    public static void create() {
        String id = new Input().publisherId("Publisher's ID: ");
        String name = new Input().publisherName("Publisher's name: ");
        String phone = new Input().publisherPhone("Publisher's phone: ");
        publisher = new Publisher(id, name, phone);

        PublisherManagement.menu();
    }

    public static void delete() {
        int deleteIndex = new Input().findPublisherIndexByID("Enter publisher's ID: ");

        if (deleteIndex != -1) {
            publisherList.remove(deleteIndex);
            System.out.println("Delete succesfully");
        } else {
            System.out.println("Publisher’s Id does not exist");
        }

        PublisherManagement.saveToFile();
    }

    public static void saveToFile() {
        System.out.println(publisher);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            ObjectOutputStream obj = new ObjectOutputStream(fos);

            for (Publisher pu : publisherList) {
                obj.writeObject(pu);
            }

            if (!publisher.getId().isBlank()) {
                obj.writeObject(publisher);
                publisherList.add(publisher);
            }
            System.out.println("Write to file successfully");
            publisher = new Publisher();
            obj.close();
            fos.close();
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
        try (FileInputStream fis = new FileInputStream(filePath)) {
            try (ObjectInputStream obj = new ObjectInputStream(fis)) {
                System.out.println("c");
                boolean hasNext = true;
                while (hasNext) {
                    if (fis.available() != 0) {
                        Publisher pu = (Publisher) obj.readObject();
                        list.add(pu);
                    } else {
                        hasNext = false;
                    }
                }
            } catch (EOFException e) {
                return list;
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
        if (publisherList.isEmpty()) {
            System.out.println("The list is empty!");
        } else {
            Collections.sort(publisherList);
            publisherList.forEach(System.out::println);
        }
        PublisherManagement.menu();
    }
}
