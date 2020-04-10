package souza.home.com.pokedexapp.utils

fun cropPokeUrl(url: String) : String {
    val id = url.substringAfterLast("n/")?.substringBeforeLast("/")

    return id
}

/*
fun cropChainUrl(url: String) : Int{

}*/
