package org.example;

public interface BattleAction {
    String label();
    Type typeEffectivity();
    void execute(Pokemon attacker, Pokemon attacked);
}
