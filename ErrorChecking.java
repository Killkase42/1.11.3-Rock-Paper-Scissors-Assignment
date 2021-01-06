package com.company;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ErrorChecking {

    /**
     * Makes sure the entered name does not include numbers or other characters.
     *pre: User is asked to enter their name.
     *post: User is either allowed to proceed, or receives an error message.
     */
    public static void NameErrorChecking(String errorMessage){

        while (!Pattern.matches("[a-zA-Z ]+", Screens.userChoice)) {
            System.out.print(errorMessage);
            Screens.userChoice = Screens.scan.nextLine();
        }
    }

    /**
     * Makes sure the user's input is a certain length or less.
     *pre: User is asked to enter their name.
     *post: User is either allowed to proceed, or receives an error message.
     */
    public static void LengthErrorChecking(String stringToCheck, int maxLength, String errorMessage){

        while (stringToCheck.length() > maxLength) {
            System.out.print(errorMessage);
            Screens.userChoice = Screens.scan.nextLine();
        }
    }

    /**
     * Makes sure the entered value is one of the available options.
     *pre: User is asked to enter their name.
     *post: User is either allowed to proceed, or receives an error message.
     */
    public static void MultipleChoiceErrorChecking(String[] options, String errorMessage){
        boolean checkedSuccessfully = false;

        while (!checkedSuccessfully){
            for (String option : options) {
                if (Screens.userChoice.equalsIgnoreCase(option)) {
                    checkedSuccessfully = true;
                    break;
                }
            }
            if (!checkedSuccessfully){
                System.out.print(errorMessage);
                Screens.userChoice = Screens.scan.nextLine();
            }
        }
    }

    /**
     * Checks to see if the user's input is a number that is within range of an arraylist.
     *pre: User is to enter their numerical choice of profile.
     *post: User is either allowed to proceed, or receives an error message.
     */
    public static void ArrayListErrorChecking(ArrayList<String> arrayListToCheck, String errorMessage){
        boolean checkedSuccessfully = false;

        while (!checkedSuccessfully) {
            Screens.userChoice = Screens.scan.nextLine();
            try{
                for (int i = 0; i < arrayListToCheck.size(); i++) {
                    if (Integer.parseInt(Screens.userChoice) == i + 1) {
                        checkedSuccessfully = true;
                    }
                }
                if (!checkedSuccessfully) {
                    for (int i = 0; i < arrayListToCheck.size(); i++) {
                        System.out.print(i + 1 + ": ");
                        System.out.println(arrayListToCheck.get(i));
                    }
                    System.out.print(errorMessage);
                }
            } catch (Exception e){
                for (int i = 0; i < arrayListToCheck.size(); i++) {
                    System.out.print(i + 1 + ": ");
                    System.out.println(arrayListToCheck.get(i));
                }
                System.out.print(errorMessage);
            }
        }
    }
}