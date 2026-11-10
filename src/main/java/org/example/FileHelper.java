package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static Path pokemonPath=Path.of("saved-pokemon.txt");
    public static Path attackPath=Path.of("saved-attacks.txt");
    public static Path pokemonFightPath=Path.of("saved-pokemon-fight.txt");
    public static Path attackFightPath=Path.of("saved-attacks-fight.txt");

    public static void saveFightDataPokemonToCSV(ArrayList<Pokemon> pokemon){
        ArrayList<String> pokemonFightLines = new ArrayList<>();
        ArrayList<String> attackFightLines = new ArrayList<>();

        for (Pokemon pok: pokemon){
            pokemonFightLines.add(pok.getName() + ","+ pok.getType() + ","+ pok.getMaxHp() + ","+ pok.getCurrentHp());
            for (Attack att : pok.getAttacks()){
                attackFightLines.add(att.getName() + ","+ att.getType() + ","+ att.getBaseDamage() + "," + att.getAccuracy() + ","+ pok.getName());
            }
        }

        String allPokemonFight= String.join("\n", pokemonFightLines);
        String allAttacksFight= String.join("\n", attackFightLines);

        try {
            Files.writeString(pokemonFightPath, allPokemonFight);
            Files.writeString(attackFightPath, allAttacksFight);
        }
        catch (IOException e){
            System.err.println("Couldn't save files: " + e.getMessage());
        }
    }

    public static void saveDataToCSV(ArrayList<Pokemon> pokemon){
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

    public static List<Pokemon> loadDataFromCSV(){
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
}
