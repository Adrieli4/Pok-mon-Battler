package org.example.repository;

import org.example.Pokemon;

import java.util.List;

public interface PokemonDao {
    List<Pokemon> getAllPokemon();
    void saveAllPokemon(List<Pokemon> pokemonList);
}
