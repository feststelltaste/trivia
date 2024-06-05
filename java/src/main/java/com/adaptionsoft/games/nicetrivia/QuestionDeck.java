package com.adaptionsoft.games.nicetrivia;

import java.util.LinkedList;

public class QuestionDeck {
    private LinkedList<Question> questions = new LinkedList<>();

    public void addQuestion(Question question) {
        questions.addLast(question);
    }

    public Question getNextQuestion() {
        return questions.removeFirst();
    }

}
