package contactsmgr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsApp {
    private static final String directory = "data";
    private static final String filename = "contacts.txt";
    private static final ArrayList<Contact> contactArray = new ArrayList<>();
    private static final String starsAndSpaces45 = "* * * * * * * * * * * * * * * * * * * * * * * * * * *";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String redStarGreen = ANSI_RED + "*  " + ANSI_GREEN;
    public static final String redStarNewLine = ANSI_RED + "*\n";

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
        System.out.println(ANSI_RED + starsAndSpaces45);
        System.out.println(ANSI_RED + "*  " + ANSI_RESET + "SUPER-HIGH-TECH CONTACT MANAGEMENT APPLICATION!" + ANSI_RED + "  *");
        System.out.printf(redStarGreen + "1. View contacts.%32s" + redStarNewLine, " ");
        System.out.printf(redStarGreen + "2. Add a new contact.%28s" + redStarNewLine, " ");
        System.out.printf(redStarGreen+ "3. Search a contact by name.%21s" + redStarNewLine, " ");
        System.out.printf(redStarGreen + "4. Edit a contact's details.%21s" + redStarNewLine, " ");
        System.out.printf(redStarGreen + "5. Delete an existing contact.%19s" + redStarNewLine, " ");
        System.out.printf(redStarGreen + "6. Exit program.%33s" + redStarNewLine, " ");
        System.out.println(ANSI_RED + starsAndSpaces45 + ANSI_RESET);
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
                break;
            case 2:
                addContact();
                break;
            case 3:
                searchContacts();
                break;
            case 4:
                editContactMenu();
                break;
            case 5:
                deleteContact();
                break;
            case 6:
                exitProgram();
        }
    }

    public static void sortContacts() {
//        ContactComparator comparator = new ContactComparator();
//        contactArray.sort(comparator);
        Collections.sort(contactArray);
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
        sortContacts();
        for (Contact contact : contactArray) {
            count += 1;
            System.out.printf("%d - %-15s | %-12s |\n", count, contact.getContactName(), contact.getContactPhone());
//            System.out.println(count + " - " + contact.getContactName() + " | " + contact.getContactPhone());
        }
        System.out.println();
    }

    public static void buildMiniMenu() {
//        Menu displayed after user views all contacts
        System.out.println(ANSI_RED + "*  SUPER-HIGH-TECH CONTACT MANAGEMENT APPLICATION!  *");
        System.out.printf(ANSI_GREEN + "*  1. Return to main menu.%26s*\n", " ");
        System.out.printf(ANSI_GREEN + "*  2. Exit program.%33s*\n", " ");
        System.out.println(ANSI_RED + "*  SUPER-HIGH-TECH CONTACT MANAGEMENT APPLICATION!  *" + ANSI_RESET);
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
        for (Contact c : contactArray) {
            if (c.getContactName().equalsIgnoreCase(contactName)) {
                System.out.println("There is already a contact with that name. Is this a different contact? (y/n)");
                boolean duplicateOrNah = input.yesNo();
                if (!duplicateOrNah) {
                    addContact();
                }
            }
        }
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
        } else {
             result = numberToFormat;
        }
        return result;
    }

    public static void searchContacts() {
        //  Gets user input to search in list of contacts and returns matching results
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

    public static void searchForEditing() {
    //  Gets user input to search in list of contacts and returns matching results
        System.out.println("Search contact name:");
        Input input = new Input();
        int count = 0;
        String userSearch = input.getString().toLowerCase();
        ArrayList<Contact> tempArr = new ArrayList<>();
        for (Contact contact : contactArray) {
            if (contact.toString().toLowerCase().contains(userSearch)) {
                tempArr.add(contact);
            }
        }
        if (tempArr.size() == 0) {
            System.out.println("No matches were found.");
            searchForEditing();
        } else {
            for (Contact c : tempArr) {
                count++;
                System.out.println(count + " - " + c.getContactName() + " | " + c.getContactPhone());
            }
            System.out.println("Enter the number (not phone number) of the contact you would like to edit:");
            int whichContact = input.getInt(1, tempArr.size());
            Contact thisContact = tempArr.get(whichContact - 1);
            System.out.printf("Is this the contact you want to edit? (y/n) %s %s\n", thisContact.getContactName(), thisContact.getContactPhone());
            //        Add yes no
            boolean yesNo = input.yesNo();
            if (yesNo) {
                System.out.println(thisContact);
                System.out.println("Name: (Enter correct name or press enter/return if name is correct.)");
                String newName = input.getString();
                if (!newName.equalsIgnoreCase("")) {
                    thisContact.setContactName(newName);
                    System.out.println("Name changed!");
                }
                System.out.println("Phone number: (Enter correct phone number or press enter/return if number is correct.)");
                String newPhoneNumber = input.getString();
                if (!newPhoneNumber.equalsIgnoreCase("")) {
                    thisContact.setContactPhone(formatNumbers(newPhoneNumber));
                    System.out.println("Number changed!");
                }
            } else {
                searchForEditing();
            }
            buildMenu();
        }
    }

    private static void editContactMenu() {
        System.out.println(ANSI_GREEN + "1 - View all contacts\n2 - Search a contact" + ANSI_RESET);
        Input input = new Input();
        int userChoice = input.getInt(1, 2);
        if (userChoice == 1) {
            viewContacts();
            editContactsListAll();
        } else if (userChoice == 2) {
            searchForEditing();
        }
    }

    private static void editContactsListAll() {
//        viewContacts();
        System.out.println("Select a contact to edit");
        Input input = new Input();
        int arrSize = contactArray.size();
        int userInput = input.getInt(1, arrSize);
        Contact thisContact = contactArray.get(userInput - 1);
        System.out.printf("Is this the contact you want to edit? (y/n) %s %s\n", thisContact.getContactName(), thisContact.getContactPhone());
//        Add yes no
        boolean yesNo = input.yesNo();
        if (yesNo) {
            System.out.println(thisContact);
            System.out.println("Name: (Enter correct name or press enter/return if name is correct.)");
            String newName = input.getString();
            if (!newName.equalsIgnoreCase("")) {
                thisContact.setContactName(newName);
                System.out.println("Name changed!");
            }
            System.out.println("Phone number: (Enter correct phone number or press enter/return if number is correct.)");
            String newPhoneNumber = input.getString();
            if (!newPhoneNumber.equalsIgnoreCase("")) {
                thisContact.setContactPhone(formatNumbers(newPhoneNumber));
                System.out.println("Number changed!");
            }
        } else {
            editContactsListAll();
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
            System.out.println("Contact deleted.");
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
            sortContacts();
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
