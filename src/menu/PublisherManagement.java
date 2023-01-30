package menu;

import validate.Input;
import object.Publisher;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublisherManagement {
    static String file = "src\\data\\Publisher.dat";
    static List<Publisher> publisherList = PublisherManagement.readFile();
    static Publisher publisher = new Publisher();
    static Input input = new Input();

    public static void menu() {
        System.out.println();
        System.out.println("----------- BOOK STORE MANAGEMENT -----------");
        System.out.println("| 1. Create a Publisher                     |");
        System.out.println("| 2. Delete the Publisher                   |");
        System.out.println("| 3. Save the Publishers list to file       |");
        System.out.println("| 4. Print the Publisher list from the file |");
        System.out.println("| Others. Go back to main menu              |");
        System.out.println("---------------------------------------------");

        int choice = input.choice("Your choice: ");
        System.out.println();
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

    static void create() {
        String id = input.publisherId("Publisher's ID: ");
        String name = input.publisherName("Publisher's name: ");
        String phone = input.publisherPhone("Publisher's phone: ");
        publisher = new Publisher(id, name, phone);

        input.menu("publisher");
    }

    static void delete() {
        int deleteIndex = new Input().findPublisherIndexByID("Enter publisher's ID: ");

        if (deleteIndex != -1) {
//            System.out.println(deleteIndex);
//            publisherList.forEach(System.out::println);
            publisherList.remove(deleteIndex);
            System.out.println("Delete succesfully");
            publisher = new Publisher();
            PublisherManagement.saveToFile();
        } else {
            System.err.println("Publisher's Id does not exist");
            input.menu("publisher");
        }
    }

    static void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            try (ObjectOutputStream obj = new ObjectOutputStream(fos)) {
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
            }

            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        input.menu("publisher");
    }

    public static List<Publisher> readFile() {
        List<Publisher> list = new ArrayList<>();
        File f = new File(file);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            try (ObjectInputStream obj = new ObjectInputStream(fis)) {
                boolean hasNext = true;
                while (hasNext) {
                    if (fis.available() != 0) {
                        object.Publisher pu = (object.Publisher) obj.readObject();
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

    static void printFromFile() {
        if (publisherList.isEmpty()) {
            System.err.println("The list is empty!");
        } else {
            Collections.sort(publisherList);
            System.out.println("---------------------------------------------------------------");
            System.out.println("| PublisherID |              Name              |     Phone    |");
            System.out.println("|-------------|--------------------------------|--------------|");
            publisherList.forEach(System.out::println);
            System.out.println("---------------------------------------------------------------");
        }
        input.menu("publisher");
    }
}
