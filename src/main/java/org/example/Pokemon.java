package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pokemon{
    private final String name;
    private final Type type;
    private final int maxHp;
    private final int currentHp;
    private final List<Attack> attacks;

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public Pokemon(String name, Type type, int maxHp, int currentHp) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("You need to write a name.");
        }
        if (maxHp < 1 || maxHp > 255){
            throw new IllegalArgumentException("Max HP must be in between 1 and 255.");
        }
        if (currentHp < 0 || currentHp > maxHp){
            throw new IllegalArgumentException("Current HP can't be less than 0 or greater than max HP.");
        }
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attacks = new ArrayList<>();
    }
}