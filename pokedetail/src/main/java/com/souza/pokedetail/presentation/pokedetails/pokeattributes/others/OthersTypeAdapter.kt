package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.model.type.TypeRoot
import java.util.Locale

class OthersTypeAdapter :
    BaseAdapter() {

    private val pokeTypes = mutableListOf<TypeRoot>()

    fun submitList(newData: MutableList<TypeRoot>) {
        if (pokeTypes.isNotEmpty()) {
            pokeTypes.clear()
        }
        pokeTypes.addAll(newData)
        this.notifyDataSetChanged()
    }

    @ExperimentalStdlibApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = convertView ?: LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_row,
                parent,
                false
            )

        val typeItem = pokeTypes[position]

        rowView.findViewById<TextView>(R.id.text_view_item_list).text = typeItem.type?.name?.capitalize(
            Locale.getDefault())

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
