package bookstoremanagement;

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

public class BookManagement {

    static Scanner sc = new Scanner(System.in);
    static String filePath = "src\\Book.dat";
    static List<Book> books = BookManagement.readFIle();
    static Book book = null;

    public static void menu() {
        System.err.println("2.1. Create a Book");
        System.err.println("2.2. Search the Book");
        System.err.println("2.3. Update a Book");
        System.err.println("2.4. Delete the Book");
        System.err.println("2.5. Save the Books list to file.");
        System.err.println("2.6. Print the Books list from the file");

        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> BookManagement.create();
            case 2 -> BookManagement.search();
            case 3 -> BookManagement.update();
            case 4 -> BookManagement.delete();
            case 5 -> BookManagement.create();
            case 6 -> BookManagement.create();
            default -> Main.menu();
        }
    }

    public static void create() {
        System.out.print("Input book's ID: ");
        int id = sc.nextInt();
        System.out.print("Input book's name: ");
        String name = sc.next();
        System.out.print("Input book's price: ");
        long price = sc.nextLong();
        System.out.print("Input book's quantity: ");
        int quantity = sc.nextInt();
        System.out.print("Input book's status (Available/ Not Available): ");
        String status = sc.next();
        System.out.print("Input publisher's ID: ");
        int publisherId = sc.nextInt();
        book = new Book(id, name, price, quantity, publisherId, status);

        boolean isFound = false;
        List<Publisher> publisherList = PublisherManagement.readFile();
        for (Publisher publisher : publisherList) {
            if (publisher.getId() == publisherId) {
                isFound = true;
            }
        }
        if (isFound) {
            books.add(book);
        } else {
            System.out.println("Publisher’s Id is not found");
        }

        BookManagement.menu();
    }

    public static void search() {
        System.out.print("Input book's name: ");
        String name = sc.next();
        System.out.print("Input publisher's ID: ");
        int publisherId = sc.nextInt();

        boolean isFound = false;
        for (Book book : books) {
            if (book.getName().contains(name) || book.getPublisherId() == publisherId) {
                System.out.println(book);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Have no any Book");
        }
        BookManagement.menu();
    }

    public static void update() {
        System.out.print("Input book's ID: ");
        int bookID = sc.nextInt();

        boolean isFound = false;
        for (Book book : books) {
            if (book.getId() == bookID) {
                System.out.print("Input book's ID: ");
                int id = sc.nextInt();
                System.out.print("Input book's name: ");
                String name = sc.next();
                System.out.print("Input book's price: ");
                long price = sc.nextLong();
                System.out.print("Input book's quantity: ");
                int quantity = sc.nextInt();
                System.out.print("Input book's status (Available/ Not Available): ");
                String status = sc.next();
                System.out.print("Input publisher's ID: ");
                int publisherId = sc.nextInt();
                Book newbook = new Book(id, name, price, quantity, publisherId, status);
                books.set(book.getId(), newbook);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Book’s Name does not exist");
        }
        BookManagement.menu();
    }

    public static void delete() {
        System.out.print("Enter book's ID: ");
        int bookID = sc.nextInt();

        boolean isFound = false;
        for (Book book : books) {
            if (book.getId() == bookID) {
                books.remove(bookID);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Have no any Book");
        }
        BookManagement.menu();
    }

    public static void saveToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream obj = new ObjectOutputStream(fos);
            obj.writeObject(book);
            System.out.println("Save to file successfuly");
            fos.close();
        } catch (IOException ex) {
            System.out.println("Error while write to file");
        }
        BookManagement.menu();
    }

    public static List<Book> readFIle() {
        List<Book> booksFromFile = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream obj = new ObjectInputStream(fis);

            boolean hasNext = true;
            while (hasNext) {
                if (fis.available() != 0) {
                    Book bookFromFile = (Book) obj.readObject();
                    booksFromFile.add(bookFromFile);
                } else {
                    hasNext = false;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BookManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

        return booksFromFile;
    }
    
    public static void printFile() {
        for (Book b: books) {
            System.out.println(b);
        }
        BookManagement.menu();
    }
    
}
