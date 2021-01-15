package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ThingsThatInvolveExternalFiles {

    /**
     * Checks to see if any profiles exist within the Rock Paper Scissors Profiles directory.
     *pre: User starts program or chooses to return to menu from another function..
     *post: Returns a true or false value, program continues accordingly.
     */
    public static boolean isDirectoryEmpty(File directory) {
        String[] files = directory.list();
        assert files != null;
        return files.length == 0;
    }

    /**
     * Allows the user to create a player profile with the internal structure on the next line:
     * (profile name)'(games played).(games won)/(games lost)~(rocks thrown)`(papers thrown):(scissors thrown)
     *pre: User does not have any player files or chooses to make a new one.
     *post: Returns a true or false value, program continues accordingly.
     */
    public static void CreatePlayerProfile(){
        System.out.println();
        System.out.print("Enter your name here to create a new profile: ");
        Screens.userChoice = Screens.scan.nextLine();

        //Error checking to make sure the user's input only contains letters and is under a certain length.
        ErrorChecking.NameErrorChecking("Your input must only include letters. Enter your name here to" +
                                                    " create a new profile: ");
        ErrorChecking.LengthErrorChecking(Screens.userChoice, 18, "Your input must not exceed 18" +
                                            " characters in length. Enter your name here to create a new profile: ");

        //Checks to make sure a profile with that name does not already exist.
        while (Files.exists(Path.of(RockPaperScissorsClient.location + "\\" + Screens.userChoice + ".txt"))) {

            System.out.print("A profile called '" + Screens.userChoice + "' already exists, enter another name to" +
                    " create a profile: ");
            Screens.userChoice = Screens.scan.nextLine();
        }

        //Creates a profile with specified name.
        Path path = Paths.get(RockPaperScissorsClient.location, Screens.userChoice + ".txt");

        try {
            Files.write(path, (Screens.userChoice + "'0.0/0~0`0:0").getBytes(), StandardOpenOption.CREATE);
            System.out.println("Your profile was successfully created.");
            //Makes newly created profile active.
            Screens.activeProfile = Screens.userChoice;
        } catch (IOException e) {
            //Exception handling.
            System.out.println("An IO error occurred and your profile could not be saved.");
        }
    }

    /**
     * Assigns a variable the data contained within a file.
     *pre: User starts a game.
     *post: Chosen variable is assigned data within chosen file.
     */
    public static void AssignFileData(String variableToAssignTo, String fileLocation) throws IOException {

        //Getting data from file.
        FileReader file = new FileReader(fileLocation);
        BufferedReader reader = new BufferedReader(file);

        String line = reader.readLine();

        StringBuilder variableToAssignToBuilder = new StringBuilder(variableToAssignTo);
        while (line != null) {
            variableToAssignToBuilder.append(line);
            line = reader.readLine();
        }
        //Assigning data to desired variable.
        variableToAssignTo = variableToAssignToBuilder.toString();
        Screens.profileDataFile = variableToAssignTo;
    }
}