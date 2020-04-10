package souza.home.com.pokedexapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeColor
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeEvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeFlavorDescription
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarieties

class TypeConverter {

    companion object{

        @TypeConverter
        fun fromVarieties(pokeVarieties: MutableList<PokeVarieties>?): String? {
            if (pokeVarieties == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<MutableList<PokeVarieties>>() {

            }.getType()
            return gson.toJson(pokeVarieties, type)
        }

        @TypeConverter
        fun fromDescription(pokeFlavorDescription: MutableList<PokeFlavorDescription>?): String? {
            if (pokeFlavorDescription == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<MutableList<PokeFlavorDescription>>() {

            }.getType()
            return gson.toJson(pokeFlavorDescription, type)
        }

        @TypeConverter
        fun fromColor(pokeColor: PokeColor?): String? {
            if (pokeColor == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<PokeColor>() {

            }.getType()
            return gson.toJson(pokeColor, type)
        }

        @TypeConverter
        fun fromEvolutionChain(evolutionChain: PokeEvolutionPath?): String? {
            if (evolutionChain == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<PokeEvolutionPath>() {

            }.getType()
            return gson.toJson(evolutionChain, type)
        }

        ///////////////////////

        @TypeConverter
        fun ToVarietiesList(pokeVarieties: String?): MutableList<PokeVarieties>? {
            if (pokeVarieties == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<MutableList<PokeVarieties>>() {

            }.getType()
            return gson.fromJson(pokeVarieties, type)
        }

        @TypeConverter
        fun ToDescriptionList(pokeDescription: String?): MutableList<PokeFlavorDescription>? {
            if (pokeDescription == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<MutableList<PokeFlavorDescription>>() {

            }.getType()
            return gson.fromJson(pokeDescription, type)
        }

        @TypeConverter
        fun ToColor(color: String?): PokeColor? {
            if (color == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<PokeColor>() {

            }.getType()
            return gson.fromJson(color, type)
        }


        @TypeConverter
        fun ToEvolutionPath(evolutionPath: String?): PokeEvolutionPath? {
            if (evolutionPath == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<PokeEvolutionPath>() {

            }.getType()
            return gson.fromJson(evolutionPath, type)
        }


    }


}