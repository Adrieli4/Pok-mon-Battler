package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pokemon{
    private String name;
    private Type type;
    private int maxHp;
    private int currentHp;
    private List<Attack> attacks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()){
            System.out.println("You need to write a name");
        }
        else {
            this.name = name;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        if (maxHp < 1){
            maxHp = 1;
        }
        else if (maxHp > 255){
            maxHp = 255;
        }
        else {
            this.maxHp = maxHp;
        }
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        if (currentHp < 0){
            currentHp = 0;
        }
        else if (currentHp > maxHp){
            currentHp = maxHp;
        }
        else{
            this.currentHp = currentHp;
        }
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public Pokemon(String name, Type type, int maxHp, int currentHp) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attacks = new ArrayList<>();
    }
}