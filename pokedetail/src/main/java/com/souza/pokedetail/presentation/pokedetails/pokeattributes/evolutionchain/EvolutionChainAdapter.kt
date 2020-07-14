package com.souza.pokedetail.presentation.pokedetails.pokeattributes.evolutionchain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.souza.pokedetail.R
import com.souza.pokedetail.utils.Constants.Companion.ABSOLUTE_ZERO
import java.util.Locale

class EvolutionChainAdapter
    : BaseAdapter() {

    private val evolutionChain = mutableListOf<String>()

    fun submitList(newData: MutableList<String>) {
        if (evolutionChain.isNotEmpty()) {
            evolutionChain.clear()
        }
        evolutionChain.addAll(newData)
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

        val evolutionChainItem = evolutionChain[position]
        val itemTextView = rowView.findViewById<TextView>(R.id.text_view_item_list)

        if (evolutionChain.size > ABSOLUTE_ZERO) {
            itemTextView.text = evolutionChainItem.capitalize(Locale.getDefault())
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
        return evolutionChain.size
    }
}
