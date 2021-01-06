package com.company;

import java.util.Random;

public class DefinitelyNotSentientAIOpponent {
    //Defining the choices the A.I. may choose from.
    static String[] aiOptions = new String[]{"a", "b", "c"};

    /**
     * If the user has not played before and does not have any information for the A.I. to guess against, the A.I. must
     * make a random choice of rock, paper, or scissors.
     * pre: User enters their choice of hand.
     * post: User either wins or loses the round.
     */
    public static void RandomAIChoice() {
        Screens.aiChoice = aiOptions[new Random().nextInt(aiOptions.length)];
    }

    /**
     * From the amount of times the user has chosen rock, paper, or scissors (imported from file earlier), the A.I.
     * chooses the most common hand and makes its move to beat that.
     * pre: User enters their choice of hand.
     * post: User either wins or loses the round.
     */
    public static void InformedAIChoice(int rockChoices, int paperChoices, int scissorsChoices) {
        if (rockChoices >= paperChoices && rockChoices >= scissorsChoices) {
            Screens.aiChoice = "b";
        } else if (paperChoices >= rockChoices && paperChoices >= scissorsChoices) {
            Screens.aiChoice = "c";
        } else {
            Screens.aiChoice = "a";
        }
    }
}
