package org.example;

import java.util.Random;

public interface BattleAction {
    String label();
    Type typeEffectivity();
    int accuracyDamage();
    void execute(Pokemon attacker, Pokemon attacked, Random random);
}