import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class notes{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileName = "notes.txt"; // file to store notes

        while (true) {
            System.out.println("\nNote Manager Menu:");
            System.out.println("1. Add Note");
            System.out.println("2. Display Notes");
            System.out.println("3. Edit Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Search Note");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Note: ");
                    String note = sc.nextLine();
                    addNoteToFile(fileName, note);
                    break;
                case 2:
                    displayNotesFromFile(fileName);
                    break;
                case 3:
                    editNoteInFile(fileName, sc);
                    break;
                case 4:
                    deleteNoteFromFile(fileName, sc);
                    break;
                case 5:
                    searchNoteInFile(fileName, sc);
                    break;
                case 6:
                    System.out.println("Exiting Note Manager. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    // Add note
    public static void addNoteToFile(String fileName, String note) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(note + "\n");
            System.out.println("Note added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Display notes
    public static void displayNotesFromFile(String fileName) {
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            System.out.println("\nNotes in File:");
            int count = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(count + ". " + line);
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No notes found. Add some notes first.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Edit a note
    public static void editNoteInFile(String fileName, Scanner sc) {
        List<String> notes = readAllNotes(fileName);
        if (notes.isEmpty()) {
            System.out.println("No notes to edit.");
            return;
        }
        displayNotes(notes);
        System.out.print("Enter note number to edit: ");
        int index = sc.nextInt();
        sc.nextLine(); // consume newline
        if (index < 1 || index > notes.size()) {
            System.out.println("Invalid note number.");
            return;
        }
        System.out.print("Enter new note text: ");
        String newNote = sc.nextLine();
        notes.set(index - 1, newNote);
        writeAllNotes(fileName, notes);
        System.out.println("Note updated successfully!");
    }

    // Delete a note
    public static void deleteNoteFromFile(String fileName, Scanner sc) {
        List<String> notes = readAllNotes(fileName);
        if (notes.isEmpty()) {
            System.out.println("No notes to delete.");
            return;
        }
        displayNotes(notes);
        System.out.print("Enter note number to delete: ");
        int index = sc.nextInt();
        sc.nextLine();
        if (index < 1 || index > notes.size()) {
            System.out.println("Invalid note number.");
            return;
        }
        notes.remove(index - 1);
        writeAllNotes(fileName, notes);
        System.out.println("Note deleted successfully!");
    }

    // Search notes
    public static void searchNoteInFile(String fileName, Scanner sc) {
        List<String> notes = readAllNotes(fileName);
        if (notes.isEmpty()) {
            System.out.println("No notes to search.");
            return;
        }
        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;
        System.out.println("Search Results:");
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).toLowerCase().contains(keyword)) {
                System.out.println((i + 1) + ". " + notes.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching notes found.");
        }
    }

    // Read all notes into a list
    public static List<String> readAllNotes(String fileName) {
        List<String> notes = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                notes.add(line);
            }
        } catch (FileNotFoundException e) {
            // File may not exist yet
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return notes;
    }

    // Write all notes from a list to file
    public static void writeAllNotes(String fileName, List<String> notes) {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (String note : notes) {
                fw.write(note + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Display notes from a list
    public static void displayNotes(List<String> notes) {
        System.out.println("\nNotes:");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i));
        }
    }
}