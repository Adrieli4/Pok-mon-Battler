package org.example;

public class Attack{
    String name;
    int baseDamage;
    int accuracy;
    Type type;

    public Attack(String name, int baseDamage, int accuracy, Type type) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.type = type;
    }
}