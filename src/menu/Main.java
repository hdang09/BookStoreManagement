package menu;

import validate.Input;

public class Main {

    public static void main(String[] args) {
        Main.menu();
    }

    public static void menu() {
        Input input = new Input();
        
        System.out.println("------- BOOK STORE MANAGEMENT -------");
        System.out.println("|     1. Publishers' management     |");
        System.out.println("|       2. Books' management        |");
        System.out.println("|            Other. Quit            |");
        System.out.println("-------------------------------------");

        int choice = input.choice("Your choice: ");
        switch (choice) {
            case 1 ->
                PublisherManagement.menu();
            case 2 ->
                BookManagement.menu();
            default ->
                System.out.println("Good bye! Have a nice day");
        }
    }
}

// Type mismatch (try-catch in sc / regex)
// clear screen
// Not async with file (sometimes)
// status (string or boolean)
