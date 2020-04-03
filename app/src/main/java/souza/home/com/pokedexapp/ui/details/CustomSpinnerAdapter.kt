package souza.home.com.pokedexapp.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import souza.home.com.pokedexapp.network.model.stats.PokeAbilities
import souza.home.com.pokedexapp.network.model.stats.PokeAbility
import souza.home.com.pokedexapp.network.model.varieties.PokeVarieties

class CustomSpinnerAdapter (private val context: Context, private val dataList: MutableList<PokeVarieties>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val dataItem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        rowView.findViewById<TextView>(R.id.tv_item).text = dataItem.pokemon.name.capitalize()

        rowView.tag = position
        return rowView
    }

    fun submitList(newData: MutableList<PokeVarieties>){
        if(dataList.isNotEmpty()){
            dataList.clear()
        }
        dataList.addAll(newData)
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