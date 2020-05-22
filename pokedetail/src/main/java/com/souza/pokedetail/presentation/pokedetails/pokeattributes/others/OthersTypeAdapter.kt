package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.model.type.TypeRoot

class OthersTypeAdapter(
    private val context: Context,
    private val pokeTypes: MutableList<TypeRoot>
) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun submitList(newData: MutableList<TypeRoot>) {
        if (pokeTypes.isNotEmpty()) {
            pokeTypes.clear()
        }
        pokeTypes.addAll(newData)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val typeItem = pokeTypes[position]
        val rowView = inflater.inflate(R.layout.list_item_row, parent, false)
        rowView.findViewById<TextView>(R.id.text_view_item_list).text = typeItem.type?.name?.capitalize()

        rowView.tag = position
        return rowView
    }

    override fun getItem(position: Int): TypeRoot {
        return pokeTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return pokeTypes.size
    }
}
