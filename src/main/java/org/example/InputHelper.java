package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    public static Type readType(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            try {
                String input= scanner.nextLine().toUpperCase();
                return Type.valueOf(input);
            }
            catch (IllegalArgumentException e){
                System.out.println("That is not a valid type.");
            }
        }
    }

    public static int readInt(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            try {
                int value=scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Try again.");
                scanner.nextLine();
            }
        }
    }

    public static int readIntBetween(Scanner scanner, String prompt, int min, int max){
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.printf("The value must be in between %d and %d.%n", min, max);
        }

    }

    public static String readString(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            String input= scanner.nextLine();
            if (!input.isBlank()){
                return input;
            }
            System.out.println("Your input must not be blank");
        }
    }
}
