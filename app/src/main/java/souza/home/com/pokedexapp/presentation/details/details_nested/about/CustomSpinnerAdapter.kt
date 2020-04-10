package souza.home.com.pokedexapp.presentation.details.details_nested.about

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarieties

class CustomSpinnerAdapter (private val context: Context, private val dataList: MutableList<PokeVarieties>) : BaseAdapter() {

    var pokemon: PokemonResponse =
        PokemonResponse("Select one item", "http://")
    var poke : PokeVarieties =
        PokeVarieties(pokemon)

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var rowView = convertView

        if(rowView == null) {
            val dataItem = dataList[position]
            rowView = inflater.inflate(R.layout.list_row, parent, false)
            rowView.findViewById<TextView>(R.id.tv_item).text = dataItem.pokemon.name.capitalize()
            rowView.tag = position
        }

        return rowView!!
    }

    fun submitList(newData: MutableList<PokeVarieties>?){
        if(dataList.isNotEmpty()){
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