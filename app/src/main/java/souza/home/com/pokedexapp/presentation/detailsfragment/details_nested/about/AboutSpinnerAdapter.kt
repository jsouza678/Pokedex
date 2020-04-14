package souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.about

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokemonResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

class AboutSpinnerAdapter(private val context: Context, private val dataList: MutableList<Varieties>) : BaseAdapter() {

    var pokemon: PokemonResponse =
        PokemonResponse(EMPTY_STRING, context.getString(R.string.spinner_hint))
    var poke: Varieties =
        Varieties(pokemon)

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var rowView = convertView

        if (rowView == null) {
            val dataItem = dataList[position]
            rowView = inflater.inflate(R.layout.list_item_row, parent, false)
            rowView.findViewById<TextView>(R.id.text_view_item_list).text = dataItem.pokemon.name.capitalize()
            rowView.tag = position
        }

        return rowView!!
    }

    fun submitList(newData: MutableList<Varieties>?) {
        if (dataList.isNotEmpty()) {
            dataList.clear()
        }
        dataList.add(0, poke)
        newData?.let { dataList.addAll(it) }
        this.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataList.size
    }
}
