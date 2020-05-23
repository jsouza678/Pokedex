package com.souza.pokedetail.presentation.pokedetails.pokeattributes.about

import android.content.Context
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

class AboutSpinnerAdapter(
    private val context: Context,
    private val pokeVariations: MutableList<Varieties>
) : BaseAdapter() {

    private var pokemonMock: PokemonResponse =
        PokemonResponse(
            EMPTY_STRING,
            context.getString(R.string.spinner_hint)
        )
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
