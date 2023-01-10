package bookstoremanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublisherManagement {

    public static Scanner sc = new Scanner(System.in);
    public static String filePath = "src\\Publisher.dat";
    public static List<Publisher> publisherList = PublisherManagement.readFile();
    private static Publisher publisher;
    

    public static void menu() {
        System.out.println("1.1. Create a Publisher");
        System.out.println("1.2. Delete the Publisher");
        System.out.println("1.3. Save the Publishers list to file");
        System.out.println("1.4. Print the Publisher list from the file");

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
        System.out.println("Publisher's ID: ");
        int id = sc.nextInt();
        System.out.println("Publisher's name: ");
        String name = sc.next();
        System.out.println("Publisher's phone: ");
        String phone = sc.next();

        publisher = new Publisher(id, name, phone);

        PublisherManagement.menu();
    }
    
    public static void delete() {
        System.out.println("Enter publisher's ID: ");
        int id = sc.nextInt();

        publisherList.forEach((item) -> {
            if (item.getId() == id) {
                publisherList.remove(id);
            }
        });
        PublisherManagement.menu();
    }

    public static void saveToFile() {
        System.out.println(publisher);
        
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            ObjectOutputStream obj = new ObjectOutputStream(fos);

            List<Publisher> publisherListFild = PublisherManagement.readFile();
            for (Publisher pu: publisherListFild) {
                obj.writeObject(pu);
            }
            
            obj.writeObject(publisher);
            System.out.println("Write to file successfully");
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
        boolean hasNext = true;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            try (ObjectInputStream obj = new ObjectInputStream(fis)) {
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
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static void printFromFile() {
        List<Publisher> publisherList = PublisherManagement.readFile();
        publisherList.forEach(System.out::println);
        PublisherManagement.menu();
    }
}
