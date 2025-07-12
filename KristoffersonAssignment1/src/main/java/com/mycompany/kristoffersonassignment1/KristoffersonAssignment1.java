
package com.mycompany.kristoffersonassignment1;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.InputMismatchException;
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
 *      4) July 9, 2025 - Changed the addPet() method to write new pets to a
 *                        file, "petDatabase.txt" and changed the limit of the
 *                        array to 5. Also changed user input to write both pet's
 *                        name and age on one line in case 2 in main(). Added 
 *                        loadFromFile() method that loads information from a 
 *                        file and populates the database array with that info.
 *                        Changed the removePet() method to write to a file. Needed
 *                        to close the writer to save the changes made, otherwise
 *                        it erased the file.
 *      5) July 10, 2025 - Went through code and tested everything to make sure
 *                         everything was behaving as expected. Cleaned up code
 *                         to match what is described in the assignment (only
 *                         have 4 menu choices instead of 7). Did error checking
 *                         on removePet(), which resulted in isFileEmpty(). Added
 *                         method isFileEmpty() to check if the file is empty, 
 *                         if so user cannot try to remove from an empty file.
 *                         Error checked addPet and handled pets with two names
 *                         i.e. tater tot to be entered using a hyphen. In the
 *                         Pet class toString the hyphen is removed and space is
 *                         added in table display, but is stored in file with
 *                         hyphen. Added method ifFileFull() to check if the file
 *                         is full before attempting to add a pet. If it is full,
 *                         message will appear notifying user pet cannot be added.
 * 
 * Sources:
 *  https://www.w3schools.com/java/ref_output_printf.asp
 * 
 *  https://www.reddit.com/r/learnprogramming/comments/uaelit/nextline_in_java/
 * 
 *  https://www.youtube.com/watch?v=ScUJx4aWRi0&t=376s
 * 
 *  https://www.geeksforgeeks.org/java/java-io-writer-class-java/
 * 
 *  https://www.geeksforgeeks.org/java/types-of-exception-in-java-with-examples/
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
        the database. Truncates names longer than 10 characters at 10. Full
        name will be stored in file, but will display only 10 characters.
    */
    public void setName(String name) {
        if (name != null && name.length() > 10) {
            this.name = name.substring(0,10);
        }
        else {
            this.name = name;
        }
    }
    // Age can only be between 0 and 20
    public void setAge(int age) {
        if (age >= 0 && age <= 20) {
            this.age = age;
        }
        else {
            throw new IllegalArgumentException ("Age must be between 0 and 20");
        }
    }
    
    // Displays pet with multiple words nicely. 
    // Ex: tater-tot(input/file) = tater tot(display)
    @Override
    public String toString() {
        String displayName = name.replace("-", " ");
        return String.format("| %-10s | %-3d |", displayName, age);
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
                System.out.printf("| %-3d %s\n", i, pets[i].toString());
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
            System.out.println("File not found, database is empty. Add pet"
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
        System.out.println("3) Remove an existing pet");
        System.out.println("4) Exit program");
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
    
    /*
        Runs in case 3, remove a pet. This will check if there are any pets in
        the file before continuing on with the remove pet command. If there are
        no pets, then a message will immediately appear and case will break.
    */
    public boolean isFileEmpty() {
        for(Pet pet : pets) {
            if(pet != null) {
                return true;
            } 
        }
        return false;
    }
    
    /*
        Runs in case 2, add pet. This will check if the database is full. If so,
        it will display a message immediately informing the user that you cannot
        add a pet at this time.
    */
    public boolean isFileFull() {
        for(Pet pet : pets) {
            if(pet == null) {
                return false; // Not full
            }
        }
        // File is full
        return true;
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
                    // Check if database is full first before continuing.
                    if(database.isFileFull()) {
                        System.out.println("Database is full, you cannot add"
                                + " a pet at this time. Remove a pet to add more.");
                        break;
                    }
                    
                    System.out.print("Use hyphen (-) to separate pet with "
                            + "multiple names. Ex: tater-tot\n"
                            + "Enter pet (name, age):");
                    scanner.nextLine();
                    
                    try {
                        String name = scanner.next();
                        int age = scanner.nextInt();
                        // Handles extra input
                        String extraInput = scanner.nextLine().trim();
                        if(extraInput.isEmpty() == false) {
                            System.out.println("Invalid input for adding pet.");
                            break;
                        }
                         
                        if(name.trim().isEmpty()) {
                            System.out.println("Pet name cannot be empty.");
                            break;
                        }
                        // Makes sure age is between 0-20
                        if(age < 0 || age > 20) {
                            System.out.println("Invalid Age. Age must be "
                                    + "between 0 and 20");
                            break;
                        }
                        database.addPet(name, age);
                    } catch (Exception e) {
                        // Catches anything besides a String int as input
                        System.out.println("Invalid input. Please enter: name "
                                + "age (ex. Kitty 7)");
                        scanner.nextLine();
                    }
                    
                    break;
                case 3:
                    // If file is empty break
                    if(database.isFileEmpty() == false) {
                        System.out.println("No pets in database to remove.");
                        break;
                    }
                    database.printAllPets();
                    System.out.print("\nEnter ID to remove: ");
                    try {
                        int deleteID = scanner.nextInt();
                        database.removePet(deleteID);
                    } catch(InputMismatchException e) {
                        // Catches if input is anything but type int
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine(); // Clears the scanner for next input
                    }
                    break;
                case 4: 
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
