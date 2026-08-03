package util;

import exception.InvalidInputException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUtils {
    private final Scanner scanner = new Scanner(System.in);

    public void println(String msg) {
        System.out.println(msg);
    }

    public void success(String msg) {
        System.out.println("\u2713 " + msg);
    }

    public void error(String msg) {
        System.out.println("ERROR: " + msg);
    }

    public String readNonEmptyString(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String line = scanner.nextLine();
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidInputException("Input cannot be empty");
        }
        return line.trim();
    }

    public int readInt(String prompt, int min, int max) throws InvalidInputException {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            if (value < min || value > max) {
                throw new InvalidInputException("Value must be between " + min + " and " + max);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a valid integer");
        }
    }
}
