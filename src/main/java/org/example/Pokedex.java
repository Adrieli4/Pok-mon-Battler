package org.example;

import java.util.*;
import java.util.Random;

import static org.example.FileHelper.*;
import static org.example.InputHelper.*;
import static org.example.PokedexCRUD.*;

public class Pokedex {
    private static ArrayList<Pokemon> pokedex = new ArrayList<>();
    private static final ArrayList<Pokemon> pokedexComputer = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        if (pokedex.isEmpty()){
            seedData();
        }

        if (pokedexComputer.isEmpty()){
            seedDataComputer();
        }

        while (true) {
            System.out.println("\nPokedex");
            System.out.println("1) Pokedex options  2) Save to file and exit  3) Edit Pokemon  4) Save data to file  5) Load data from file  6) Start battle");
            int choice = readIntBetween(scanner, "Your choice: ", 1, 6);

            if (choice == 1) {
                pokedexOptions(scanner);
            }

            if (choice == 2) {
                if (pokedex.isEmpty()){
                    System.out.println("\nThere are no Pokemon to save.");
                }
                else {
                    ArrayList<Pokemon> allPokemonToSave = new ArrayList<>(pokedex);

                    saveDataToCSV(allPokemonToSave);

                }
                System.out.println("\nExiting program...");
                break;
            }

            if (choice==3){
                if (pokedex.isEmpty()){
                    System.out.println("\nThe pokedex is empty.");
                }
                else{
                    editPokemon(scanner);
                }

            }

            if (choice==4){
                if (pokedex.isEmpty()){
                    System.out.println("\nThe pokedex is empty. There is nothing to save.");
                }
                else {
                    ArrayList<Pokemon> allPokemonToSave = new ArrayList<>(pokedex);

                    saveDataToCSV(allPokemonToSave);
                }

            }

            if (choice==5){
                pokedex.clear();
                List<Pokemon> loadedPokemon=loadDataFromCSV();
                pokedex= (ArrayList<Pokemon>) loadedPokemon;
                if (pokedex.isEmpty()){
                    System.out.println("\nNo data has been loaded. The pokedex is empty.");
                }
                else{
                    System.out.println("\nThe data has been loaded.");
                }
            }

            if (choice==6){
                System.out.println("\nYour Pokemon:");
                showPokedex(pokedex);
                System.out.println("To choose a Pokemon, enter the corresponding number:");
                pokedexNameChoice(pokedex);
                int choicePokemon=readIntBetween(scanner, "\nYour choice: ", 0, pokedex.size()-1);
                Pokemon player=pokedex.get(choicePokemon);
                Random random = new Random();
                int choiceComputer = random.nextInt(0, pokedexComputer.size());
                Pokemon enemy=pokedexComputer.get(choiceComputer);
                System.out.println("Your choice: " + player.getName() + ", " + player.getCurrentHp() + " HP");
                System.out.println("Computer choice: " + enemy.getName() + ", " + enemy.getCurrentHp() + " HP");

                ArrayList<Pokemon> fighterPokemon=new ArrayList<>();
                fighterPokemon.add(player);
                fighterPokemon.add(enemy);
                saveFightDataPokemonToCSV(fighterPokemon);

                while (player.getCurrentHp() > 0 && enemy.getCurrentHp() > 0){
                    System.out.println("\nAttacks:");
                    for (int i=0;i<player.getAttacks().size();i++){
                        System.out.println(i + ". " + player.getAttacks().get(i).getName());
                    }

                    int choicePokemonAtt=readIntBetween(scanner, "\nChoose attack: ", 0, player.getAttacks().size()-1);
                    Attack playerAttack=player.getAttacks().get(choicePokemonAtt);
                    System.out.println("\nYou have chosen " + player.getName() + " (" + playerAttack.getName() + ")");
                    DamageAttack damageAttackPlayer=new DamageAttack(playerAttack.getName(), playerAttack.getBaseDamage(), playerAttack.getAccuracy(), playerAttack.getType(), playerAttack.getBaseDamage());

                    int choiceComputerAtt = random.nextInt(0, enemy.getAttacks().size());
                    Attack enemyAttack=enemy.getAttacks().get(choiceComputerAtt);
                    System.out.println("\nThe computer has chosen " + enemy.getName() + " (" + enemyAttack.getName() + ")");
                    DamageAttack damageAttackEnemy=new DamageAttack(enemyAttack.getName(), enemyAttack.getBaseDamage(), enemyAttack.getAccuracy(), enemyAttack.getType(), enemyAttack.getBaseDamage());

                    if (player.getCurrentHp() < enemy.getCurrentHp()){
                        int probability=random.nextInt(1, 101);
                        if (probability <= playerAttack.getAccuracy()){
                            damageAttackPlayer.execute(player, enemy);
                        }
                        else{
                            System.out.println("\nYou missed!");
                        }
                        System.out.println("\nComputer HP: " + enemy.getCurrentHp());
                        if (enemy.isFainted()){
                            break;
                        }
                        int secondProb=random.nextInt(1, 101);
                        if (secondProb <= enemyAttack.getAccuracy()){
                            damageAttackEnemy.execute(enemy, player);
                        }
                        else {
                            System.out.println("\nYour opponent missed!");
                        }
                        System.out.println("\nYour HP: " + player.getCurrentHp());
                    }
                    else {
                        int probability= random.nextInt(1, 101);
                        if (probability <= enemyAttack.getAccuracy()){
                            damageAttackEnemy.execute(enemy, player);
                        }
                        else{
                            System.out.println("\nYour opponent missed!");
                        }
                        System.out.println("\nYour HP: " + player.getCurrentHp());
                        if (player.isFainted()){
                            break;
                        }
                        int secondProb=random.nextInt(1, 101);
                        if (secondProb <= playerAttack.getAccuracy()){
                            damageAttackPlayer.execute(player, enemy);
                        }
                        else {
                            System.out.println("\nYou missed!");
                        }
                        System.out.println("\nComputer HP: " + enemy.getCurrentHp());
                    }
                }

                if (player.isFainted()){
                    System.out.println("\nYou lost! " + enemy.getName() + "'s HP is " + enemy.getCurrentHp() + ".");
                }
                else{
                    System.out.println("\nYou won! " + player.getName() + "'s HP is " + player.getCurrentHp() + ".");
                }
                player.heal(player.getMaxHp());
                enemy.heal(enemy.getMaxHp());
            }

        }

