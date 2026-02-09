package org.stanimirovic.skocko.domain;

import java.util.*;

public class SkockoGame {

    private static final int MAX_ATTEMPTS = 6;

    private final Guess secret;
    private final List<Turn> turns = new ArrayList<>();
    private GamePhase phase = GamePhase.BUILDING_GUESS;

    public SkockoGame(Guess secret) {
        this.secret = secret;
    }

    public void submitGuess(Guess guess) {
        if (phase != GamePhase.BUILDING_GUESS) {
            throw new IllegalStateException("Igra je završena");
        }

        Feedback feedback = SkockoRules.evaluate(guess, secret);
        turns.add(new Turn(guess, feedback));

        if (feedback.isWin()) {
            phase = GamePhase.WON;
        } else if (turns.size() >= MAX_ATTEMPTS) {
            phase = GamePhase.LOST;
        }
    }

    public List<Turn> turns() {
        return List.copyOf(turns);
    }

    public GamePhase phase() {
        return phase;
    }

    public int remainingAttempts() {
        return MAX_ATTEMPTS - turns.size();
    }

    public Guess secret() {
        return secret;
    }
}
