package menu;

import object.Book;
import validate.Input;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookManagement {

    static Scanner sc = new Scanner(System.in);
    public static String filePath = "src\\data\\Book.dat";
    public static List<Book> books = BookManagement.readFIle();
    static Book book = new Book();
    static Input input = new Input();

    public static void menu() {
        System.out.println();
        System.out.println("--------- BOOK STORE MANAGEMENT --------");
        System.out.println("| 1. Create a Book                      |");
        System.out.println("| 2. Search the Book                    |");
        System.out.println("| 3. Update a Book                      |");
        System.out.println("| 4. Delete the Book                    |");
        System.out.println("| 5. Save the Books list to file.       |");
        System.out.println("| 6. Print the Books list from the file |");
        System.out.println("| Others. Go back to main menu          |");
        System.out.println("-----------------------------------------");

        System.out.print("Your choice: ");
        int choice = sc.nextInt();
        System.out.println();
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
        String id = input.bookId("Input book's ID: ");
        String name = input.bookName("Input book's name: ");
        Double price = input.bookPrice("Input book's price: ");
        int quantity = input.bookQuantity("Input book's quantity: ");
        String status = input.bookStatus("Input book's status (Available/ Not Available): ");
        String publisherId = input.findPublisherId("Input publisher's ID: ");

        book = new Book(id, name, price, quantity, publisherId, status);
        input.menu("book");
    }

    public static void search() {
        String name = input.bookName("Input book's name: ");
        String publisherId = input.findPublisherId("Input publisher's ID: ");

        boolean isFound = false;
        for (Book b : books) {
            if (b.getName().contains(name) || b.getPublisherId().equals(publisherId)) {
                System.out.println(b);
                isFound = true;
            }
        }
        if (!isFound) {
            System.err.println("Have no any Book");
        }
        input.menu("book");
    }

    public static void update() {
        String bookID = input.findBookId("Input book's ID you want to update: ");

        boolean isFound = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(bookID)) {
                String id = input.bookId("Input book's ID you want to change: ");
                String name = input.bookName("Input book's name: ");
                Double price = input.bookPrice("Input book's price: ");
                int quantity = input.bookQuantity("Input book's quantity: ");
                String status = input.bookStatus("Input book's status (Available/ Not Available): ");
                String publisherId = input.findPublisherId("Input publisher's ID: ");

                isFound = true;
                book = new Book(id, name, price, quantity, publisherId, status);
                books.set(i, book);
                book = new Book();
                BookManagement.saveToFile();
                break;
            }
        }
        if (!isFound) {
            System.err.println("Book's Name does not exist");
        }
        input.menu("book");
    }

    public static void delete() {
        int bookIndex = new Input().findBookIndexById("Enter book's ID: ");

        if (bookIndex != -1) {
            books.remove(bookIndex);
            System.out.println("Deleted successfully");
            BookManagement.saveToFile();
        } else {
            System.err.println("Have no any Book");
            BookManagement.menu();
        }
        input.menu("book");
    }

    public static void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            try (ObjectOutputStream obj = new ObjectOutputStream(fos)) {
                for (Book b : books) {
                    obj.writeObject(b);
                }
                if (!book.getId().isBlank()) {
                    obj.writeObject(book);
                    books.add(book);
                }
                System.out.println("Write to file successfuly");
                book = new Book();
                fos.close();
                obj.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublisherManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        input.menu("book");
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
            } catch (EOFException e) {
                return booksFromFile;
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
        if (books.isEmpty()) {
            System.err.println("The list is empty!");
        } else {
            Collections.sort(books);
            System.out.println("----------------------------------------------------------------------------------------------------------------");
            System.out.println("| BookID |              Name              |    Price   |  Quantity  |   Subtotal | PublisherID |     Status    |");
            System.out.println("|--------|--------------------------------|------------|------------|------------|-------------|---------------|");
            books.forEach(System.out::println);
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        }
        input.menu("book");
    }

}
