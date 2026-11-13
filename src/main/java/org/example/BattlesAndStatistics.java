package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.example.FileHelper.*;
import static org.example.FileHelper.saveEnemyHp;
import static org.example.FileHelper.saveLosses;
import static org.example.FileHelper.savePlayerHp;
import static org.example.FileHelper.saveWins;
import static org.example.InputHelper.readIntBetween;
import static org.example.PokedexCRUD.*;
import static org.example.PokedexCRUD.showPokemon;

public class BattlesAndStatistics {
    public static void winsAndLosses(ArrayList<Integer> numberOfLosses, ArrayList<Integer> numberOfWins){
        if (numberOfLosses.isEmpty() || numberOfLosses.getFirst()<0){
            System.out.println("\nYou have never lost.");
        }
        else {
            System.out.println("\nAmount of losses: " + numberOfLosses.getFirst());
        }
        if (numberOfWins.isEmpty() || numberOfWins.getFirst()<0){
            System.out.println("\nYou have never won.");
        }
        else {
            System.out.println("\nAmount of wins: " + numberOfWins.getFirst());
        }
    }

    public static void latestBattleStatistics(ArrayList<Pokemon> fighterPokemon, ArrayList<Integer> playerHp, ArrayList<Integer> enemyHp){
        fighterPokemon.clear();
        playerHp.clear();
        enemyHp.clear();
        List<Pokemon> loadedFighterPokemon=loadFightDataPokemonFromCSV();
        fighterPokemon= (ArrayList<Pokemon>) loadedFighterPokemon;
        playerHp=loadPlayerHp();
        enemyHp=loadEnemyHp();
        if ((fighterPokemon.size()) != 2 || (playerHp.isEmpty()) || (enemyHp.isEmpty()) || (playerHp.size()!=enemyHp.size()) ||
                (playerHp.getLast()==0 && enemyHp.getLast()==0) || (playerHp.getLast()!=0 && enemyHp.getLast()!=0) ||
                (playerHp.getFirst()>fighterPokemon.getFirst().getMaxHp()) || (enemyHp.getFirst()>fighterPokemon.getLast().getMaxHp())){
            System.out.println("\nThere are no statistics.");
        }
        else{
            System.out.println("\nYour Pokemon:");
            showPokemon(fighterPokemon.getFirst());
            System.out.println("\nComputer's Pokemon:");
            showPokemon(fighterPokemon.getLast());
            System.out.println();
            System.out.println("\nYour HP after round number 1: " + playerHp.getFirst());
            if (playerHp.size()==1){
                System.out.println(" ");
            }
            else{
                for (int i=2 ; i <= playerHp.size() ; i++){
                    if (playerHp.get(i-1)>playerHp.get(i-2)){
                        System.out.println("\nYour remaining statistics can't be loaded because they are faulty.");
                        break;
                    }
                    else{
                        System.out.println("\nYour HP after round number " + i + ": " + playerHp.get(i-1));
                    }
                }
            }
            System.out.println("\nComputer HP after round number 1: " + enemyHp.getFirst());
            if (enemyHp.size()==1){
                System.out.println(" ");
            }
            else{
                for (int i=2 ; i <= enemyHp.size() ; i++){
                    if (enemyHp.get(i-1)>enemyHp.get(i-2)){
                        System.out.println("\nThe remaining computer statistics can't be loaded because they are faulty.");
                        break;
                    }
                    else{
                        System.out.println("\nComputer HP after round number " + i + ": " + enemyHp.get(i-1));
                    }
                }
            }
        }
    }

