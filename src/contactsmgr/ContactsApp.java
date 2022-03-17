package contactsmgr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContactsApp {
    private static final String directory = "data";
    private static final String filename = "contacts.txt";
    private static final ArrayList<Contact> contactArray = new ArrayList<>();
    private static final String starsAndSpaces45 = "* * * * * * * * * * * * * * * * * * * * * * * * * * *";

    public static void main(String[] args) {
        try {
            checkDataFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        readTxtFileData();
        buildMenu();
    }

    public static void checkDataFile() throws IOException {
//        Checks if directory and txt file exists if not it creates new directory and or data file.
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }
    }

    public static void readTxtFileData() {
//        Attempt to read file from contacts.txt and plug it into an arrayList
        try {
            Path contactPath = Paths.get("data", "contacts.txt");
            List<String> contactList = Files.readAllLines(contactPath);
            //Take the resulting string, delimited by a |, and split it
            //into name & phone fields.
            for (String c : contactList) {
                String[] cParts = c.split(", ");
                Contact loopContact = new Contact(cParts[0], cParts[1]);
                contactArray.add(loopContact);
            }
//            System.out.println(contactArray);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void buildMenu() {
//        Display options to user
        System.out.println(starsAndSpaces45);
        System.out.println("*  SUPER-HIGH-TECH CONTACT MANAGEMENT APPLICATION!  *");
        System.out.printf("*  1. View contacts.%32s*\n", " ");
        System.out.printf("*  2. Add a new contact.%28s*\n", " ");
        System.out.printf("*  3. Search a contact by name.%21s*\n", " ");
        System.out.printf("*  4. Edit a contact's details.%21s*\n", " ");
        System.out.printf("*  5. Delete an existing contact.%19s*\n", " ");
        System.out.printf("*  6. Exit program.%33s*\n", " ");
        System.out.println(starsAndSpaces45);
        System.out.println("Enter an option (1 - 6):");
//      Call to allow user to enter menu choice
        getMenuChoice();
    }

    public static void getMenuChoice() {
//        Gets user input for navigating menu choices and calls the method chosen
        Input input = new Input();
        int menuChoice = input.getInt(1, 6);
        switch (menuChoice) {
            case 1:
                viewContactsPlus();
            case 2:
                addContact();
            case 3:
                searchContacts();
            case 4:
                editContactMenu();
            case 5:
                deleteContact();
            case 6:
                exitProgram();
        }
    }

    public static void viewContactsPlus() {
//        Display contacts followed by option to return to main menu or exit program.
        viewContacts();
        buildMiniMenu();
    }

    public static void viewContacts() {
//        Displays all contacts in arrayList.
        System.out.println("Here are the contacts");
        System.out.printf("%7s Name %-6s | Phone number |\n------------------------------------\n", "", "");
        int count = 0;
        for (Contact contact : contactArray) {
            count += 1;
            System.out.printf("%d - %-15s | %-12s |\n", count, contact.getContactName(), contact.getContactPhone());
//            System.out.println(count + " - " + contact.getContactName() + " | " + contact.getContactPhone());
        }
        System.out.println();
    }

    public static void buildMiniMenu() {
//        Menu displayed after user views all contacts
        System.out.println(starsAndSpaces45);
        System.out.printf("*  1. Return to main menu.%26s*\n", " ");
        System.out.printf("*  2. Exit program.%33s*\n", " ");
        System.out.println(starsAndSpaces45);
        Input input = new Input();
        int menuChoice = input.getInt(1, 2);
        switch (menuChoice) {
            case 1:
                buildMenu();
            case 2:
                exitProgram();
        }
    }

    public static void addContact() {
//        Gets user input for contact name and number, formats and saves to arrayList.
        Input input = new Input();
        System.out.println("Enter the contact's first and last name:");
        String contactName = input.getString();
        System.out.println("Enter the contact's telephone number:");
        String contactPhone = input.getString();
        contactPhone = formatNumbers(contactPhone);

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

    public static String formatNumbers(String numberToFormat) {
        String areaCode;
        String prefix;
        String postfix;
        String result = null;
        if (!numberToFormat.contains("-")) {
            if (numberToFormat.length() == 7) {
                prefix = numberToFormat.substring(0, 3);
                postfix = numberToFormat.substring(3);
                result = prefix + "-" + postfix;
            } else if (numberToFormat.length() == 10) {
                areaCode = numberToFormat.substring(0, 3);
                prefix = numberToFormat.substring(3, 6);
                postfix = numberToFormat.substring(6);
                result = areaCode + "-" + prefix + "-" + postfix;
            }
        }
        return result;
    }

    public static void searchContacts() {
//        Gets user input to search in list of contacts and returns matching results
        System.out.println("Search contact name:");
        Input input = new Input();
        String userSearch = input.getString().toLowerCase();
        ArrayList<String> tempArr = new ArrayList<>();
        for (Contact contact : contactArray) {
            if (contact.toString().toLowerCase().contains(userSearch)) {
                tempArr.add(contact.toString());
            }
        }
        if (tempArr.size() == 0) {
            System.out.println("No matches were found");
        } else {
            System.out.println(tempArr);
        }
        System.out.println("Would you like to do another search? (y/n)");
        if (input.yesNo()) {
            searchContacts();
        } else {
            buildMenu();
        }
    }

    private static void editContactMenu() {
        System.out.println("1 - View all contacts\n2 - Search a contact");
        Input input = new Input();
        int userChoice = input.getInt(1, 2);
        if (userChoice == 1) {
            viewContacts();
            editContactsListAll();
        } else if (userChoice == 2) {
            searchContacts();
        }
    }

    private static void editContactsListAll() {
        viewContacts();
        System.out.println("Select a contact to edit");
        Input input = new Input();
        int arrSize = contactArray.size();
        int userInput = input.getInt(1, arrSize);
        Contact thisContact = contactArray.get(userInput - 1);
        System.out.printf("Is this the contact you want to edit? (y/n) %s %s\n", thisContact.getContactName(), thisContact.getContactPhone());
//        Add yes no
        System.out.println("Name: (Enter correct name or press enter/return if name is correct.)");
        String newName = input.getString();
        if (!newName.equalsIgnoreCase("")) {
            thisContact.setContactName(newName);
            System.out.println("Name changed!");
        }
        System.out.println("Phone number: (Enter correct phone number or press enter/return if number is correct");
        String newPhoneNumber = input.getString();
        if (!newPhoneNumber.equalsIgnoreCase("")) {
            thisContact.setContactPhone(formatNumbers(newPhoneNumber));
            System.out.println("Number changed!");
        }
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

    public static void exitProgram() {
//        Saves arrayList contacts to contacts.txt file before exiting program.
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
