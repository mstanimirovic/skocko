package org.stanimirovic.skocko.domain;

import java.util.*;

public final class SkockoRules {

    private SkockoRules() {}

    public static Feedback evaluate(Guess guess, Guess secret) {
        List<Symbol> g = new ArrayList<>(guess.symbols());
        List<Symbol> s = new ArrayList<>(secret.symbols());

        int exact = 0;
        for (int i = 0; i < 4; i++) {
            if (g.get(i) == s.get(i)) {
                exact++;
                g.set(i, null);
                s.set(i, null);
            }
        }

        int present = 0;
        for (Symbol symbol : g) {
            if (symbol != null && s.contains(symbol)) {
                present++;
                s.set(s.indexOf(symbol), null);
            }
        }

        return new Feedback(exact, present);
    }
}
