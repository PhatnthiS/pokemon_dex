package com.example.pokemon.domain.model

import androidx.compose.ui.graphics.Color

enum class PokemonTypeEnum(
    val displayName: String,
    val color: Color
) {
    NORMAL("Normal", Color(0xFFA8A77A)),
    FIRE("Fire", Color(0xFFEE8130)),
    WATER("Water", Color(0xFF6390F0)),
    ELECTRIC("Electric", Color(0xFFF7D02C)),
    GRASS("Grass", Color(0xFF7AC74C)),
    ICE("Ice", Color(0xFF96D9D6)),
    FIGHTING("Fighting", Color(0xFFC22E28)),
    POISON("Poison", Color(0xFFA33EA1)),
    GROUND("Ground", Color(0xFFE2BF65)),
    FLYING("Flying", Color(0xFFA98FF3)),
    PSYCHIC("Psychic", Color(0xFFF95587)),
    BUG("Bug", Color(0xFFA6B91A)),
    ROCK("Rock", Color(0xFFB6A136)),
    GHOST("Ghost", Color(0xFF735797)),
    DRAGON("Dragon", Color(0xFF6F35FC)),
    DARK("Dark", Color(0xFF705746)),
    STEEL("Steel", Color(0xFFB7B7CE)),
    FAIRY("Fairy", Color(0xFFD685AD)),

    NOT_FOUND("ERROR", Color(0xFF000000));

    companion object {
        fun fromName(name: String): PokemonTypeEnum {
            return entries.find { it.name.equals(name, ignoreCase = true) } ?: NOT_FOUND
        }
    }
}
