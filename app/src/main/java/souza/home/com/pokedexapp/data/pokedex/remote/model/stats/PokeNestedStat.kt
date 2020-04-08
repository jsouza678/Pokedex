package souza.home.com.pokedexapp.data.pokedex.remote.model.stats

import com.squareup.moshi.Json

data class PokeNestedStat(
    //here i can pickup speed, hp, defense and etc using PokeStats object.pokeNestedStat.. and everything
    @Json(name="name")
    var stat: String
)