package org.example;

public class DamageAttack extends Attack{
    private final int power;

    public DamageAttack(String name, int baseDamage, int accuracy, Type type, int power) {
        super(name, baseDamage, accuracy, type);
        this.power = power;
    }
}
