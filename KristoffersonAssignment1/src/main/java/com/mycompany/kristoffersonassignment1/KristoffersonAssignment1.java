
package com.mycompany.kristoffersonassignment1;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
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
 *      3) July 3, 2025 - Added functionality to update or remove a pet's
 *                        information in the database. Added in scanner.nextLine
 *                        because it can handle 2 worded names. Also needed to
 *                        add in other blank lines so that it will work
 *                        properly.
 *      4) July 9, 2025 - Changed the addPet() method to write new pet's to a
 *                        file, "petDatabase.txt" and changed the limit of the
 *                        array to 5. Also changed user input to write both pet's
 *                        name and age on one line in case 2 in main(). Added 
 *                        loadFromFile() method that loads information from a 
 *                        file and populates the database array with that info.
 *                        Changed the removePet() method to write to a file. Needed
 *                        to close the writer to save the changes made, otherwise
 *                        it erased the file.
 * 
 * Sources:
 *  https://www.w3schools.com/java/ref_output_printf.asp
 * 
 *  https://www.reddit.com/r/learnprogramming/comments/uaelit/nextline_in_java/
 * 
 * https://www.youtube.com/watch?v=ScUJx4aWRi0&t=376s
 * 
 * https://www.geeksforgeeks.org/java/java-io-writer-class-java/
 * 
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
    private static Pet[] pets = new Pet[5];
    
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
        Method to load data from file, "petDatabase.txt" that will parse through
        the lines of the file and clean up the data and assign it to the correct
        variable to make a new Pet instance.
    */
    public void loadFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("petDatabase.txt"));
            String line;
            int index = 0;
            
            for (int i = 0; i < pets.length; i++) {
                pets[i] = null;
            }
            
            while ((line = reader.readLine()) != null && index < pets.length) {
                String[] parts = line.split(",");
                // Cleans up data from file. Assigns data to variable.
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int age = Integer.parseInt(parts[1].trim());
                    pets[index] = new Pet(name, age);
                    index++;
                }
            }
            reader.close();
        } catch(IOException e){
            System.out.println("File not found, data base is empty. Add pet"
                    + "to create database.");
        }
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
        Add pet to array database. Database limit is 5
        Updated to write new pets to file, "petDatabase.txt"
    */
    public void addPet(String name, int age) {
        for (int i = 0; i < pets.length; i++) {
            if (pets[i] == null) {
                pets[i] = new Pet(name, age);
                
                try {
                    BufferedWriter writer = new BufferedWriter(new 
                            FileWriter("petDatabase.txt", true));
                    writer.write(name + "," + age + "\n");
                    writer.close();
                    System.out.println("Added: " + name + ", " + age);
                    writer.close();
                } catch(IOException e){
                    System.out.println("Error: Unable to write to file: " 
                            + e.getMessage());
                }
                System.out.println("Added pet with ID: " + i);
                return;
            }
        }
        System.out.println("\nDatabase is full. Remove a pet to add more.");
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
    
    /*
        Updates a pet's name and age based on their petID.
    */
    public void updatePet(int petID, String updateName, int updateAge) {
        if (petID < 0 || petID >= pets.length || pets[petID] == null) {
            System.out.println("Invalid petID: " + petID);
            return;
        }
        
        // Capture old name and age for print statement
        String tempName = pets[petID].getName();
        int tempAge = pets[petID].getAge();
        
        pets[petID].setName(updateName);
        pets[petID].setAge(updateAge);
        // Confirmation of name change.
        System.out.println(tempName + ", aged " + tempAge + " has been changed to, " 
                + updateName + ", aged " + updateAge);
        
    }
    
    /*
        Removes a pet from the database by using their array index (petID). 
        Added file read/write capabilities when removing a pet. Method rewrites
        the file when setting an element in the array to null, skipping over
        that value.
    */
    public void removePet(int deleteID) {
        if (deleteID < 0 || deleteID >= pets.length || pets[deleteID] == null) {
            System.out.println("Invalid petID: " + deleteID);
            return;
        }
        
        // Shift all objects in array that are to the right of the removed index left.
        String removePet = pets[deleteID].getName();
        
        for (int i = deleteID; i < pets.length - 1; i++) {
            pets[i] = pets[i + 1];
        }
        
        pets[pets.length - 1] = null;
        
        // Rewrite the file with remaining pets while skipping over null.
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("petDatabase.txt"));
            for (Pet pet : pets) {
                if (pet != null) {
                    writer.write(pet.getName() + "," + pet.getAge() + "\n");
                }
            }
            // Need this line otherwise file will be erased!
            writer.close();
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + e.getMessage());
        }
        
        System.out.println(removePet + " has been removed from database.");
        
    }
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        KristoffersonAssignment1 database = new KristoffersonAssignment1();
        
        // Populate the array with info from file. File is created in addPet()
        // if it doesn't exist.
        database.loadFromFile();
        
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
                    System.out.print("Enter pet (name, age): ");
                    scanner.nextLine();
                    String name = scanner.next();
                    int age = scanner.nextInt();
                    database.addPet(name, age);
                    break;
                case 3:
                    database.printAllPets();
                    System.out.println("\nEnter the pet ID to update: ");
                    int petID = scanner.nextInt();
                    System.out.print("\nEnter new name: ");
                    scanner.nextLine();
                    String updateName = scanner.nextLine();
                    System.out.print("Enter new age: ");
                    int updateAge = scanner.nextInt();
                    database.updatePet(petID, updateName, updateAge);
                    break;
                case 4:
                    database.printAllPets();
                    System.out.println("\nEnter ID to remove: ");
                    try {
                        int deleteID = scanner.nextInt();
                        database.removePet(deleteID);
                    }
                    catch(InputMismatchException e){
                        System.out.println("Invalid value, expected a numeric integer value. Example: 1");
                        scanner.nextLine(); // Cleaning the scanner's buffer
                    }
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.print("\nEnter a name to search: ");
                    String searchName = scanner.nextLine();
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