        scanner.close();
    }

    public static void editPokemon(Scanner scanner){
        System.out.println("\nYour Pokemon:");
        showPokedex(pokedex);
        System.out.println("\nChoose something to edit:");
        System.out.println("1) Edit name  2) Edit type  3) Edit HP  4) Add attack  5) Remove attack");
        int choiceEditPokemon=readIntBetween(scanner, "Your choice: ", 1, 5);

        if (choiceEditPokemon==1){
            editName(scanner, pokedex);
        }

        if (choiceEditPokemon==2){
            editType(scanner, pokedex);
        }

        if (choiceEditPokemon==3){
            editHp(scanner, pokedex);
        }

        if (choiceEditPokemon==4){
            editAddAttack(scanner, pokedex);
        }

        if (choiceEditPokemon==5){
            removeAttack(scanner, pokedex);
        }
    }

    public static void pokedexOptions(Scanner scanner){
        System.out.println("\nYour options:");
        System.out.println("1) Show pokedex  2) Add Pokemon  3) Delete pokemon  4) Reset to seed data");
        int choicePokedex=readIntBetween(scanner, "Your choice: ", 1, 4);

        if (choicePokedex==1){
            readPokedex(pokedex);
        }

        if (choicePokedex==2){
            addPokemon(pokedex, scanner);
        }

        if (choicePokedex==3){
            deletePokemon(pokedex, scanner);
        }

        if (choicePokedex==4){
            System.out.println("\nResetting the pokedex");
            seedData();
        }

    }

    private static void seedData() {
        pokedex.clear();

        Pokemon p1 = new Pokemon("Pikachu", Type.ELECTRIC, 100, 100);
        p1.getAttacks().add(new Attack("Thunderbolt", 90, 100, Type.ELECTRIC));
        p1.getAttacks().add(new Attack("Quick Attack", 40, 100, Type.NORMAL));

        Pokemon p2 = new Pokemon("Charmander", Type.FIRE, 95, 95);
        p2.getAttacks().add(new Attack("Ember", 40, 100, Type.FIRE));

        Pokemon p3 = new Pokemon("Squirtle", Type.WATER, 105, 105);
        p3.getAttacks().add(new Attack("Water Gun", 40, 100, Type.WATER));

        Pokemon p4 = new Pokemon("Bulbasaur", Type.GRASS, 100, 100);
        p4.getAttacks().add(new Attack("Vine Whip", 45, 100, Type.GRASS));

        Pokemon p5 = new Pokemon("Eevee", Type.NORMAL, 110, 110);
        p5.getAttacks().add(new Attack("Tackle", 40, 100, Type.NORMAL));

        Pokemon p6 = new Pokemon("Snorlax", Type.NORMAL, 200, 200);
        p6.getAttacks().add(new Attack("Body Slam", 85, 100, Type.NORMAL));

        pokedex.addAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
    }

    private static void seedDataComputer() {
        pokedexComputer.clear();

        Pokemon p1 = new Pokemon("Pidgey", Type.NORMAL, 40, 40);
        p1.getAttacks().add(new Attack("Headbutt", 70, 100, Type.NORMAL));
        p1.getAttacks().add(new Attack("Heat Wave", 95, 90, Type.FIRE));

        Pokemon p2 = new Pokemon("Jigglypuff", Type.NORMAL, 115, 115);
        p2.getAttacks().add(new Attack("Tackle", 40, 100, Type.NORMAL));

        Pokemon p3 = new Pokemon("Vulpix", Type.FIRE, 38, 38);
        p3.getAttacks().add(new Attack("Inferno", 100, 50, Type.FIRE));
        p3.getAttacks().add(new Attack("Fire Blast", 110, 85, Type.FIRE));

        Pokemon p4 = new Pokemon("Raticate", Type.NORMAL, 55, 55);
        p4.getAttacks().add(new Attack("Double-Edge", 120, 100, Type.NORMAL));

        Pokemon p5 = new Pokemon("Gloom", Type.GRASS, 60, 60);
        p5.getAttacks().add(new Attack("Mega Drain", 40, 100, Type.GRASS));
        p5.getAttacks().add(new Attack("Giga Drain", 75, 100, Type.GRASS));

        Pokemon p6 = new Pokemon("Psyduck", Type.WATER, 50, 50);
        p6.getAttacks().add(new Attack("Aqua Tail", 90, 90, Type.WATER));

        Pokemon p7 = new Pokemon("Persian", Type.NORMAL, 65, 65);
        p7.getAttacks().add(new Attack("Slash", 70, 100, Type.NORMAL));

        pokedexComputer.addAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
    }

}