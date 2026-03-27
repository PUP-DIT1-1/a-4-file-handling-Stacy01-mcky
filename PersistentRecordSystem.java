import java.io.*;
import java.util.*;

public class PersistentRecordSystem {
    private static final String FILENAME = "records.txt";
    private static List<String[]> records = new ArrayList<>();

    
    // Load records from file
    
    public static void loadRecords() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            try {
                file.createNewFile(); // create empty file if not exists
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            records.clear();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                records.add(data);
            }
        } catch (IOException e) {
            System.out.println("Error loading records: " + e.getMessage());
        }
    }

    
    // Save records to file
    
    public static void saveRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (String[] record : records) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    
    // Display records
    
    public static void displayRecords() {
        if (records.isEmpty()) {
            System.out.println("No records found.");
        } else {
            System.out.println("\n--- Current Records ---");
            for (int i = 0; i < records.size(); i++) {
                System.out.println(i + ": Name=" + records.get(i)[0] +
                                   ", Grade=" + records.get(i)[1] +
                                   ", Subject=" + records.get(i)[2]);
            }
            System.out.println("-----------------------");
        }
    }

   
    // Add a new record
    
    public static void addRecord(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter grade: ");
        String grade = sc.nextLine();
        System.out.print("Enter subject: ");
        String subject = sc.nextLine();

        records.add(new String[]{name, grade, subject});
        saveRecords();
        System.out.println(" Record added successfully!");
    }

    
    // Update a record
    
    public static void updateRecord(Scanner sc) {
        displayRecords();
        System.out.print("Enter record index to update: ");
        try {
            int index = Integer.parseInt(sc.nextLine());
            if (index >= 0 && index < records.size()) {
                System.out.print("Enter new name: ");
                String name = sc.nextLine();
                System.out.print("Enter new grade: ");
                String grade = sc.nextLine();
                System.out.print("Enter new subject: ");
                String subject = sc.nextLine();

                records.set(index, new String[]{name, grade, subject});
                saveRecords();
                System.out.println("Record updated successfully!");
            } else {
                System.out.println("Invalid index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    
    // Delete a record
    
    public static void deleteRecord(Scanner sc) {
        displayRecords();
        System.out.print("Enter record index to delete: ");
        try {
            int index = Integer.parseInt(sc.nextLine());
            if (index >= 0 && index < records.size()) {
                records.remove(index);
                saveRecords();
                System.out.println("Record deleted successfully!");
            } else {
                System.out.println("Invalid index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // Main Program Loop
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadRecords();
        System.out.println(" Persistent Record System (Grade & Subject)");
        displayRecords();

        while (true) {
            System.out.println("\nOptions: [1] Add [2] Update [3] Delete [4] Display [5] Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addRecord(sc);
                    break;
                case "2":
                    updateRecord(sc);
                    break;
                case "3":
                    deleteRecord(sc);
                    break;
                case "4":
                    displayRecords();
                    break;
                case "5":
                    System.out.println(" Exiting program. Data saved permanently.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
