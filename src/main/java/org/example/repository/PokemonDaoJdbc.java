package org.example.repository;

import org.example.Attack;
import org.example.Pokemon;
import org.example.Type;
import org.example.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PokemonDaoJdbc implements PokemonDao {

    @Override
    public List<Pokemon> getAllPokemon() {
        String sql = """
                SELECT
                            p.pokemon_id,
                            p.name AS pokemon_name,
                            p.type AS pokemon_type,
                            p.max_hp,
                            p.current_hp,
                            a.attack_id,
                            a.name AS attack_name,
                            a.type AS attack_type,
                            a.base_damage,
                            a.accuracy
                        FROM pokemon p
                        LEFT JOIN attack a
                            ON p.pokemon_id = a.pokemon_id
                        ORDER BY p.pokemon_id, a.attack_id
                """;

        Map<Integer, Pokemon> pokemonMap = new LinkedHashMap<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int pokemonId = rs.getInt("pokemon_id");
                if (!pokemonMap.containsKey(pokemonId)) {
                    Pokemon pokemon = new Pokemon(rs.getString("pokemon_name"),
                            Type.valueOf(rs.getString("pokemon_type")), rs.getInt("max_hp"),
                            rs.getInt("current_hp"));
                    pokemonMap.put(pokemonId, pokemon);
                }
                int attackId = rs.getInt("attack_id");
                if (!rs.wasNull()) {
                    Attack attack = new Attack(
                            rs.getString("attack_name"), rs.getInt("base_damage"),
                            rs.getInt("accuracy"), Type.valueOf(rs.getString("attack_type"))
                    );
                    pokemonMap.get(pokemonId).getAttacks().add(attack);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not retrieve Pokemon.", e);
        }

        return new ArrayList<>(pokemonMap.values());
    }

    @Override
    public void saveAllPokemon(List<Pokemon> pokemonList) {
        String pokemonSql = """
                INSERT INTO pokemon (name, type, max_hp, current_hp)
                VALUES (?,?,?,?)
                ON CONFLICT (name) DO UPDATE SET
                type = EXCLUDED.type,
                            max_hp = EXCLUDED.max_hp,
                            current_hp = EXCLUDED.current_hp
                        RETURNING pokemon_id
                """;

        String deleteAttacksSql = """
                DELETE FROM attack
                WHERE pokemon_id = ?
                """;

        String attackSql = """
        INSERT INTO attack (name, type, base_damage, accuracy, pokemon_id)
        VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn=DatabaseConfig.getConnection()){
            conn.setAutoCommit(false);

            try{
                for (Pokemon pokemon : pokemonList) {

                    if (pokemon.getAttacks().isEmpty()
                            || pokemon.getAttacks().size() > 4) {
                        throw new IllegalArgumentException(
                                "A Pokemon must have between 1 and 4 attacks."
                        );
                    }

                    int pokemonId;

                    // Infoga eller uppdatera Pokémon.
                    try (PreparedStatement pokemonStmt =
                                 conn.prepareStatement(pokemonSql)) {

                        pokemonStmt.setString(1, pokemon.getName());
                        pokemonStmt.setString(2, pokemon.getType().name());
                        pokemonStmt.setInt(3, pokemon.getMaxHp());
                        pokemonStmt.setInt(4, pokemon.getCurrentHp());

                        try (ResultSet rs = pokemonStmt.executeQuery()) {
                            rs.next();
                            pokemonId = rs.getInt("pokemon_id");
                        }
                    }

                    // Ta bort Pokémonens gamla attacker.
                    try (PreparedStatement deleteStmt =
                                 conn.prepareStatement(deleteAttacksSql)) {

                        deleteStmt.setInt(1, pokemonId);
                        deleteStmt.executeUpdate();
                    }

                    // Lägg till Pokémonens aktuella attacker.
                    try (PreparedStatement attackStmt =
                                 conn.prepareStatement(attackSql)) {

                        for (Attack attack : pokemon.getAttacks()) {

                            attackStmt.setString(1, attack.getName());
                            attackStmt.setString(2, attack.getType().name());
                            attackStmt.setInt(3, attack.getBaseDamage());
                            attackStmt.setInt(4, attack.getAccuracy());
                            attackStmt.setInt(5, pokemonId);

                            attackStmt.addBatch();
                        }

                        attackStmt.executeBatch();
                    }
                }

                conn.commit();
            }
            catch (SQLException | RuntimeException e){
                conn.rollback();
                throw e;
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Could not save Pokemon.", e);
        }
    }
}
