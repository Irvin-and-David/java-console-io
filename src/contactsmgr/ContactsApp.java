package contactsmgr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ContactsApp {
    private static ArrayList<Contact> contactArray = new ArrayList<>();

    public static void main(String[] args) {
        buildMenu();
        try {
            checkDataFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void buildMenu() {
        System.out.println("SUPER-HIGH-TECH CONTACT MANAGEMENT APPLICATION!");
        System.out.println("1. View contacts.\n2. Add a new contact.\n3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n5. Exit.\nEnter an option (1, 2, 3, 4, 5):");
        getMenuChoice();
    }

    public static void getMenuChoice() {
        Input input = new Input();
        int menuChoice = input.getInt(1,5);
        switch (menuChoice) {
            case 1:
//                viewContacts();
                break;
            case 2:
                addContact();
                break;
            case 3:
//                searchContacts();
                break;
            case 4:
//                deleteContact();
                break;
            case 5:
                System.out.println("OK, bye-bye!");
                System.exit(0);
        }
    }

    public static void viewContacts() {

    }

    public static void addContact() {
        Input input = new Input();
        System.out.println("Enter the contact's first and last name:");
        String contactName = input.getString();
        System.out.println("Enter the contact's telephone number:");
        String contactPhone = input.getString();
        Contact contact = new Contact(contactName,contactPhone);
        contactArray.add(contact);
        System.out.println(contactArray.toString());
    }

    public static void checkDataFile() throws IOException {
        String directory = "data";
        String filename = "contacts.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory,filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (! Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }
    }
}
