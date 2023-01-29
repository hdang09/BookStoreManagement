package bookstoremanagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main.menu();
    }
    
    public static void menu() {
        System.out.println("1. Publishers’ management");
        System.out.println("2. Books management");
        System.out.println("Others: Exit");
        
        System.out.print("Your choice: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        
        switch (choice) {
            case 1 -> PublisherManagement.menu();
            case 2 -> BookManagement.menu();
            default -> System.out.println("Good bye! Have a nice day");
        }
    }
}

// Type mismatch
// sc choice (optional)
// clear screen / add space top bottom menu
// Not async with file