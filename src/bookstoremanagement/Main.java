package bookstoremanagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main.menu();
    }
    
    public static void menu() {
        System.out.println("1. Publishers’ management");
        System.out.println("2. Books management");
        
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        
        switch (choice) {
            case 1 -> PublisherManagement.menu();
            case 2 -> BookManagement.menu();
            default -> throw new AssertionError();
        }
    }
}

// ASC Publisher list