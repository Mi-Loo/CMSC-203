/*
 * Class: CMSC203 
 * Instructor: Prof. Grigoriy A. Grinberg
 * Description: A program that reads data from an input file, produces a randomized value 
 * contained in that input file, display that output on the console and reads it to an external output file.
 * Due: 06/16/2025
 * Platform/compiler: Exclipse
 * I pledge that I have completed the programming assignment 
* independently. I have not copied the code from a student or   * any source. I have not given my code to any student.
 * Print your Name here: Somtochukwu Igboeli(Milo).
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ESPGame {
    public static void main(String[] args) {
        // Constants
        final String FILE_NAME = "colors.txt";
        final String RESULTS_FILE = "EspGameResults.txt";
        final String MENU_OPTIONS = "a. Read and display 16 colors\nb. Read and display 10 colors\nc. Read and display 5 colors\nd. Exit\n";
        final int MAX_COLORS = 16;
        final int MAX_TRIES = 3;
        
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        // Game variables
        int totalCorrectGuesses = 0;
        int gameRounds = 0;
        
        // Display menu first
        while (true) {
            System.out.println("\nESP Game Menu");
            System.out.println(MENU_OPTIONS);
            System.out.print("Enter your choice (a-d): ");
            String choice = scanner.nextLine().toLowerCase();
            
            if (choice.equals("d")) {
                break;
            }
            
            // Determine how many colors to show
            int colorsToShow = 0;
            if (choice.equals("a")) {
                colorsToShow = 16;
            } else if (choice.equals("b")) {
                colorsToShow = 10;
            } else if (choice.equals("c")) {
                colorsToShow = 5;
            } else {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            
            // Read and display colors
            try {
                Scanner fileScanner = new Scanner(new File(FILE_NAME));
                System.out.println("\nAvailable colors:");
                for (int i = 0; i < colorsToShow && fileScanner.hasNextLine(); i++) {
                    System.out.println((i + 1) + ". " + fileScanner.nextLine());
                }
                fileScanner.close();
            } catch (IOException e) {
                System.out.println("Error reading colors file. Please ensure colors.txt exists in the same directory.");
                continue;
            }
            
            // Play the game
            int roundCorrectGuesses = 0;
            for (int i = 0; i < MAX_TRIES; i++) {
                // Computer selects a random color (1-16)
                int randomColorIndex = random.nextInt(MAX_COLORS) + 1;
                String selectedColor = "";
                
                // Read the selected color from file
                try {
                    Scanner fileScanner = new Scanner(new File(FILE_NAME));
                    for (int j = 0; j < randomColorIndex; j++) {
                        if (fileScanner.hasNextLine()) {
                            selectedColor = fileScanner.nextLine();
                        }
                    }
                    fileScanner.close();
                } catch (IOException e) {
                    System.out.println("Error reading colors file during game play.");
                    continue;
                }
                
                // Get user's guess
                System.out.print("\nAttempt " + (i + 1) + ": Enter your guess: ");
                String userGuess = scanner.nextLine().toLowerCase();
                
                // Check if guess is correct
                if (userGuess.equals(selectedColor.toLowerCase())) {
                    System.out.println("Correct!");
                    roundCorrectGuesses++;
                } else {
                    System.out.println("Incorrect. The correct color was: " + selectedColor);
                }
            }
            
            // Show results for this round
            totalCorrectGuesses += roundCorrectGuesses;
            gameRounds++;
            
            System.out.println("\nRound Results:");
            System.out.println("You guessed " + roundCorrectGuesses + " out of " + MAX_TRIES + " colors correctly.");
            System.out.println("Total correct guesses so far: " + totalCorrectGuesses + " out of " + (gameRounds * MAX_TRIES));
        }
        
        // Game over - now collect user information
        System.out.println("\nGame Over");
        System.out.print("\nEnter your name: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter a description about yourself: ");
        String userDescription = scanner.nextLine();
        
        System.out.print("Enter due date (MM/DD/YY): ");
        String dueDate = scanner.nextLine();
        
        // Display final information
        System.out.println("\nFinal Results:");
        System.out.println("Total correct guesses: " + totalCorrectGuesses + " out of " + (gameRounds * MAX_TRIES));
        System.out.println("Due Date: " + dueDate);
        System.out.println("Username: " + username);
        System.out.println("User Description: " + userDescription);

        
        // Write results to file
        try {
            FileWriter writer = new FileWriter(RESULTS_FILE);
            writer.write("Game Over\n");
            writer.write("Total correct guesses: " + totalCorrectGuesses + " out of " + (gameRounds * MAX_TRIES) + "\n");
            writer.write("Due Date: " + dueDate + "\n");
            writer.write("Username: " + username + "\n");
            writer.write("User Description: " + userDescription + "\n");
            writer.close();
            System.out.println("\nResults saved to " + RESULTS_FILE);
        } catch (IOException e) {
            System.out.println("Error writing results to file: " + e.getMessage());
        }
        
        scanner.close();
    }
}