package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import static org.example.InputHelper.*;

public class PokedexCRUD {
    public static void showPokemon(Pokemon pokemon){
        System.out.println("\nName: " + pokemon.getName() + " (" + pokemon.getType() + ")");
        System.out.println(" - Max HP: " + pokemon.getMaxHp());
        System.out.println(" - Current HP: " + pokemon.getCurrentHp());
        System.out.println(" - Attacks:");
        for (Attack att : pokemon.getAttacks()) {
            System.out.println("  -  " + att.getName() + " (base damage: " + att.getBaseDamage()
                    + ", accuracy: " + att.getAccuracy() + ", type: " + att.getType() + ")");
        }
    }

    public static void removeAttack(Scanner scanner, ArrayList<Pokemon> pokedex){
        pokedexNameChoice(pokedex);
        int num=readIntBetween(scanner, "\nTo choose a Pokemon, enter the corresponding number: ", 0, pokedex.size()-1);
        if (pokedex.get(num).getAttacks().size()==1){
            System.out.println(" ");
            System.out.println(pokedex.get(num).getName() + " only has 1 attack.");
        }
        else{
            System.out.println(" ");
            for (int i = 0; i < pokedex.get(num).getAttacks().size(); i++){
                System.out.println(i+ ". "+ pokedex.get(num).getAttacks().get(i).getName());
            }
            int removeAttack=readIntBetween(scanner, "\nTo remove an attack, enter the corresponding number: ", 0, pokedex.get(num).getAttacks().size()-1);
            System.out.println("\nRemoving " + pokedex.get(num).getAttacks().get(removeAttack).getName() + "...");
            pokedex.get(num).getAttacks().remove(removeAttack);
        }
    }

    public static void editAddAttack(Scanner scanner, ArrayList<Pokemon> pokedex){
        pokedexNameChoice(pokedex);
        int num=readIntBetween(scanner, "\nTo choose a Pokemon, enter the corresponding number: ", 0, pokedex.size()-1);
        if (pokedex.get(num).getAttacks().size()==4){
            System.out.println(" ");
            System.out.println(pokedex.get(num).getName() + " already has 4 attacks.");
        }
        else{
            addAttack(scanner, pokedex.get(num));
            System.out.println("\n" + pokedex.get(num).getAttacks().getLast().getName() + " has successfully been added.");
        }
    }

    public static void editHp(Scanner scanner, ArrayList<Pokemon> pokedex){
        pokedexNameChoice(pokedex);
        int num= readIntBetween(scanner, "\nTo choose a Pokemon, enter the corresponding number: ", 0, pokedex.size()-1);
        int newHp= readIntBetween(scanner, "What should the max HP be (1-255): ", 1, 255);
        Pokemon pokemon=new Pokemon(pokedex.get(num).getName(), pokedex.get(num).getType(), newHp, newHp);
        for (Attack attack : pokedex.get(num).getAttacks()) {
            pokemon.getAttacks().add(new Attack(attack.getName(), attack.getBaseDamage(), attack.getAccuracy(), attack.getType()));
        }
        pokedex.set(num, pokemon);
        System.out.println("\n" + pokemon.getName() + "'s HP has successfully been changed to " + pokemon.getMaxHp());
    }

    public static void editType(Scanner scanner, ArrayList<Pokemon> pokedex){
        pokedexNameChoice(pokedex);
        int num= readIntBetween(scanner, "\nTo choose a Pokemon, enter the corresponding number: ", 0, pokedex.size()-1);
        Type newType= readType(scanner, "What should the type be (FIRE, WATER, GRASS, ELECTRIC, NORMAL): ");
        Pokemon pokemon=new Pokemon(pokedex.get(num).getName(), newType, pokedex.get(num).getMaxHp(), pokedex.get(num).getCurrentHp());
        for (Attack attack : pokedex.get(num).getAttacks()) {
            pokemon.getAttacks().add(new Attack(attack.getName(), attack.getBaseDamage(), attack.getAccuracy(), attack.getType()));
        }
        pokedex.set(num, pokemon);
        System.out.println("\n" + pokemon.getName() + "'s type has successfully been changed to " + pokemon.getType());
    }

    public static void editName(Scanner scanner, ArrayList<Pokemon> pokedex){
        pokedexNameChoice(pokedex);
        int num= readIntBetween(scanner, "\nTo choose a Pokemon, enter the corresponding number: ", 0, pokedex.size()-1);
        String newName=readString(scanner, "What would you like to call the Pokemon: ");
        Pokemon pokemon=new Pokemon(newName, pokedex.get(num).getType(), pokedex.get(num).getMaxHp(), pokedex.get(num).getCurrentHp());
        for (Attack attack : pokedex.get(num).getAttacks()) {
            pokemon.getAttacks().add(new Attack(attack.getName(), attack.getBaseDamage(), attack.getAccuracy(), attack.getType()));
        }
        pokedex.set(num, pokemon);
        System.out.println("\nThe name has successfully been changed to " + newName);
    }

    public static void pokedexNameChoice(ArrayList<Pokemon> pokedex){
        System.out.println(" ");
        for (int i=0; i< pokedex.size(); i++){
            System.out.println(i+ ". "+ pokedex.get(i).getName());
        }
    }

    public static void deletePokemon(ArrayList<Pokemon> pokedex, Scanner scanner){
        if (pokedex.isEmpty()){
            System.out.println("\nThe pokedex is empty.");
        }
        else{
            pokedexNameChoice(pokedex);
            int number= readIntBetween(scanner, "\nEnter the number of the Pokemon you'd like to delete: ", 0, pokedex.size()-1);
            System.out.println("\nDeleting "+ pokedex.get(number).getName());
            pokedex.remove(number);

        }
    }

    public static void addAttack(Scanner scanner, Pokemon pokemon){
        String nameAttack = readString(scanner, "\nName of attack: ");
        int baseDamage= readIntBetween(scanner, "Base damage (1-250): ", 1, 250);
        int accuracy= readIntBetween(scanner, "Accuracy (30-100): ",30 ,100);
        Type typeAttack=readType(scanner, "Enter the type of the attack (FIRE, WATER, GRASS, ELECTRIC, NORMAL): ");
        pokemon.getAttacks().add(new Attack(nameAttack, baseDamage, accuracy, typeAttack));
    }

    public static void addPokemon(ArrayList<Pokemon> pokedex, Scanner scanner){
        String name = readString(scanner, "\nEnter Pokemon name: ");

        Type type = readType(scanner, "Enter type (FIRE, WATER, GRASS, ELECTRIC, NORMAL): ");

        int maxHp = readIntBetween(scanner, "Enter Max HP (1-255): ", 1, 255);
        Pokemon pokemon=new Pokemon(name, type, maxHp, maxHp);

        int numOfAttacks= readIntBetween(scanner, "Enter the number of attacks the Pokemon has (1-4): ", 1, 4);

        for (int i =1; i<=numOfAttacks; i++){
            addAttack(scanner, pokemon);
        }

        System.out.println("\n" + pokemon.getName() + " has been added to pokedex.");
        pokedex.add(pokemon);
    }

    public static void showPokedex(ArrayList<Pokemon> pokedex){
        for (Pokemon pok : pokedex) {
            showPokemon(pok);
            System.out.println();
        }
    }

    public static void readPokedex(ArrayList<Pokemon> pokedex){
        if (pokedex.isEmpty()){
            System.out.println("\nThe pokedex is empty.");
        }
        else{
            showPokedex(pokedex);
        }
    }
}
