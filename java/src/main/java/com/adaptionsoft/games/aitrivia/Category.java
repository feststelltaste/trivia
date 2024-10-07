package com.adaptionsoft.games.aitrivia;

public enum Category {
    POP, SCIENCE, SPORTS, ROCK;

    public static Category fromPosition(int position) {
        if (position % 4 == 0) return POP;
        if (position % 4 == 1) return SCIENCE;
        if (position % 4 == 2) return SPORTS;
        return ROCK;
    }
}
