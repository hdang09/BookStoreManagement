package object;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookList extends ArrayList<Book> {

    String file = "src\\data\\Book.dat";
    Input input = new Input();

    public void create(Book book) {
        this.add(book);
    }

    public void search(String name, String publisherId) {
        boolean isFound = false;
        for (Book b : this) {
            if (b.getName().contains(name) || b.getPublisherId().equals(publisherId)) {
                System.out.println("----------------------------------------------------------------------------------------------------------------");
                System.out.println("| BookID |              Name              |    Price   |  Quantity  |   Subtotal | PublisherID |     Status    |");
                System.out.println("|--------|--------------------------------|------------|------------|------------|-------------|---------------|");
                System.out.println(b);
                System.out.println("----------------------------------------------------------------------------------------------------------------");
                isFound = true;
            }
        }
        if (!isFound) {
            System.err.println("Have no any Book");
        }
    }

    public void update(String bookID, ArrayList<Publisher> publisherList) {
        boolean isFound = false;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(bookID)) {
                String id = input.bookId("Input book's ID you want to change: ", this);
                String name = input.bookName("Input book's name: ");
                Double price = input.bookPrice("Input book's price: ");
                int quantity = input.bookQuantity("Input book's quantity: ");
                String status = input.bookStatus("Input book's status (Available/ Not Available): ");
                String publisherId = input.findPublisherId("Input publisher's ID: ", publisherList);

                isFound = true;
                Book book = new Book(id, name, price, quantity, publisherId, status);
                this.set(i, book);
                break;
            }
        }
        if (!isFound) {
            System.err.println("Book's Name does not exist");
        }
    }

    public void delete(int bookIndex) {
        if (bookIndex != -1) {
            this.remove(bookIndex);
            System.out.println("Deleted successfully");
            this.saveToFile();
        } else {
            System.err.println("Have no any Book");
        }
    }

    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            try (ObjectOutputStream obj = new ObjectOutputStream(fos)) {
                for (Book b : this) {
                    obj.writeObject(b);
                }
                System.out.println("Write to file successfuly");
                fos.close();
                obj.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BookList readFIle() {
        File f = new File(file);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            try (ObjectInputStream obj = new ObjectInputStream(fis)) {
                boolean hasNext = true;
                while (hasNext) {
                    if (fis.available() != 0) {
                        Book b = (Book) obj.readObject();
                        this.add(b);
                    } else {
                        hasNext = false;
                    }
                }
                obj.close();
            } catch (EOFException e) {
                return this;
            }
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
            return this;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
            return this;
        }
        return this;
    }

    public void printFile() {
        if (this.isEmpty()) {
            System.err.println("The list is empty!");
        } else {
            Collections.sort(this);
            System.out.println("----------------------------------------------------------------------------------------------------------------");
            System.out.println("| BookID |              Name              |    Price   |  Quantity  |   Subtotal | PublisherID |     Status    |");
            System.out.println("|--------|--------------------------------|------------|------------|------------|-------------|---------------|");
            this.forEach(System.out::println);
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        }
    }
}
