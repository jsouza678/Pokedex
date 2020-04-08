package souza.home.com.pokedexapp.data.pokedex.remote.model.ability

import com.squareup.moshi.Json

data class PokeAbilityRoot(
    @Json(name="effect_entries")
    var effect : MutableList<PokeEffectDescription>
)