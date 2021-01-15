/*
 *Nicholas Vourlas
 *December 17, 2020
 * ICS4U-01
 * Mr. Hofstatter
 * Rock Paper Scissors Assignment
 * A game of rock paper scissors (that was coded using multiple files) that allows multiple player profiles to be
 * stored on the computer as .txt files and that incorporates a simple A.I. opponent.
 */

package com.company;

import java.io.IOException;

public class RockPaperScissorsClient {

    static String location = "C:\\Rock Paper Scissors Profiles"; // This is where all files will be saved.
    static Boolean running = true;

    /**
     * Client code.
     *pre: none.
     *post: User is directed to the main menu method.
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Hello and welcome to Rock Paper Scissors!");
        Screens.MainMenu();
    }
}