    public static void startBattle(ArrayList<Pokemon> pokedex, ArrayList<Pokemon> fighterPokemon, ArrayList<Integer> playerHp,
                                   ArrayList<Integer> enemyHp, Scanner scanner, ArrayList<Pokemon> pokedexComputer,
                                   ArrayList<Integer> numberOfLosses, ArrayList<Integer> numberOfWins){
        if (pokedex.isEmpty()){
            System.out.println("\nYou can't start a battle if your pokedex is empty.");
        }
        else{
            fighterPokemon.clear();
            playerHp.clear();
            enemyHp.clear();

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
            System.out.println("\nComputer's pokedex:");
            showPokedex(pokedexComputer);
            System.out.println("Computer choice: " + enemy.getName() + ", " + enemy.getCurrentHp() + " HP");

            fighterPokemon.add(player);
            fighterPokemon.add(enemy);
            ArrayList<Pokemon> allPokemonFightersToSave=new ArrayList<>(fighterPokemon);
            saveFightDataPokemonToCSV(allPokemonFightersToSave);

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
                    damageAttackPlayer.execute(player, enemy, random);
                    System.out.println("\nComputer HP: " + enemy.getCurrentHp());
                    enemyHp.add(enemy.getCurrentHp());
                    if (enemy.isFainted()){
                        playerHp.add(player.getCurrentHp());
                        ArrayList<String> playerHpStr=new ArrayList<>();
                        ArrayList<String> enemyHpStr=new ArrayList<>();
                        for (int i : playerHp){
                            playerHpStr.add(String.valueOf(i));
                        }
                        for (int i : enemyHp){
                            enemyHpStr.add(String.valueOf(i));
                        }
                        savePlayerHp(playerHpStr);
                        saveEnemyHp(enemyHpStr);
                        break;
                    }
                    damageAttackEnemy.execute(enemy, player, random);
                    System.out.println("\nYour HP: " + player.getCurrentHp());
                    playerHp.add(player.getCurrentHp());
                }
                else {
                    damageAttackEnemy.execute(enemy, player, random);
                    System.out.println("\nYour HP: " + player.getCurrentHp());
                    playerHp.add(player.getCurrentHp());
                    if (player.isFainted()){
                        enemyHp.add(enemy.getCurrentHp());
                        ArrayList<String> playerHpStr=new ArrayList<>();
                        ArrayList<String> enemyHpStr=new ArrayList<>();
                        for (int i : playerHp){
                            playerHpStr.add(String.valueOf(i));
                        }
                        for (int i : enemyHp){
                            enemyHpStr.add(String.valueOf(i));
                        }
                        savePlayerHp(playerHpStr);
                        saveEnemyHp(enemyHpStr);
                        break;
                    }
                    damageAttackPlayer.execute(player, enemy, random);
                    System.out.println("\nComputer HP: " + enemy.getCurrentHp());
                    enemyHp.add(enemy.getCurrentHp());
                }
            }
            ArrayList<String> playerHpString=new ArrayList<>();
            ArrayList<String> enemyHpString=new ArrayList<>();
            for (int i : playerHp){
                playerHpString.add(String.valueOf(i));
            }
            for (int i : enemyHp){
                enemyHpString.add(String.valueOf(i));
            }
            savePlayerHp(playerHpString);
            saveEnemyHp(enemyHpString);

            if (player.isFainted()){
                System.out.println("\nYou lost! " + enemy.getName() + "'s HP is " + enemy.getCurrentHp() + ".");
                if (numberOfLosses.isEmpty()){
                    numberOfLosses.add(1);
                }
                else {
                    numberOfLosses.set(0, numberOfLosses.getFirst()+1);
                }
            }
            else{
                System.out.println("\nYou won! " + player.getName() + "'s HP is " + player.getCurrentHp() + ".");
                if (numberOfWins.isEmpty()){
                    numberOfWins.add(1);
                }
                else {
                    numberOfWins.set(0, numberOfWins.getFirst()+1);
                }
            }
            player.heal(player.getMaxHp());
            enemy.heal(enemy.getMaxHp());
            if (numberOfLosses.isEmpty()){
                numberOfLosses.add(0);
            }
            if (numberOfWins.isEmpty()){
                numberOfWins.add(0);
            }
            saveLosses(numberOfLosses.getFirst());
            saveWins(numberOfWins.getFirst());
        }
    }
}
