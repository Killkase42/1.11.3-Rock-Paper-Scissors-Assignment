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