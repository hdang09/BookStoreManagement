package menu;

import object.Book;
import object.BookList;
import object.Publisher;
import object.PublisherList;
import validate.Input;

public class Main {

    PublisherList publisherList = new PublisherList().readFile();
    BookList bookList = new BookList().readFIle();
    Input input = new Input();
    boolean isContinue;

    public static void main(String[] args) {
        new Main().mainMenu();
    }

    public void publisherMenu() {
        do {
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
                case 1 -> {
                    String id = input.publisherId("Publisher's ID: ", publisherList);
                    String name = input.publisherName("Publisher's name: ");
                    String phone = input.publisherPhone("Publisher's phone: ");
                    publisherList.create(new Publisher(id, name, phone));
                }
                case 2 -> {
                    int deleteIndex = new Input().findPublisherIndexByID("Enter publisher's ID: ", publisherList);
                    publisherList.delete(deleteIndex);
                }
                case 3 ->
                    publisherList.saveToFile();
                case 4 ->
                    publisherList.printFromFile();
                default ->
                    mainMenu();
            }
            isContinue = input.menu();
            if (!isContinue) {
                return;
            }
        } while (isContinue);

    }

    public void bookMenu() {
        do {
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

            int choice = input.choice("Your choice: ");
            System.out.println();
            switch (choice) {
                case 1 -> {
                    String id = input.bookId("Input book's ID: ", bookList);
                    String name = input.bookName("Input book's name: ");
                    Double price = input.bookPrice("Input book's price: ");
                    int quantity = input.bookQuantity("Input book's quantity: ");
                    String status = input.bookStatus("Input book's status (Available/ Not Available): ");
                    String publisherId = input.findPublisherId("Input publisher's ID: ", publisherList);
                    Book book = new Book(id, name, price, quantity, publisherId, status);
                    bookList.create(book);
                }
                case 2 -> {
                    String name = input.bookName("Input book's name: ");
                    String publisherId = input.findPublisherId("Input publisher's ID: ", publisherList);
                    bookList.search(name, publisherId);
                }
                case 3 -> {
                    String bookID = input.findBookId("Input book's ID you want to update: ", bookList);
                    bookList.update(bookID, publisherList);
                }
                case 4 -> {
                    int bookIndex = new Input().findBookIndexById("Enter book's ID: ", bookList);
                    bookList.delete(bookIndex);
                }
                case 5 ->
                    bookList.saveToFile();
                case 6 ->
                    bookList.printFile();
                default ->
                    mainMenu();
            }
            isContinue = input.menu();
            if (!isContinue) {
                return;
            }

        } while (isContinue);
    }

    public void mainMenu() {
        System.out.println("------- BOOK STORE MANAGEMENT -------");
        System.out.println("|     1. Publishers' management     |");
        System.out.println("|       2. Books' management        |");
        System.out.println("|            Other. Quit            |");
        System.out.println("-------------------------------------");

        int choice = input.choice("Your choice: ");
        switch (choice) {
            case 1 ->
                publisherMenu();
            case 2 ->
                bookMenu();
            default ->
                System.out.println("Good bye! Have a nice day");
        }
    }
}
