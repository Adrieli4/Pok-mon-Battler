package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pokemon{
    String name;
    Type type;
    int maxHp;
    int currentHp;
    List<Attack> attacks;

    public Pokemon(String name, Type type, int maxHp, int currentHp) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attacks = new ArrayList<>();
    }
}