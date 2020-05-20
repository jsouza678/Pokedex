package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.evolutionchain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import souza.home.com.pokedexapp.R

class EvolutionChainAdapter(
    private val context: Context,
    private val evolutionChain: MutableList<String>?
) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun submitList(newData: MutableList<String>) {
        if (evolutionChain != null) {
            if (evolutionChain.isNotEmpty()) {
                evolutionChain.clear()
            }
        }
        evolutionChain?.addAll(newData)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val evolutionChainItem = evolutionChain?.get(position)
        val rowView = inflater.inflate(R.layout.list_item_row, parent, false)
        val itemTextView = rowView.findViewById<TextView>(R.id.text_view_item_list)

        if (evolutionChain != null) {
            if (evolutionChain.size > 0) {
                itemTextView.text = evolutionChainItem?.capitalize()
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
        if (evolutionChain != null) {
            return evolutionChain.size
        } else {
            return 0
        }
    }
}
