package org.example;

import java.util.Random;

public class DamageAttack extends Attack implements BattleAction{
    private final int power;

    public DamageAttack(String name, int baseDamage, int accuracy, Type type, int power) {
        super(name, baseDamage, accuracy, type);
        if (power < 1){
            throw new IllegalArgumentException("Power must be higher than 0.");
        }
        this.power = power;
    }

    @Override
    public String label() {
        return getName();
    }

    @Override
    public Type typeEffectivity() {
        return getType();
    }

    @Override
    public int accuracyDamage() {
        return getAccuracy();

    }

    @Override
    public void execute(Pokemon attacker, Pokemon attacked, Random random) {
        int probability=random.nextInt(1, 101);
        if (probability <= accuracyDamage()){
            if ((typeEffectivity().equals(Type.FIRE) && attacked.getType().equals(Type.GRASS)) ||
                    (typeEffectivity().equals(Type.WATER) && attacked.getType().equals(Type.FIRE))||
                    (typeEffectivity().equals(Type.GRASS) && attacked.getType().equals(Type.WATER)) ||
                    (typeEffectivity().equals(Type.ELECTRIC) && attacked.getType().equals(Type.WATER))){
                attacked.takeDamage(2*power);
                System.out.println("\n" + attacker.getName() + " uses " + label() + " against " + attacked.getName() + " and the damage it does is " + 2*power + "!");
            }
            else if ((typeEffectivity().equals(Type.FIRE) && attacked.getType().equals(Type.WATER)) ||
                    (typeEffectivity().equals(Type.WATER) && attacked.getType().equals(Type.GRASS))||
                    (typeEffectivity().equals(Type.GRASS) && attacked.getType().equals(Type.FIRE))){
                attacked.takeDamage(power/2);
                System.out.println("\n" + attacker.getName() + " uses " + label() + " against " + attacked.getName() + " and the damage it does is " + power/2 + "!");
            }
            else{
                attacked.takeDamage(power);
                System.out.println("\n" + attacker.getName() + " uses " + label() + " against " + attacked.getName() + " and the damage it does is " + power + "!");
            }
        }
        else {
            System.out.println("\n" + attacker.getName() + " missed!");
        }
    }
}