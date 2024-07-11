package core;

import java.util.Scanner;

public class validationCheck {
    public static int getValidIntegerInput(Scanner scanner, String prompt, int minValidValue, int maxValidValue) {
        int input;
        while (true) {
            try {
                System.out.println(prompt);
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= minValidValue && input <= maxValidValue) {
                    break; // Input is within the valid range
                } else {
                    System.out.println("Invalid integer input, please select an integer from " + minValidValue + " to " + maxValidValue + " :");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter an integer:");
            }
        }
        return input;
    }

    protected static String getValidInput(Scanner scanner, String prompt, String validOption1, String validOption2) {
        System.out.println(prompt);
        String input;
        while (true) {
            input = scanner.nextLine().trim().toLowerCase();
            try {
                // Attempt to parse input as integer
                int numericInput = Integer.parseInt(input);
                if (numericInput == 1 || numericInput == 2) {
                    // If input is numeric and matches one of the valid options
                    break;
                } else {
                    System.out.println("invalid input, please enter '" + validOption1 + "' or '" + validOption2 + "':");
                }
            } catch (NumberFormatException e) {
                // Input is not a valid integer
                if (input.equals(validOption1) || input.equals(validOption2)) {
                    // If input matches one of the valid string options
                    break;
                } else {
                    System.out.println("invalid input, please enter '" + validOption1 + "' or '" + validOption2 + "':");
                }
            }
        }
        return input;
    }


}
