package souza.home.com.pokedexapp.utils

import souza.home.com.pokedexapp.data.pokedex.remote.model.response.EvolutionChainResponse
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.FlavorDescription
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.INT_MAX_SIZE
import souza.home.com.pokedexapp.utils.Constants.Companion.LANGUAGE_DESCRIPTIONS

fun cropPokeUrl(url: String) : String {
    val id = url.substringAfterLast("n/").substringBeforeLast("/")
    return id
}

fun cropAbilityUrl(url: String) : String {
    val id = url.substringAfterLast("y/").substringBeforeLast("/")
    return id
}

fun cropTypeUrl(url: String) : String {
    val id = url.substringAfterLast("e/").substringBeforeLast("/")
    return id
}

fun optimizeDescription(it: MutableList<FlavorDescription>?) : String {
    var description : String = ""

    for(i in 0 until it?.size!!) {
        if (it.get(i).language.name == LANGUAGE_DESCRIPTIONS) {
            description += it[i].flavor_text + "\n "
        }
    }
    return description
}

fun optimizeChain(it: EvolutionChainResponse) : MutableList<String> {
    val croppedList = mutableListOf<String>()

    it.chain.species?.name?.let { it1 -> croppedList.add(it1) }
    it.chain.evolves_to?.get(0)?.species?.name?.let { it1 -> croppedList.add(it1) }
    it.chain.evolves_to?.get(0)?.evolves_to?.get(0)?.species?.name?.let { it1 -> croppedList.add(it1) }

    return croppedList
}

fun isString(text: String) : Boolean{
    var numeric = true
    numeric = text.matches("-?\\d+(\\.\\d+)?".toRegex())

    if(text == EMPTY_STRING){
        return false
    }

    if(numeric == true){
        try{
            Integer.parseInt(text)
            return true
        }catch (e: Exception){
            return false
        }
        return false
    }

    return numeric
}