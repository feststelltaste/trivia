package com.adaptionsoft.games.nicetrivia;

public class Player {
    private String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.place = 0;
        this.purse = 0;
        this.inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public void move(int roll) {
        place += roll;
        if (place > 11) place -= 12;
    }

    public int getPurse() {
        return purse;
    }

    public void incrementPurse() {
        purse++;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }
}
