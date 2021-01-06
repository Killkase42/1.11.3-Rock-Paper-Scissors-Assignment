package com.company;

//Importing necessary classes.
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Screens {
    //Creating instance variables and arraylists.
    static Scanner scan = new Scanner(System.in);
    static String userChoice = "";
    static String activeProfile = "";
    static int profileNumber = 1;
    static String profileDataFile = "";
    static String aiChoice = "";

    static ArrayList<String> availableProfiles = new ArrayList<>();
    static ArrayList<String> profileData = new ArrayList<>();
    static ArrayList<String> orderedProfileData = new ArrayList<>();


    /**
     * Allows the user to exit the program; made a method just for convenience.
     *pre: Input of ! from a list of choices.
     *post: Program stops running.
     */
    public static void ExitProgramMethod() {
        if (userChoice.equals("!")){
            RockPaperScissorsClient.running = false;
        }
    }

    /**
     * Allows the user to start a new game, create a new profile, or view players' scores.
     *pre: User starts program or chooses to return to menu from another function.
     *post: Either the GameScreen or ScoreScreen function is run.
     */
    public static void MainMenu() throws IOException {
        //If the user has not already run the code, this statement creates a directory that player profiles will be
        //stored in.
        if (Files.notExists(Path.of(RockPaperScissorsClient.location))) {
            new File(RockPaperScissorsClient.location).mkdir();
            System.out.println("A new directory called 'Rock Paper Scissors Profiles' has been created in your C:\\ " +
                    "directory, all player profiles for this game will be stored there.");
        }

        //Checks if the user has any profiles made--they need at least one to play the game--and prompts them to make
        // one if they do not already have one. If they do have one, they are prompted to choose one to play as.
        if (ThingsThatInvolveExternalFiles.isDirectoryEmpty(new File("C:\\Rock Paper Scissors Profiles"))){
            System.out.print("You don't have any player profiles made (you need at least one to play). ");
            ThingsThatInvolveExternalFiles.CreatePlayerProfile();
        } else if (activeProfile.equalsIgnoreCase("")) {
            ChangeProfile();
        }

        //Allows the user to choose what they want to do from the main menu.
        while (RockPaperScissorsClient.running) {
            //Printing the user's options while the main menu is displayed.
            System.out.println();
            System.out.print("Enter 'a' to start a new game, 'b' to view player statistics, 'c' " +
                    "to change active profile (active profile: " + activeProfile + "), or 'd' to create a new" +
                    " one. (Enter '!' to exit.): ");
            userChoice = scan.nextLine();

            ErrorChecking.MultipleChoiceErrorChecking(new String[]{"a", "b", "c", "d", "!"},
                    "Invalid input. Enter 'a' to start a new game, 'b' to view player statistics, 'c' " +
                            "to change active profile (active profile: " + activeProfile + "), or 'd' to create a new" +
                            "one. (Enter '!' to exit.): ");

            if (userChoice.equalsIgnoreCase("a")){
                GameScreen();
            }
            else if (userChoice.equalsIgnoreCase("b")){
                ScoreScreen();
            }
            else if (userChoice.equalsIgnoreCase("c")){
                ChangeProfile();
            }
            else if (userChoice.equalsIgnoreCase("d")){
                ThingsThatInvolveExternalFiles.CreatePlayerProfile();
            }

            ExitProgramMethod();
        }
    }

    /**
     * Allows the user to start a new game and play against the A.I.
     *pre: User chooses to start a new game in the main menu.
     *post: User is returned to the main menu.
     */
    public static void GameScreen () throws IOException {
        //Creating method variables that will be involved in modifying files.
        String profileDataFileLocation = RockPaperScissorsClient.location + "\\" + activeProfile + ".txt";
        int activeGamesPlayed;
        int activeWins;
        int activeLosses;
        int activeRock ;
        int activePaper ;
        int activeScissors;
        int currentRound = 1;
        int playerScore = 0;
        int aiScore = 0;
        boolean updateProfile = true;

        //Resetting the value of user's data instance variable.
        profileDataFile = "";

        //This assigns the data in the active user profile file to a variable that will later be modified.
        ThingsThatInvolveExternalFiles.AssignFileData(profileDataFile, profileDataFileLocation);

        //These statements assign individual variables data based on what was found on the imported file.
        activeGamesPlayed = Integer.parseInt(profileDataFile.substring(profileDataFile.indexOf("'") + 1,
                profileDataFile.indexOf(".")));
        activeWins = Integer.parseInt(profileDataFile.substring(profileDataFile.indexOf(".") + 1,
                profileDataFile.indexOf("/")));
        activeLosses = Integer.parseInt(profileDataFile.substring(profileDataFile.indexOf("/") + 1,
                profileDataFile.indexOf("~")));
        activeRock = Integer.parseInt(profileDataFile.substring(profileDataFile.indexOf("~") + 1,
                profileDataFile.indexOf("`")));
        activePaper = Integer.parseInt(profileDataFile.substring(profileDataFile.indexOf("`") + 1,
                profileDataFile.indexOf(":")));
        activeScissors = Integer.parseInt(profileDataFile.substring(profileDataFile.indexOf(":") + 1));

        //Decides how many rounds will be played.
        System.out.println();
        System.out.println("You're going to be facing off against an extremely intelligent A.I. opponent...");
        System.out.print("Enter how many rounds you would like to play (maximum of 5): ");
        userChoice = scan.nextLine();

        ErrorChecking.MultipleChoiceErrorChecking(new String[]{"1", "2", "3", "4", "5"}, "Invalid input. " +
                                                    "Enter how many rounds you would like to play (maximum of 5): ");
        int totalRounds = Integer.parseInt(userChoice);

        //Game continues until final round.
        while (currentRound <= totalRounds || playerScore == aiScore) {
            //Print current round.
            System.out.println();
            System.out.print("---Round " + currentRound + " of " + totalRounds);
            if (playerScore == aiScore && currentRound > totalRounds) {
                System.out.print(" (tiebreaker round)");
            }
            System.out.println("---");

            //Asks user to enter their choice of hand.
            System.out.print("Enter 'a' to throw rock, 'b' to throw paper, or 'c' to throw scissors. (Enter '!' to" +
                    " return to main menu.): ");
            userChoice = scan.nextLine();

            //Checks if the user entered a valid choice of hand.
            ErrorChecking.MultipleChoiceErrorChecking(new String[]{"a", "b", "c", "!"}, "Invalid input. " +
                    "Enter 'a' to throw rock, 'b' to throw paper, or 'c' to throw scissors. (Enter '!' to return to" +
                    " main menu.): ");

            //Checks if the user wishes to exit to the main menu (does not save game data to their file).
            if (userChoice.equals("!")) {
                System.out.println("Game data has not been saved to profile.");
                updateProfile = false;
                userChoice = "";
                break;
            }

            //Displaying what the player chose.
            System.out.print("You threw ");
            if (userChoice.equalsIgnoreCase("a")) {
                System.out.println("rock...");
                activeRock++;
            } else if (userChoice.equalsIgnoreCase("b")) {
                System.out.println("paper...");
                activePaper++;
            } else if (userChoice.equalsIgnoreCase("c")) {
                System.out.println("scissors...");
                activeScissors++;
            }

            //Using the A.I. and displaying what it chose.
            System.out.print("The A.I. threw ");

            //If the user does not have any previous choice data (aside from the choice of hand just made, the A.I. will
            //randomly select a throw to avoid it winning every single time.
            if (activeRock == 1 && activePaper == 0 && activeScissors == 0 ||
                    activeRock == 0 && activePaper == 1 && activeScissors == 0 ||
                    activeRock == 0 && activePaper == 0 && activeScissors == 1) {
                DefinitelyNotSentientAIOpponent.RandomAIChoice();
            } else { //If the user does have previous choice data, the A.I. will used it to make an informed guess and
                //make a move to beat the most common throw.
                DefinitelyNotSentientAIOpponent.InformedAIChoice(activeRock, activePaper, activeScissors);
            }

            //Displaying what the A.I. chose.
            if (aiChoice.equalsIgnoreCase("a")) {
                System.out.println("rock...");
            } else if (aiChoice.equalsIgnoreCase("b")) {
                System.out.println("paper...");
            } else if (aiChoice.equalsIgnoreCase("c")) {
                System.out.println("scissors...");
            }

            //Deciding if the player or A.I. won the round.
            if (userChoice.equalsIgnoreCase("a") && aiChoice.equalsIgnoreCase("c") ||
                    userChoice.equalsIgnoreCase("b") && aiChoice.equalsIgnoreCase("a") ||
                    userChoice.equalsIgnoreCase("c") && aiChoice.equalsIgnoreCase("b")) {
                playerScore++;
                System.out.print("You won the round");
                if (currentRound < totalRounds || playerScore == aiScore) {
                    System.out.print("! ");
                }
            } else if (aiChoice.equalsIgnoreCase("a") && userChoice.equalsIgnoreCase("c") ||
                    aiChoice.equalsIgnoreCase("b") && userChoice.equalsIgnoreCase("a") ||
                    aiChoice.equalsIgnoreCase("c") && userChoice.equalsIgnoreCase("b")) {
                aiScore++;
                System.out.print("You lost the round");
                if (currentRound < totalRounds || playerScore == aiScore){
                    System.out.print(". ");
                }
            } else {
                System.out.print("This round has no winner");
                if (currentRound < totalRounds || playerScore == aiScore){
                    System.out.print(". ");
                }
            }
            //Displaying if the user won or lost the game.
            if ((currentRound >= totalRounds && playerScore > aiScore) || (playerScore != 0 && aiScore != 0 && totalRounds == 1)) {
                System.out.print("...and you won the game");
            } else if (currentRound >= totalRounds && playerScore < aiScore){
                System.out.print("...and you lost the game");
            }
            if (currentRound >= totalRounds && playerScore > aiScore) {
                System.out.print("! ");
            } else if ((currentRound >= totalRounds && playerScore < aiScore) || (currentRound == totalRounds && totalRounds == 1)){
                System.out.print(". ");
            }
            //Displaying the current score.
            System.out.print("The ");
            //Displaying "final" in "the score is..." line based on if the game is over.
            if (currentRound >= totalRounds && playerScore != aiScore){
                System.out.print("final ");
            }
            System.out.println("score is " + activeProfile + ": " + playerScore + ", A.I.: " + aiScore + ".");

            //Increases the current round number by one in preparation for the next while loop cycle.
            currentRound ++;
        }

        //Updating the active profile with the game's results.
        if (updateProfile){
            System.out.println();
            System.out.print("(Enter '!' to return to the main menu.): ");
            userChoice = scan.nextLine();
            ErrorChecking.MultipleChoiceErrorChecking(new String[] {"!"}, "Invalid input. (Enter '!' to " +
                    "return to the main menu.): ");
            userChoice = "";

            //Incrementing the active profile's statistics.
            activeGamesPlayed ++;
            if (playerScore > aiScore){
                activeWins ++;
            } else if (aiScore > playerScore){
                activeLosses ++;
            }

            //Getting the path of the active profile.
            Path profilePath = Paths.get(RockPaperScissorsClient.location, Screens.activeProfile + ".txt");

            try {
                Files.write(profilePath, (Screens.activeProfile + "'" + activeGamesPlayed + "." + activeWins
                        + "/" + activeLosses + "~" + activeRock + "`" + activePaper + ":" + activeScissors).getBytes());
                System.out.println("Game data has been saved to profile.");
            } catch (IOException e) {
                //Exception handling.
                System.out.println("An IO error occurred and game data could not be saved to your profile.");
            }
        }
    }

    /**
     * Displays the number of wins, losses, and games played of each profile (in order from most wins to least).
     *pre: User chooses to view player statistics.
     *post: User is returned to main menu.
     */
    public static void ScoreScreen () throws IOException {
        profileData.clear();
        orderedProfileData.clear();

        System.out.println();
        System.out.println("PLACE (GAMES WON):    PROFILE:            GAMES PLAYED:            GAMES WON:            GAMES LOST:");

        File dir = new File(RockPaperScissorsClient.location);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                Scanner fScn = new Scanner(new File(String.valueOf(child)));
                String iteration;

                //Intakes the data from a file.
                iteration = fScn.nextLine();
                fScn.close();

                //Putting the file data into an array list in a form that will allow is to be sorted based on wins.
                orderedProfileData.add(iteration.substring(iteration.indexOf(".") + 1, iteration.indexOf("/") + 1)
                        + iteration.substring(0, iteration.indexOf("'"))
                        + iteration.substring(iteration.indexOf("'"), iteration.indexOf(".")) + "."
                        + iteration.substring(iteration.indexOf("/") + 1 ));
            }
        }

        //Ordering the list containing profile data from most to least wins.
        Collections.sort(orderedProfileData);
        Collections.reverse(orderedProfileData);

        int profilePlacing = 1;
        //Printing the ordered array list in a readable form that lines up in columns.
        for (String orderedFileDatum : orderedProfileData) {

            System.out.print(profilePlacing + " ".repeat(22 - String.valueOf(profilePlacing).length()));
            profilePlacing ++;

            System.out.println(orderedFileDatum.substring(orderedFileDatum.indexOf("/") + 1,
                    orderedFileDatum.indexOf("'")) + " ".repeat(21 - (orderedFileDatum.indexOf("'")
                    - orderedFileDatum.indexOf("/"))) + orderedFileDatum.substring(orderedFileDatum.indexOf("'")
                    + 1, orderedFileDatum.indexOf(".")) + " ".repeat(26 - (orderedFileDatum.indexOf(".")
                    - orderedFileDatum.indexOf("'"))) + orderedFileDatum.substring(0,
                    orderedFileDatum.indexOf("/")) + " ".repeat(22 - (orderedFileDatum.indexOf("/")))
                    + orderedFileDatum.substring(orderedFileDatum.indexOf(".") + 1, orderedFileDatum.indexOf("~")));
        }
        System.out.print("(Enter '!' to return to main menu.): ");
        userChoice = scan.nextLine();
        ErrorChecking.MultipleChoiceErrorChecking(new String[]{"!"}, "Invalid input. (Enter '!' to return " +
                "to main menu.): ");
        MainMenu();
    }

    /**
     * Allows the user to change their active profile, thus changing which file's data is altered when a game is played.
     *pre: User starts program or chooses to change their profile from the main menu.
     *post: Main menu is displayed.
     */
    public static void ChangeProfile(){
        System.out.println();
        System.out.println("Here is a list of all player profiles on this computer.");

        //Resetting the value of profileNumber so it can be used in the next for loop.
        profileNumber = 1;

        //Resets the values contained within the array list so they are not duplicated by the next action.
        availableProfiles.clear();

        //Iterates through all files in directory and assigns them to a selectable number by putting them in an arraylist.
        File dir = new File(RockPaperScissorsClient.location);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String iteration = String.valueOf(child).replace(RockPaperScissorsClient.location + "\\", "");
                iteration = iteration.replace(".txt", "");
                System.out.println(profileNumber + ": " + iteration);
                availableProfiles.add(iteration);
                profileNumber ++;
            }
            //Resetting the value of profileNumber so it can be used in the next for loop.
            profileNumber = 1;
        }

        System.out.print("Enter a profile's corresponding number here to activate it (game data will be recorded to it): ");

        //Checks to make sure the user's input is within range of the arraylist.
        ErrorChecking.ArrayListErrorChecking(availableProfiles, "Invalid input. Enter a " +
                "profile's corresponding number here to activate it (game data will be recorded to it): ");

        //Assigns the active profile a name based on which one the user chose.
        for (String availableProfile : availableProfiles) {
            if (Integer.parseInt(userChoice) == profileNumber) {
                activeProfile = availableProfile;
                break;
            }
            profileNumber++;
        }
        System.out.println("Active profile has been changed.");
    }
}