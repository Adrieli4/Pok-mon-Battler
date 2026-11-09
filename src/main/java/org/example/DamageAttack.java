package org.example;

public class DamageAttack extends Attack implements BattleAction{
    private final int power;
    private final boolean canCritical;

    public DamageAttack(String name, int baseDamage, int accuracy, Type type, int power, boolean canCritical) {
        super(name, baseDamage, accuracy, type);
        if (power < 1){
            throw new IllegalArgumentException("Power must be higher than 0.");
        }
        this.power = power;
        this.canCritical=false;
    }

    @Override
    public String label() {
        return getName();
    }

    @Override
    public void execute(Pokemon attacker, Pokemon attacked) {
        attacked.takeDamage(power);
        System.out.println(label() + " attack is worth " + power + " HP!");
    }
}
