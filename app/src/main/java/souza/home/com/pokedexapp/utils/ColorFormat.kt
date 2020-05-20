package souza.home.com.pokedexapp.utils

import souza.home.com.pokedexapp.R

class ColorFormat {
    companion object {
        fun setColor(color: String?, pokeId: Int?): Int {
            var formattedColor = when (color) {
                "red" -> R.color.poke_red
                "green" -> R.color.poke_green
                "blue" -> R.color.poke_blue
                "grey" -> R.color.poke_grey
                "black" -> R.color.poke_black
                "yellow" -> R.color.poke_yellow
                "white" -> R.color.poke_white
                "purple" -> R.color.poke_purple
                "pink" -> R.color.poke_pink
                "brown" -> R.color.poke_brown
                else -> R.color.poke_grey
            }

            if (pokeId != null) {
                if (pokeId > Constants.LIMIT_NORMAL_POKES) {
                    formattedColor = R.color.poke_black
                }
            }
            return formattedColor
        }
    }
}
