
package com.mycompany.kristoffersonassignment1;
import java.util.Scanner;
/**
 *
 * @author Rachel Kristofferson
 * Date: July 2, 2025
 * 
 * Releases:
 *      1) July 2, 2025 - Formatted table for pet database and ability to 
 *                        add pets into database.
 *      2) July 2, 2025 - Added ability to search for pet by name and age. Will
 *                        return all matches to the queries that are in the 
 *                        database array. Also went through and formatted print
 *                        statements so that they're easier to read. Changed the
 *                        pet count at the end of the footer. Didn't realize it
 *                        was a sentence.
 *      3)
 * 
 * Sources:
 *  https://www.w3schools.com/java/ref_output_printf.asp
 */

class Pet {
    private String name;
    private int age;
    
    /*
        Creates a Pet object with a name and an age. The name cannot be 
        longer than 10 characters, so only takes first 10 and sets them to name.
    */
    public Pet(String name, int age) {
        if (name != null && name.length() > 10) {
            this.name = name.substring(0,10);
        }
        else {
            this.name = name;
        }
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    /*
        Ensures that the pet's name cannot be more than 10 characters in
        the database. Tourniquets names longer than 10 characters at 10.
    */
    public void setName(String name) {
        if (name != null && name.length() > 10) {
            this.name = name.substring(0,10);
        }
        else {
            this.name = name;
        }
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return String.format("| %-10s | %-3d |", name, age);
    }
}


public class KristoffersonAssignment1 {
    
    // Array representing pet database
    private static Pet[] pets = new Pet[100];
    
    /*
        Prints out the table header.
    */
    public void printTableHeader() {
        System.out.println("+------------------------+");
        System.out.println("| ID  | NAME       | AGE |");
        System.out.println("+------------------------+");
        
    }
    
    /*
        Prints out each row of the pet database. Formatted using Pet class
        toString.
    */
    public void printTableRows() {
        for (int i = 0; i < pets.length; i++) {
            if (pets[i] != null) {
                System.out.printf("| %-3d | %-10s | %-3d |\n", i, 
                        pets[i].getName(), pets[i].getAge());
            }
        }
    }
    
    
    /*
        Prints out the formatting for the table as well as a count of
        pets in the database after formatting.
    */
    public void printTableFooter() {
        System.out.print("+------------------------+");
        int petCount = 0;
        for (Pet pet : pets) {
            if (pet != null) {
                petCount++;
            }
        }
        System.out.println("\n" + petCount + " rows in set.");
        
    }
    
    /*
        Prints the full database in table format
    */
    public void printAllPets() {
        printTableHeader();
        printTableRows();
        printTableFooter();
    }
    
    /*
        Menu for main loop of program
    */
    public void printMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        System.out.println("3) Update an existing pet");
        System.out.println("4) Remove an existing pet");
        System.out.println("5) Search pets by name");
        System.out.println("6) Search pets by age");
        System.out.println("7) Exit program");
        System.out.print("\nYour choice: ");
        
    }
    
    /*
        Add pet to array database. Database limit is 100
    */
    public void addPet(String name, int age) {
        for (int i = 0; i < pets.length; i++) {
            if (pets[i] == null) {
                pets[i] = new Pet(name, age);
                System.out.println("Added pet with ID: " + i);
                return;
            }
        }
        System.out.println("Database is full");
    }
    
    /*
        Searches pet by name. If found, loops through all pets in the pets array
        to find all matches. Used IgnoreCase for any typing errors that may have
        occured.
    */
    public void searchPetByName(String name) {
        boolean found = false;
        
        for (int i = 0; i < pets.length; i++) {
            if (pets[i] != null && pets[i].getName().equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }
        
        if (found) {
            printTableHeader();
            
            for (int i = 0; i < pets.length; i++) {
                if (pets[i] != null && pets[i].getName().equalsIgnoreCase(name)) {
                    System.out.printf("| %-3d | %-10s | %-3d |\n", i,
                            pets[i].getName(), pets[i].getAge());
                }
            }
            printTableFooter();
        } 
        else {
            System.out.println("\nNo pets found with name: " + name + "\n");
        }
    }

    /*
        Searching pet by age. If found, loops through pets array to find all
        instances of pets with that age.
    */
    public void searchPetByAge(int age) {
        boolean found = false;
        
        for (int i = 0; i < pets.length; i++) {
            if (pets[i] != null && pets[i].getAge() == age) {
                found = true;
                break;
            }
        }
        
        if (found) {
            printTableHeader();
            
            for (int i = 0; i < pets.length; i++) {
                if (pets[i] != null && pets[i].getAge() == age) {
                    System.out.printf("| %-3d | %-10s | %-3d |\n", 
                        i, pets[i].getName(), pets[i].getAge());
                }
            }
            printTableFooter();
        }
        else {
            System.out.println("No pets found with that age: " + age);
        }
        
    }
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        KristoffersonAssignment1 database = new KristoffersonAssignment1();
        
        
        database.addPet("Tiny", 7);
        
        // Main loop
        boolean running = true;
        while (running) {
            database.printMenu();
            int choice = scanner.nextInt();
            
            // Cycle through menu via user choice
            switch(choice) {
                case 1:
                    database.printAllPets();
                    break;
                case 2:
                    System.out.print("\nEnter pet name: ");
                    String name = scanner.next();
                    System.out.print("Enter " + name + "'s age: ");
                    int age = scanner.nextInt();
                    database.addPet(name, age);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.print("\nEnter a name to search: ");
                    String searchName = scanner.next();
                    database.searchPetByName(searchName);
                    break;
                case 6:
                    System.out.print("\nEnter an age to search: ");
                    int searchAge = scanner.nextInt();
                    database.searchPetByAge(searchAge);
                    break;
                case 7: 
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
