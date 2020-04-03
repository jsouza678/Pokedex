package souza.home.com.pokedexapp.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolutionChain
import souza.home.com.pokedexapp.network.model.stats.PokeAbilities
import souza.home.com.pokedexapp.network.model.stats.PokeAbility
import souza.home.com.pokedexapp.network.model.stats.PokeTypes

class CustomChainAdapter (private val context: Context, private val dataList: MutableList<PokeEvolution>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun submitList(newData: MutableList<PokeEvolution>){
        if(dataList.isNotEmpty()){
            dataList.clear()
        }
        dataList.addAll(newData)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val dataItem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        val tv = rowView.findViewById<TextView>(R.id.tv_item)


        if(dataList.size > 0){
            tv.text = dataItem.species?.name?.capitalize()
        }else{
            tv.text = "none"
        }


        rowView.tag = position

        return rowView
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