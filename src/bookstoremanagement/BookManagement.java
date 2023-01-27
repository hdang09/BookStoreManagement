package bookstoremanagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BookManagement {

    static Scanner sc = new Scanner(System.in);
    public static String filePath = "src\\Book.dat";
    public static List<Book> books = BookManagement.readFIle();
    static Book book = new Book();

    public static void menu() {
        System.out.println("2.1. Create a Book");
        System.out.println("2.2. Search the Book");
        System.out.println("2.3. Update a Book");
        System.out.println("2.4. Delete the Book");
        System.out.println("2.5. Save the Books list to file.");
        System.out.println("2.6. Print the Books list from the file");
        System.out.println("Others. Go back to main menu");

        int choice = sc.nextInt();
        switch (choice) {
            case 1 ->
                BookManagement.create();
            case 2 ->
                BookManagement.search();
            case 3 ->
                BookManagement.update();
            case 4 ->
                BookManagement.delete();
            case 5 ->
                BookManagement.saveToFile();
            case 6 ->
                BookManagement.printFile();
            default ->
                Main.menu();
        }
    }

    public static void create() {
        boolean wrong;
        String idRegex = "[B]{1}\\d{5}";
        String nameRegex = ".{5,30}";

        // Validate ID
        do {
            System.out.print("Input book's ID: ");
            String id = sc.next();
            for (Book book : books) {
                if (book.getId().equals(id)) {
                    System.out.println("Publisher’s id is not allowed to duplicate");
                    wrong = true;
                    // break;
                }
            }
            if (Pattern.matches(idRegex, id)) {
                wrong = false;
                book.setId(id);
            } else {
                wrong = true;
                System.err.println("Publisher’s Id has pattern “Pxxxxx”, with xxxxx is five digits");
            }

        } while (wrong);

        // Validate name
        do {
            System.out.print("Input book's name: ");
            String name = sc.next();
            if (Pattern.matches(nameRegex, name)) {
                wrong = false;
                book.setName(name);
            } else {
                System.err.println("Publisher’s Name is a string and has a length from 5 to 30 characters.");
                wrong = true;
            }
        } while (wrong);

        // Validate price
        do {
            System.out.print("Input book's price: ");
            long price = sc.nextLong();
            wrong = price <= 0;
            if (wrong) {
                System.err.println("Book’s Price is a real number and greater than 0");
            } else {
                book.setPrice(price);
            }
        } while (wrong);

        // Validate quantity
        do {
            System.out.print("Input book's quantity: ");
            int quantity = sc.nextInt();
            wrong = quantity <= 0;
            if (wrong) {
                System.err.println("Book’s Quantity is an integer number and greater than 0.");
            } else {
                book.setQuantity(quantity);
            }
        } while (wrong);

        // Validate status
        do {
            System.out.print("Input book's status (Available/ Not Available): ");
            String status = sc.next();
            wrong = !status.equals("Available") && !status.equals("Available");
            if (wrong) {
                System.err.println("Status is a string and has values: Available or Not Available values");
            } else {
                book.setStatus(status);
            }
        } while (wrong);

        // Validate publisher's ID
        do {
            wrong = true;
            System.out.print("Input publisher's ID: ");
            String publisherId = sc.next();
            List<Publisher> publisherList = PublisherManagement.readFile();
            for (Publisher publisher : publisherList) {
                if (publisher.getId().equals(publisherId)) {
                    wrong = false;
                    break;
                }
            }
            if (wrong) {
                System.err.println("Publisher’s Id is not found");
            } else {
                book.setPublisherId(publisherId);
            }
        } while (wrong);
        
        BookManagement.menu();
    }

    public static void search() {
        System.out.print("Input book's name: ");
        String name = sc.next();
        System.out.print("Input publisher's ID: ");
        String publisherId = sc.next();

        boolean isFound = false;
        for (Book b : books) {
            if (b.getName().contains(name) || b.getPublisherId().equals(publisherId)) {
                System.out.println(b);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Have no any Book");
        }
        BookManagement.menu();
    }

    public static void update() {
        System.out.print("Input book's ID you want to update: ");
        String bookID = sc.next();

        boolean isFound = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(bookID)) {
                System.out.print("Input book's ID: ");
                String id = sc.next();
                System.out.print("Input book's name: ");
                String name = sc.next();
                System.out.print("Input book's price: ");
                long price = sc.nextLong();
                System.out.print("Input book's quantity: ");
                int quantity = sc.nextInt();
                System.out.print("Input book's status (Available/ Not Available): ");
                String status = sc.next();
                System.out.print("Input publisher's ID: ");
                String publisherId = sc.next();
                Book newBook = new Book(id, name, price, quantity, publisherId, status);
                books.set(i, newBook);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Book’s Name does not exist");
        }
        BookManagement.menu();
    }

    public static void delete() {
        System.out.print("Enter book's ID: ");
        String bookID = sc.next();

        boolean isFound = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(bookID)) {
                books.remove(i);
                isFound = true;
                System.out.println("Deleted successfully");
            }
        }
        if (!isFound) {
            System.out.println("Have no any Book");
        }
        BookManagement.menu();
    }

    public static void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            ObjectOutputStream obj = new ObjectOutputStream(fos);
            for (Book b: books) {
                obj.writeObject(b);
            }
            obj.writeObject(book);
            books.add(book);
            System.out.println("Save to file successfuly");
            fos.close();
            obj.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookManagement.menu();
    }

    public static List<Book> readFIle() {
        List<Book> booksFromFile = new ArrayList<>();
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
                boolean hasNext = true;
                while (hasNext) {
                    if (fis.available() != 0) {
                        Book b = (Book) obj.readObject();
                        booksFromFile.add(b);
                    } else {
                        hasNext = false;
                    }
                }
                obj.close();
            }
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookManagement.class.getName()).log(Level.SEVERE, null, ex);
            return booksFromFile;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BookManagement.class.getName()).log(Level.SEVERE, null, ex);
            return booksFromFile;
        }
        return booksFromFile;
    }

    public static void printFile() {
        books.forEach(System.out::println);
        BookManagement.menu();
    }

}
