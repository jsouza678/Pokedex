package souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.evolution_chain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R

class EvolutionChainAdapter(private val context: Context, private val dataList: MutableList<String>?) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun submitList(newData: MutableList<String>) {
        if (dataList != null) {
            if (dataList.isNotEmpty()) {
                dataList.clear()
            }
        }
        dataList?.addAll(newData)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val dataItem = dataList?.get(position)
        val rowView = inflater.inflate(R.layout.list_item_row, parent, false)
        val tv = rowView.findViewById<TextView>(R.id.text_view_item_list)

        if (dataList != null) {
            if (dataList.size > 0) {
                tv.text = dataItem?.capitalize()
            }
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
        if (dataList != null) {
            return dataList.size
        } else {
            return 0
        }
    }
}
