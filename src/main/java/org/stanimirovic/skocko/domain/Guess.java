package org.stanimirovic.skocko.domain;

import java.util.List;

public final class Guess {

    private final List<Symbol> symbols;

    public Guess(List<Symbol> symbols) {
        if (symbols.size() != 4) {
            throw new IllegalArgumentException(
                "Škocko guess mora imati tačno 4 simbola"
            );
        }
        this.symbols = List.copyOf(symbols);
    }

    public List<Symbol> symbols() {
        return symbols;
    }
}
