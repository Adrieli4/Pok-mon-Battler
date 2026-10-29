package org.example;

public class Attack{
    private final String name;
    private final int baseDamage;
    private final int accuracy;
    private final Type type;

    public String getName() {
        return name;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public Type getType() {
        return type;
    }

    public Attack(String name, int baseDamage, int accuracy, Type type) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("You need to write a name.");
        }
        if (baseDamage < 1 || baseDamage > 250){
            throw new IllegalArgumentException("Base damage must be between 1-250.");
        }
        if (accuracy < 30 || accuracy > 100){
            throw new IllegalArgumentException("Accuracy must be between 30-100.");
        }
        this.name = name;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.type = type;
    }

    public void pokUseAtt(Pokemon attacker, Pokemon defender){
        System.out.println(attacker.getName() + " chooses: " + name);
    }
}