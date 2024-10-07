package com.adaptionsoft.games.aitrivia;

public class Player {
    private final String name;
    private int position;
    private int purse;
    private boolean inPenaltyBox;
    private boolean gettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.purse = 0;
        this.inPenaltyBox = false;
        this.gettingOutOfPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getPurse() {
        return purse;
    }

    public void incrementPurse() {
        this.purse++;
    }

    public boolean hasWon() {
        return purse >= 6;
    }

    public void movePosition(int roll) {
        this.position += roll;
        if (position > 11) {
            position -= 12;
        }
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void sendToPenaltyBox() {
        this.inPenaltyBox = true;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        this.gettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return gettingOutOfPenaltyBox;
    }
}
