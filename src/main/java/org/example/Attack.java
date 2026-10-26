package org.example;

public class Attack{
    private String name;
    private int baseDamage;
    private int accuracy;
    private Type type;

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

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        if (baseDamage < 1){
            baseDamage = 1;
        }
        else if (baseDamage > 250){
            baseDamage = 250;
        }
        else {
            this.baseDamage = baseDamage;
        }
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        if (accuracy < 30){
            accuracy = 30;
        }
        else if (accuracy > 100){
            accuracy = 100;
        }
        else {
            this.accuracy = accuracy;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Attack(String name, int baseDamage, int accuracy, Type type) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.type = type;
    }
}