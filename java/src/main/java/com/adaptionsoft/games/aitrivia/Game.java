package com.adaptionsoft.games.aitrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;
    private final QuestionDeck questionDeck;
    private int currentPlayerIndex;
    
    public Game() {
        this.players = new ArrayList<>();
        this.questionDeck = new QuestionDeck();
        this.currentPlayerIndex = 0;
    }

    public boolean isPlayable() {
        return players.size() >= 2;
    }

    public boolean addPlayer(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        System.out.println(playerName + " was added.");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        Player currentPlayer = getCurrentPlayer();
        System.out.println(currentPlayer.getName() + " is the current player.");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            handlePenaltyBox(roll, currentPlayer);
        } else {
            movePlayer(roll, currentPlayer);
            askQuestion();
        }
    }

    private void handlePenaltyBox(int roll, Player player) {
        if (roll % 2 != 0) {
            player.setGettingOutOfPenaltyBox(true);
            System.out.println(player.getName() + " is getting out of the penalty box.");
            movePlayer(roll, player);
            askQuestion();
        } else {
            System.out.println(player.getName() + " is not getting out of the penalty box.");
            player.setGettingOutOfPenaltyBox(false);
        }
    }

    private void movePlayer(int roll, Player player) {
        player.movePosition(roll);
        System.out.println(player.getName() + "'s new location is " + player.getPosition());
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        Category category = currentCategory();
        String question = questionDeck.getQuestionForCategory(category);
        System.out.println(question);
    }

    private Category currentCategory() {
        Player currentPlayer = getCurrentPlayer();
        return Category.fromPosition(currentPlayer.getPosition());
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.isInPenaltyBox() && !currentPlayer.isGettingOutOfPenaltyBox()) {
            nextPlayer();
            return true;
        }

        System.out.println("Answer was correct!!!!");
        currentPlayer.incrementPurse();
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getPurse() + " Gold Coins.");

        boolean winner = currentPlayer.hasWon();
        nextPlayer();
        return winner;
    }

    public boolean wrongAnswer() {
        Player currentPlayer = getCurrentPlayer();
        System.out.println("Question was incorrectly answered.");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box.");
        currentPlayer.sendToPenaltyBox();
        nextPlayer();
        return true;
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
        }
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}
