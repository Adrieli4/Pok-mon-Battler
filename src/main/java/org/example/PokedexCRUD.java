package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import static org.example.InputHelper.*;

public class PokedexCRUD {
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
            System.out.println("\nName: " + pok.getName() + " (" + pok.getType() + ")");
            System.out.println(" - Max HP: " + pok.getMaxHp());
            System.out.println(" - Current HP: " + pok.getCurrentHp());
            System.out.println(" - Attacks:");
            for (Attack att : pok.getAttacks()) {
                System.out.println("  -  " + att.getName() + " (base damage: " + att.getBaseDamage()
                        + ", accuracy: " + att.getAccuracy() + ", type: " + att.getType() + ")");
            }
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
