package com.adaptionsoft.games.aitrivia;

import java.util.LinkedList;

public class QuestionDeck {
    private final LinkedList<String> popQuestions;
    private final LinkedList<String> scienceQuestions;
    private final LinkedList<String> sportsQuestions;
    private final LinkedList<String> rockQuestions;

    public QuestionDeck() {
        popQuestions = new LinkedList<>();
        scienceQuestions = new LinkedList<>();
        sportsQuestions = new LinkedList<>();
        rockQuestions = new LinkedList<>();
        initializeQuestions();
    }

    private void initializeQuestions() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public String getQuestionForCategory(Category category) {
        switch (category) {
            case POP:
                return popQuestions.removeFirst();
            case SCIENCE:
                return scienceQuestions.removeFirst();
            case SPORTS:
                return sportsQuestions.removeFirst();
            case ROCK:
                return rockQuestions.removeFirst();
            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }
}
