package com.adaptionsoft.games.nicetrivia;

import java.util.Random;

/**
 * This class runs the Nice Trivia game.
 */
public class NiceGameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		// Initialize a new game instance
		Game aGame = new Game();

		// Add players to the game
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");

		// Create a random number generator
		Random rand = new Random();

		// Play the game until a winner is determined
		do {
			// Simulate rolling the dice
			aGame.roll(rand.nextInt(5) + 1);

			// Randomly determine if the question was answered incorrectly
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);
	}
}
