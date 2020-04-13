package souza.home.com.pokedexapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesMain
import souza.home.com.pokedexapp.data.pokedex.remote.model.stat.Sprites
import souza.home.com.pokedexapp.data.pokedex.remote.model.stat.Stats
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.Types
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Color
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.EvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties

class TypeConverter {

    companion object{
        private var gson = Gson()

        @TypeConverter
        fun fromEvolution(evolution: MutableList<String>?): String?{
            if(evolution == null){ return null }
            gson = Gson()
            val type = object: TypeToken<MutableList<String>>(){
            }.type

            return gson.toJson(evolution, type)
        }

        @TypeConverter
        fun fromAbilities(pokeAbilities: MutableList<AbilitiesMain>?): String?{
            if(pokeAbilities == null){ return null }
            gson = Gson()
            val type = object: TypeToken<MutableList<AbilitiesMain>>(){
            }.type

            return gson.toJson(pokeAbilities, type)
        }

        @TypeConverter
        fun fromSprites(pokeSprites: Sprites?): String?{
            if(pokeSprites == null){ return null }
            gson = Gson()
            val type = object: TypeToken<Sprites>(){
            }.type

            return gson.toJson(pokeSprites, type)
        }

        @TypeConverter
        fun fromStats(pokeStats: List<Stats>?): String?{
            if(pokeStats == null){ return null }
            gson = Gson()
            val type = object: TypeToken<List<Stats>>(){
            }.type

            return gson.toJson(pokeStats, type)
        }

        @TypeConverter
        fun fromTypes(pokeTypes: MutableList<Types>?): String?{
            if(pokeTypes == null){ return null }
            gson = Gson()
            val type = object: TypeToken<MutableList<Types>>(){
            }.type

            return gson.toJson(pokeTypes, type)
        }

        @TypeConverter
        fun fromVarieties(pokeVarieties: MutableList<Varieties>?): String? {
            if (pokeVarieties == null) { return null }
            gson = Gson()
            val type = object : TypeToken<MutableList<Varieties>>() {
            }.type

            return gson.toJson(pokeVarieties, type)
        }

        @TypeConverter
        fun fromColor(pokeColor: Color?): String? {
            if (pokeColor == null) { return null }
            gson = Gson()
            val type = object : TypeToken<Color>() {
            }.type

            return gson.toJson(pokeColor, type)
        }

        @TypeConverter
        fun fromEvolutionPath(evolutionChain: EvolutionPath?): String? {
            if (evolutionChain == null) { return null }
            gson = Gson()
            val type = object : TypeToken<EvolutionPath>() {
            }.type

            return gson.toJson(evolutionChain, type)
        }
        ///////////////////////
        @TypeConverter
        fun toVarietiesList(pokeVarieties: String?): MutableList<Varieties>? {
            if (pokeVarieties == null) { return null }
            gson = Gson()
            val type = object : TypeToken<MutableList<Varieties>>() {
            }.type

            return gson.fromJson(pokeVarieties, type)
        }

        @TypeConverter
        fun toColor(color: String?): Color? {
            if (color == null) { return null }
            gson = Gson()
            val type = object : TypeToken<Color>() {
            }.type

            return gson.fromJson(color, type)
        }

        @TypeConverter
        fun toEvolutionPath(evolutionPath: String?): EvolutionPath? {
            if (evolutionPath == null) { return null }
            gson = Gson()
            val type = object : TypeToken<EvolutionPath>() {
            }.type

            return gson.fromJson(evolutionPath, type)
        }

        @TypeConverter
        fun toAbilitiesList(pokeAbilities: String?): MutableList<AbilitiesMain>?{
            if(pokeAbilities == null){ return null }
            gson = Gson()
            val type = object: TypeToken<MutableList<AbilitiesMain>>(){
            }.type

            return gson.fromJson(pokeAbilities, type)
        }

        @TypeConverter
        fun toSprites(pokeSprites: String?): Sprites?{
            if(pokeSprites == null){ return null }
            gson = Gson()
            val type = object: TypeToken<Sprites>(){
            }.type

            return gson.fromJson(pokeSprites, type)
        }

        @TypeConverter
        fun toStatsList(pokeStats: String?): List<Stats>?{
            if(pokeStats == null){ return null }
            gson = Gson()
            val type = object: TypeToken<List<Stats>>(){
            }.type

            return gson.fromJson(pokeStats, type)
        }

        @TypeConverter
        fun toTypesList(pokeTypes: String?): MutableList<Types>?{
            if(pokeTypes == null){ return null }
            gson = Gson()
            val type = object: TypeToken<MutableList<Types>>(){
            }.type

            return gson.fromJson(pokeTypes, type)
        }

        @TypeConverter
        fun toEvolution(evolution: String?): MutableList<String>?{
            if(evolution == null){ return null }
            gson = Gson()
            val type = object: TypeToken<MutableList<String>>(){
            }.type

            return gson.fromJson(evolution, type)
        }
    }
}