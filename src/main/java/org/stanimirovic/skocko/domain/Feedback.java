package org.stanimirovic.skocko.domain;

public record Feedback(int exact, int present) {
    public boolean isWin() {
        return exact == 4;
    }
}
