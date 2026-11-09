package org.example;

public interface BattleAction {
    String label();
    void execute(Pokemon attacker, Pokemon attacked);
}
