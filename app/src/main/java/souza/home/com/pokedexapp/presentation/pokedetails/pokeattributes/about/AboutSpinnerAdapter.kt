package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.about

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.Locale
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokemonResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

class AboutSpinnerAdapter(
    private val context: Context,
    private val pokeVariations: MutableList<Varieties>
) : BaseAdapter() {

    private var pokemonMock: PokemonResponse =
        PokemonResponse(EMPTY_STRING, context.getString(R.string.spinner_hint))
    private var poke: Varieties =
        Varieties(pokemonMock)

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @ExperimentalStdlibApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var rowView = convertView
        if (rowView == null) {
            val pokeVariationItem = pokeVariations[position]
            rowView = inflater.inflate(R.layout.list_item_row, parent, false)
            rowView.findViewById<TextView>(R.id.text_view_item_list).text = pokeVariationItem.pokemon.name?.capitalize(
                Locale.getDefault())
            rowView.tag = position
        }

        return rowView
    }

    fun submitList(newData: MutableList<Varieties>?) {
        if (pokeVariations.isNotEmpty()) {
            pokeVariations.clear()
        }
        pokeVariations.add(ABSOLUTE_ZERO, poke)
        newData?.let { pokeVariations.addAll(it) }
        this.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return pokeVariations.size
    }
}
