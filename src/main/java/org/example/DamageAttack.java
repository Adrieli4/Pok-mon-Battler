package org.example;

public class DamageAttack extends Attack implements BattleAction{
    private final int power;

    public int getPower() {
        return power;
    }

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
    public void execute(Pokemon attacker, Pokemon attacked) {
        attacked.takeDamage(power);
        System.out.println("\n" + attacker.getName() + " uses " + label() + " against " + attacked.getName() + " and the damage it does is " + power + "!");
    }
}
