package com.souza.pokedetail.presentation.pokedetails.pokeattributes.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.model.variety.Varieties
import com.souza.pokedetail.data.pokedex.remote.response.PokemonResponse
import com.souza.pokedetail.utils.Constants.Companion.ABSOLUTE_ZERO
import com.souza.pokedetail.utils.Constants.Companion.EMPTY_STRING
import java.util.Locale

class AboutSpinnerAdapter :
    BaseAdapter() {

    private val pokeVariations = mutableListOf<Varieties>()

    private var pokemonMock: PokemonResponse =
        PokemonResponse(
            EMPTY_STRING,
            "Select one item"
        )
    private var poke: Varieties =
        Varieties(pokemonMock)

    @ExperimentalStdlibApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val rowView = convertView ?: LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_row,
                parent,
                false
            )

        val pokeVariationItem = pokeVariations[position]

        rowView.findViewById<TextView>(R.id.text_view_item_list).text = pokeVariationItem.pokemon.name.capitalize(
            Locale.getDefault())
        rowView.tag = position

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
