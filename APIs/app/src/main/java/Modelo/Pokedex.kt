package Modelo

data class PokedexDescription(
    val description: String,
    val language: Language
)

data class Language(
    val name: String,
    val url: String
)

data class PokedexName(
    val name: String,
    val language: Language
)

data class PokemonEntry(
    val entry_number: Int,
    val pokemon_species: PokemonSpecies
)

data class PokemonSpecies(
    val name: String,
    val url: String
)

data class Pokedex(
    val descriptions: List<PokedexDescription>,
    val id: Int,
    val is_main_series: Boolean,
    val name: String,
    val names: List<PokedexName>,
    val pokemon_entries: List<PokemonEntry>
)

