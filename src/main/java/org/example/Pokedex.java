package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Pokedex {
    private static ArrayList<Pokemon> pokedex = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        if (pokedex.isEmpty()){
            seedData();
        }

        while (true) {
            System.out.println("\nPokedex");
            System.out.println("1) Pokedex options  2) Save to file and exit  3) Edit Pokemon  4) Save data to file  5) Load data from file");
            int choice = readIntBetween(scanner, "Your choice: ", 1, 5);

            if (choice == 1) {
                System.out.println("\nYour options:");
                System.out.println("1) Show pokedex  2) Add Pokemon  3) Delete pokemon  4) Reset to seed data");
                int choiceEditPokedex=readIntBetween(scanner, "Your choice: ", 1, 4);

                if (choiceEditPokedex==1){
                    if (pokedex.isEmpty()){
                        System.out.println("\nThe pokedex is empty.");
                    }
                    else{
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
                }

                if (choiceEditPokedex==2){
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

                if (choiceEditPokedex==3){
                    if (pokedex.isEmpty()){
                        System.out.println("\nThe pokedex is empty.");
                    }
                    else{
                        System.out.println(" ");
                        for (int i= 0; i<pokedex.size(); i++){
                            System.out.println(i + ". " + pokedex.get(i).getName());
                        }
                        int number= readIntBetween(scanner, "\nEnter the number of the Pokemon you'd like to delete: ", 0, pokedex.size()-1);
                        System.out.println("\nDeleting "+ pokedex.get(number).getName());
                        pokedex.remove(number);

                    }
                }

                if (choiceEditPokedex==4){
                    System.out.println("\nResetting the pokedex");
                    seedData();
                }

            }

            if (choice == 2) {
                if (pokedex.isEmpty()){
                    System.out.println("\nThere are no Pokemon to save.");
                    System.out.println("\nExiting program...");
                    break;
                }
                else {
                    Path pokemonPath=Path.of("saved-pokemon.txt");
                    Path attackPath=Path.of("saved-attacks.txt");

                    ArrayList<Pokemon> allPokemonToSave = new ArrayList<>(pokedex);

                    saveDataToCSV(allPokemonToSave, pokemonPath, attackPath);

                    System.out.println("\nExiting program...");
                    break;
                }
            }

            if (choice==3){
                System.out.println("\nChoose something to edit:");
                System.out.println("1) Edit name  2) Edit type  3) Edit HP  4) Add attack  5) Remove attack");
                int choiceEditPokemon=readIntBetween(scanner, "Your choice: ", 1, 5);

                if (choiceEditPokemon==1){
                    if (pokedex.isEmpty()){
                        System.out.println("\nThe pokedex is empty.");
                    }
                    else{
                        System.out.println(" ");
                        for (int i=0; i< pokedex.size(); i++){
                            System.out.println(i+ ". "+ pokedex.get(i).getName());
                        }
                        int num= readIntBetween(scanner, "\nEnter the number of the Pokemon whose name you'd like to change: ", 0, pokedex.size()-1);
                        String newName=readString(scanner, "What would you like to call the Pokemon: ");
                        Pokemon pokemon=new Pokemon(newName, pokedex.get(num).getType(), pokedex.get(num).getMaxHp(), pokedex.get(num).getCurrentHp());
                        for (Attack attack : pokedex.get(num).getAttacks()) {
                            pokemon.getAttacks().add(new Attack(attack.getName(), attack.getBaseDamage(), attack.getAccuracy(), attack.getType()));
                        }
                        pokedex.set(num, pokemon);
                        System.out.println("\nThe name has successfully been changed to " + newName);
                    }
                }

                if (choiceEditPokemon==2){
                    if (pokedex.isEmpty()){
                        System.out.println("\nThe pokedex is empty.");
                    }
                    else{
                        System.out.println(" ");
                        for (int i=0; i< pokedex.size(); i++){
                            System.out.println(i+ ". "+ pokedex.get(i).getName() + " ("+ pokedex.get(i).getType() + ")");
                        }
                        int num= readIntBetween(scanner, "\nEnter the number of the Pokemon to change its type: ", 0, pokedex.size()-1);
                        Type newType= readType(scanner, "What should the type be (FIRE, WATER, GRASS, ELECTRIC, NORMAL): ");
                        Pokemon pokemon=new Pokemon(pokedex.get(num).getName(), newType, pokedex.get(num).getMaxHp(), pokedex.get(num).getCurrentHp());
                        for (Attack attack : pokedex.get(num).getAttacks()) {
                            pokemon.getAttacks().add(new Attack(attack.getName(), attack.getBaseDamage(), attack.getAccuracy(), attack.getType()));
                        }
                        pokedex.set(num, pokemon);
                        System.out.println("\n" + pokemon.getName() + "'s type has successfully been changed to " + pokemon.getType());
                    }
                }

                if (choiceEditPokemon==3){
                    if (pokedex.isEmpty()){
                        System.out.println("\nThe pokedex is empty.");
                    }
                    else{
                        System.out.println(" ");
                        for (int i=0; i< pokedex.size(); i++){
                            System.out.println(i+ ". "+ pokedex.get(i).getName() + " ("+ pokedex.get(i).getMaxHp() + " HP)");
                        }
                        int num= readIntBetween(scanner, "\nEnter the number of the Pokemon to change its max HP: ", 0, pokedex.size()-1);
                        int newHp= readIntBetween(scanner, "What should the max HP be (1-255): ", 1, 255);
                        Pokemon pokemon=new Pokemon(pokedex.get(num).getName(), pokedex.get(num).getType(), newHp, newHp);
                        for (Attack attack : pokedex.get(num).getAttacks()) {
                            pokemon.getAttacks().add(new Attack(attack.getName(), attack.getBaseDamage(), attack.getAccuracy(), attack.getType()));
                        }
                        pokedex.set(num, pokemon);
                        System.out.println("\n" + pokemon.getName() + "'s HP has successfully been changed to " + pokemon.getMaxHp());
                    }
                }

                if (choiceEditPokemon==4){
                    if (pokedex.isEmpty()){
                        System.out.println("\nThe pokedex is empty");
                    }
                    else {
                        for (int i=0; i< pokedex.size(); i++){
                            System.out.println("\n" + i+ ". "+ pokedex.get(i).getName() + " has attack(s):");
                            for (Attack attack : pokedex.get(i).getAttacks()){
                                System.out.println(attack.getName());
                            }
                        }
                        int num=readIntBetween(scanner, "\nEnter the number of a Pokemon to give it an extra attack: ", 0, pokedex.size()-1);
                        if (pokedex.get(num).getAttacks().size()==4){
                            System.out.println(" ");
                            System.out.println(pokedex.get(num).getName() + " already has 4 attacks.");
                        }
                        else{
                            addAttack(scanner, pokedex.get(num));
                            System.out.println("\n" + pokedex.get(num).getAttacks().getLast().getName() + " has successfully been added.");
                        }
                    }

                }

                if (choiceEditPokemon==5){
                    if (pokedex.isEmpty()){
                        System.out.println("\nThe pokedex is empty");
                    }
                    else {
                        for (int i=0; i< pokedex.size(); i++){
                            System.out.println("\n" + i+ ". "+ pokedex.get(i).getName() + " has attack(s):");
                            for (Attack attack : pokedex.get(i).getAttacks()){
                                System.out.println(attack.getName());
                            }
                        }
                        int num=readIntBetween(scanner, "\nEnter the number of a Pokemon to remove an attack: ", 0, pokedex.size()-1);
                        if (pokedex.get(num).getAttacks().size()==1){
                            System.out.println(" ");
                            System.out.println(pokedex.get(num).getName() + " only has 1 attack.");
                        }
                        else{
                            System.out.println(" ");
                            for (int i = 0; i < pokedex.get(num).getAttacks().size(); i++){
                                System.out.println(i+ ". "+ pokedex.get(num).getAttacks().get(i).getName());
                            }
                            int removeAttack=readIntBetween(scanner, "\nEnter the number of the attack you'd like to remove: ", 0, pokedex.get(num).getAttacks().size()-1);
                            System.out.println("\nRemoving " + pokedex.get(num).getAttacks().get(removeAttack).getName() + "...");
                            pokedex.get(num).getAttacks().remove(removeAttack);
                        }
                    }
                }

            }

            if (choice==4){
                if (pokedex.isEmpty()){
                    System.out.println("\nThe pokedex is empty. There is nothing to save.");
                }
                else {
                    Path pokemonPath=Path.of("saved-pokemon.txt");
                    Path attackPath=Path.of("saved-attacks.txt");

                    ArrayList<Pokemon> allPokemonToSave = new ArrayList<>(pokedex);

                    saveDataToCSV(allPokemonToSave, pokemonPath, attackPath);
                }

            }

            if (choice==5){
                pokedex.clear();
                Path pokemonPath=Path.of("saved-pokemon.txt");
                Path attackPath=Path.of("saved-attacks.txt");
                List<Pokemon> loadedPokemon=loadDataFromCSV(pokemonPath, attackPath);
                pokedex= (ArrayList<Pokemon>) loadedPokemon;
                if (pokedex.isEmpty()){
                    System.out.println("\nNo data has been loaded. The pokedex is empty.");
                }
                else{
                    System.out.println("\nThe data has successfully been loaded.");
                }
            }

        }

        scanner.close();
    }

    public static void addAttack(Scanner scanner, Pokemon pokemon){
        String nameAttack = readString(scanner, "\nName of attack: ");
        int baseDamage= readIntBetween(scanner, "Base damage (1-250): ", 1, 250);
        int accuracy= readIntBetween(scanner, "Accuracy (30-100): ",30 ,100);
        Type typeAttack=readType(scanner, "Enter the type of the attack (FIRE, WATER, GRASS, ELECTRIC, NORMAL): ");
        pokemon.getAttacks().add(new Attack(nameAttack, baseDamage, accuracy, typeAttack));
    }

    public static Type readType(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            try {
                String input= scanner.nextLine().toUpperCase();
                return Type.valueOf(input);
            }
            catch (IllegalArgumentException e){
                System.out.println("That is not a valid type.");
            }
        }
    }

    public static int readInt(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            try {
                int value=scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Try again.");
                scanner.nextLine();
            }
        }
    }

    public static int readIntBetween(Scanner scanner, String prompt, int min, int max){
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.printf("The value must be in between %d and %d.%n", min, max);
        }

    }

    public static String readString(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            String input= scanner.nextLine();
            if (!input.isBlank()){
                return input;
            }
            System.out.println("Your input must not be blank");
        }
    }

    private static void saveDataToCSV(ArrayList<Pokemon> pokemon, Path pokemonPath, Path attackPath){
        ArrayList<String> pokemonLines = new ArrayList<>();
        ArrayList<String> attackLines = new ArrayList<>();

        for (Pokemon pok: pokemon){
            pokemonLines.add(pok.getName() + ","+ pok.getType() + ","+ pok.getMaxHp() + ","+ pok.getCurrentHp());
            for (Attack att : pok.getAttacks()){
                attackLines.add(att.getName() + ","+ att.getType() + ","+ att.getBaseDamage() + "," + att.getAccuracy() + ","+ pok.getName());
            }
        }

        String allPokemon= String.join("\n", pokemonLines);
        String allAttacks= String.join("\n", attackLines);

        try {
            Files.writeString(pokemonPath, allPokemon);
            Files.writeString(attackPath, allAttacks);
            System.out.println("\nYour Pokemon have been saved!");
        }
        catch (IOException e){
            System.err.println("Couldn't save files: " + e.getMessage());
        }
    }

    private static List<Pokemon> loadDataFromCSV(Path pokemonPath, Path attackPath){
        List<Pokemon> loadedPokemon=new ArrayList<>();
        try {
            List<String> pokemonLines=Files.readAllLines(pokemonPath);
            for (String line : pokemonLines){
                if (line.isBlank()) continue;
                String[] parts=line.split(",");
                String name=parts[0];
                Type type= Type.valueOf(parts[1]);
                int maxHp= Integer.parseInt(parts[2]);
                int currentHp= Integer.parseInt(parts[3]);

                Pokemon pok = new Pokemon(name, type, maxHp, currentHp);
                loadedPokemon.add(pok);
            }

            List<String> attackLines=Files.readAllLines(attackPath);
            for (String line : attackLines){
                if (line.isBlank()) continue;
                String[] parts=line.split(",");
                String name=parts[0];
                Type type=Type.valueOf(parts[1]);
                int baseDamage=Integer.parseInt(parts[2]);
                int accuracy=Integer.parseInt(parts[3]);
                String belongsToPokemon=parts[4];

                Attack att = new Attack(name, baseDamage, accuracy, type);

                for (Pokemon pok : loadedPokemon){
                    if (pok.getName().equalsIgnoreCase(belongsToPokemon)){
                        pok.getAttacks().add(att);
                        break;
                    }
                }
            }

            loadedPokemon.removeIf(pokemon -> pokemon.getName() == null || pokemon.getName().isBlank() ||
                    pokemon.getMaxHp() < 1 || pokemon.getMaxHp() > 255 || pokemon.getCurrentHp() < 0 ||
                    pokemon.getCurrentHp() > pokemon.getMaxHp() || pokemon.getAttacks().isEmpty() ||
                    pokemon.getAttacks().size() > 4);

            for (Pokemon pokemon : loadedPokemon){
                for (Attack attack : pokemon.getAttacks()){
                    if (attack.getName()==null || attack.getName().isBlank() || attack.getBaseDamage() < 1 ||
                            attack.getBaseDamage() > 250 || attack.getAccuracy() < 30 || attack.getAccuracy() > 100){
                        loadedPokemon.remove(pokemon);
                    }
                }
            }
        }
        catch (IOException e){
            System.err.println("Couldn't load files: " + e.getMessage());
        }
        catch (Exception e){
            System.out.println("\nSomething went wrong when loading the files.");
        }

        return loadedPokemon;
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


}