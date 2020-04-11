package souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.Evolution

class EvolutionChainAdapter (private val context: Context, private val dataList: MutableList<Evolution>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun submitList(newData: MutableList<Evolution>){
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
            tv.text = context.getString(R.string.no_poke_evolution)
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