package souza.home.com.pokedexapp.presentation.details.details_nested.others

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesMain

class AbilityAdapter (private val context: Context, private val dataList: MutableList<AbilitiesMain>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun submitList(newData: MutableList<AbilitiesMain>){
        if(dataList.isNotEmpty()){
            dataList.clear()
        }
        dataList.addAll(newData)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val dataItem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        rowView.findViewById<TextView>(R.id.text_view_item).text = dataItem.ability.name.capitalize()

        rowView.tag = position
        return rowView
    }

    override fun getItem(position: Int): AbilitiesMain {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataList.size
    }

}