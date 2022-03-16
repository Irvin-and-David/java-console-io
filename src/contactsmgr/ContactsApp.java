package contactsmgr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ContactsApp {
    private static final String directory = "data";
    private static final String filename = "contacts.txt";
    private static List contactArray = new ArrayList<>();

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
        int menuChoice = input.getInt(1, 5);
        switch (menuChoice) {
            case 1:
                viewContacts();
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
                Path filepath = Paths.get(directory, filename);
                try {
                    Files.write(filepath, contactArray, StandardOpenOption.APPEND);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                System.exit(0);
        }
    }

    public static void viewContacts() {
        System.out.println("Here are the contacts");
        System.out.println("Name | Phone number\n-------------");
        int count = 0;
        for (Object c : contactArray) {
            Contact contact = (Contact) c;
            count += 1;
            System.out.println(count + "- " + contact.getContactName() + " | " + contact.getContactPhone());
        }
        buildMenu();
    }

    public static void addContact() {
        Input input = new Input();
        System.out.println("Enter the contact's first and last name:");
        String contactName = input.getString();
        System.out.println("Enter the contact's telephone number:");
        String contactPhone = input.getString();
        System.out.printf("Is this name correct? %s and phone %s (y/n)", contactName, contactPhone);
        if (input.yesNo()) {
            Contact contact = new Contact(contactName, contactPhone);
            contactArray.add(contact);
            System.out.println(contact.getContactName() + " " + contact.getContactPhone());
        } else {
            addContact();
        }
        System.out.println("Would you like to add another contact? (y/n)");
        if (input.yesNo()) {
            addContact();
        }
        buildMenu();
    }


    public static void checkDataFile() throws IOException {
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }
    }
}
