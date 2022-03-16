package contactsmgr;

import java.util.Scanner;

public class Input {
    private Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String getString() {
        return scanner.nextLine();
    }

    public boolean yesNo() {
        String scInput = scanner.nextLine();
        if (scInput.equalsIgnoreCase("y") || scInput.equalsIgnoreCase("yes") || scInput.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    public int getInt(int min, int max) {
        int scIntInput = 0;
        try {
            scIntInput = Integer.valueOf(getString());
        } catch(Exception e) {
            System.out.println("Sorry, that isn't an integer.");
            getInt(min,max);
        }
        if (scIntInput < min || scIntInput > max) {
            System.out.println("Please follow the instructions and enter an integer.");
            getInt(min, max);
        } else {
            return scIntInput;
        }
        return 0;
    }

    public int getInt(String prompt) {
        try {
            System.out.println(prompt);
            return Integer.valueOf(getString());
        } catch (Exception e){
            System.out.println("Sorry, that isn't an integer.");
            return getInt(prompt);
        }
    }

    public double getDouble(double min, double max) {
        double scDblInput = 0;
        try {
            scDblInput = Double.valueOf(getString());
        } catch (Exception e) {
            System.out.println("Sorry, that isn't an number.");
            getDouble(min,max);
        }
        if (scDblInput < min || scDblInput > max) {
            System.out.println("Please follow the instructions and enter something numeric.");
            getDouble(min,max);
        } else {
            return scDblInput;
        }
        return 0;
    }

    public double getDouble(String prompt) {
        try {
            System.out.println(prompt);
            return Double.valueOf(getString());
        } catch (Exception e) {
            System.out.println("Sorry, we need a positive number, decimals are OK.");
            return getDouble(prompt);
        }

    }
    public int getBinary(String prompt) {
        try {
            System.out.println(prompt);
            return Integer.valueOf(getString(),2);
        } catch (Exception e) {
            System.out.println("Sorry, that doesn't work. Please try again, 0's and 1's only.");
            return getBinary(prompt);
        }
    }

    public int getHex(String prompt) {
        try {
            System.out.println(prompt);
            return Integer.valueOf(getString(),16);
        } catch (Exception e) {
            System.out.println("Sorry, that doesn't work. Enter all digits or A through F.");
            return getHex(prompt);
        }
    }
    public void closeMe() {
        scanner.close();
    }
}
