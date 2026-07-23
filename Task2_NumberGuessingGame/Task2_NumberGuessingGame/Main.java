package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        boolean playAgain = true;
        int round = 1;

        System.out.println("===== Number Guessing Game =====");

        while (playAgain) {

            int secretNumber = random.nextInt(100) + 1;
            int maxAttempts = 7;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\nRound " + round);
            System.out.println("Guess a number between 1 and 100");

            while (attempts < maxAttempts) {

                System.out.print("Enter your guess: ");
                int guess = sc.nextInt();

                attempts++;

                if (guess > secretNumber) {
                    System.out.println("Too High!");
                } else if (guess < secretNumber) {
                    System.out.println("Too Low!");
                } else {
                    System.out.println("Correct!");
                    guessed = true;
                    break;
                }

                System.out.println("Attempts Left: "
                        + (maxAttempts - attempts));
            }

            if (guessed) {
                System.out.println("You guessed the number in "
                        + attempts + " attempts.");

                System.out.println("Round " + round
                        + " - Guessed in "
                        + attempts + " attempts");
            } else {
                System.out.println("You Lost!");
                System.out.println("The number was: "
                        + secretNumber);
            }

            System.out.print("Play Again? (yes/no): ");
            String choice = sc.next();

            if (choice.equalsIgnoreCase("no")) {
                playAgain = false;
            }

            round++;
        }

        System.out.println("Thanks for playing!");
        sc.close();
    }
}
