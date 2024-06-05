package com.adaptionsoft.games.nicetrivia;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Trivia game logic.
 */
public class Game {
    private List<Player> players = new ArrayList<>();
    private QuestionDeck popDeck = new QuestionDeck();
    private QuestionDeck scienceDeck = new QuestionDeck();
    private QuestionDeck sportsDeck = new QuestionDeck();
    private QuestionDeck rockDeck = new QuestionDeck();

    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popDeck.addQuestion(new Question("Pop Question " + i));
            scienceDeck.addQuestion(new Question("Science Question " + i));
            sportsDeck.addQuestion(new Question("Sports Question " + i));
            rockDeck.addQuestion(new Question(createRockQuestion(i)));
        }
    }

    private String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return players.size() >= 2;
    }

    public boolean addPlayer(String playerName) {
        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                movePlayer(currentPlayer, roll);
            } else {
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            movePlayer(currentPlayer, roll);
        }
    }

    private void movePlayer(Player player, int roll) {
        player.move(roll);
        System.out.println(player.getName() + "'s new location is " + player.getPlace());
        System.out.println("The category is " + currentCategory(player));
        askQuestion();
    }

    private void askQuestion() {
        switch (currentCategory(players.get(currentPlayerIndex))) {
            case "Pop":
                System.out.println(popDeck.getNextQuestion());
                break;
            case "Science":
                System.out.println(scienceDeck.getNextQuestion());
                break;
            case "Sports":
                System.out.println(sportsDeck.getNextQuestion());
                break;
            case "Rock":
                System.out.println(rockDeck.getNextQuestion());
                break;
        }
    }

    private String currentCategory(Player player) {
        int place = player.getPlace();
        if (place == 0 || place == 4 || place == 8) return "Pop";
        if (place == 1 || place == 5 || place == 9) return "Science";
        if (place == 2 || place == 6 || place == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                return handleCorrectAnswer(currentPlayer);
            } else {
                nextPlayer();
                return true;
            }
        } else {
            return handleCorrectAnswer(currentPlayer);
        }
    }

    private boolean handleCorrectAnswer(Player player) {
        System.out.println("Answer was correct!!!!");
        player.incrementPurse();
        System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

        boolean winner = didPlayerWin(player);
        nextPlayer();
        return winner;
    }

    public boolean wrongAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);

        nextPlayer();
        return true;
    }

    private boolean didPlayerWin(Player player) {
        return player.getPurse() != 6;
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
    }
}
