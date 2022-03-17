package contactsmgr;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactsApp {
    private static final String directory = "data";
    private static final String filename = "contacts.txt";
    private static ArrayList<Contact> contactArray = new ArrayList<>();
    private static final String starsAndSpaces45 = "* * * * * * * * * * * * * * * * * * * * * * * * * * *";

    public static void main(String[] args) {
        readTxtFileData();
        buildMenu();
        try {
            checkDataFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readTxtFileData() {
        try {
            Path contactPath = Paths.get("data", "contacts.txt");
            List<String> contactList = Files.readAllLines(contactPath);
            //Take the resulting string, delimited by a |, and split it
            //into name & phone fields.
            for (String c : contactList) {
                String [] cParts = c.split(", ");
                Contact loopContact = new Contact(cParts[0],cParts[1]);
                contactArray.add(loopContact);
            }
//            System.out.println(contactArray);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void buildMenu() {
        System.out.println(starsAndSpaces45);
        System.out.println("*  SUPER-HIGH-TECH CONTACT MANAGEMENT APPLICATION!  *");
        System.out.printf("*  1. View contacts.%32s*\n"," ");
        System.out.printf("*  2. Add a new contact.%28s*\n"," ");
        System.out.printf("*  3. Search a contact by name.%21s*\n"," ");
        System.out.printf("*  4. Delete an existing contact.%19s*\n"," ");
        System.out.printf("*  5. Exit program.%33s*\n"," ");
        System.out.println(starsAndSpaces45);
        System.out.println("Enter an option (1, 2, 3, 4, 5):");

        getMenuChoice();
    }

    public static void buildMiniMenu() {
        System.out.println(starsAndSpaces45);
        System.out.printf("*  1. Return to main menu.%26s*\n"," ");
        System.out.printf("*  2. Exit program.%33s*\n"," ");
        System.out.println(starsAndSpaces45);
        Input input = new Input();
        int menuChoice = input.getInt(1, 2);
        switch (menuChoice) {
            case 1:
                buildMenu();
                break;
            case 2:
                exitProgram();
        }
    }
    public static void getMenuChoice() {
        Input input = new Input();
        int menuChoice = input.getInt(1, 5);
        switch (menuChoice) {
            case 1:
                viewContactsPlus();
                break;
            case 2:
                addContact();
                break;
            case 3:
                searchContacts();
                break;
            case 4:
                deleteContact();
                break;
            case 5:
                exitProgram();
        }
    }

    public static void viewContactsPlus() {
        viewContacts();
        buildMiniMenu();
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

    public static void deleteContact() {
        // Display list of contacts
        viewContacts();

        // Ask user to choose which contact to delete
        Input input = new Input();
        System.out.println("Enter the number of the contact you wish to delete:");
        int userChoice = input.getInt(1, contactArray.size());
        int deleteThis = userChoice - 1;

        // Use ArrayList.remove[user's choice minus 1]
        // Confirm the user's intention to delete the record
        Contact c = contactArray.get(deleteThis);
        System.out.printf("Are you sure you want to delete %s? (y/n)", c);
        boolean yesOrNo = input.yesNo();
        if (yesOrNo) {
            contactArray.remove(deleteThis);
            buildMenu();
        } else {
            deleteContact();
        }
    }

    public static void searchContacts() {
        System.out.println("Search contact name:");
        Input input = new Input();
        String userSearch = input.getString().toLowerCase();
        ArrayList<String> tempArr = new ArrayList<>();
        for (Contact contact : contactArray) {
            if (contact.toString().toLowerCase().contains(userSearch)) {
                tempArr.add(contact.toString());
            }
        }
        System.out.println(tempArr);
        System.out.println("Would you like to do another search? (y/n)");
        if (input.yesNo()) {
            searchContacts();
        } else {
            buildMenu();
        }
    }

    public static void viewContacts() {
        System.out.println("Here are the contacts");
        System.out.println("Name | Phone number\n-------------");
        int count = 0;
        for (Contact contact : contactArray) {
            count += 1;
            System.out.println(count + " - " + contact.getContactName() + " | " + contact.getContactPhone());
        }
        System.out.println();
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

    public static void exitProgram() {
        System.out.println("OK, bye-bye!");
        Path filepath = Paths.get(directory, filename);
        List<String> contactStringArray = new ArrayList<>();
        try {
            for (Contact c : contactArray) {
                contactStringArray.add(c.toString());
            }
            Files.write(filepath, contactStringArray);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.exit(0);
    }
}
