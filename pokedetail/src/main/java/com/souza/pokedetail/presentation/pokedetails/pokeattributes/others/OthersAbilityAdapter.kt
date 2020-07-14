package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.model.ability.AbilitiesRoot
import java.util.Locale

class OthersAbilityAdapter
    : BaseAdapter() {

    private val pokeAbilities = mutableListOf<AbilitiesRoot>()

    fun submitList(newData: MutableList<AbilitiesRoot>) {
        if (pokeAbilities.isNotEmpty()) {
            pokeAbilities.clear()
        }
        pokeAbilities.addAll(newData)
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

        val abilityItem = pokeAbilities[position]

        rowView.findViewById<TextView>(R.id.text_view_item_list).text = abilityItem.ability?.name?.capitalize(
            Locale.getDefault())

        rowView.tag = position
        return rowView
    }

    override fun getItem(position: Int): AbilitiesRoot {
        return pokeAbilities[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return pokeAbilities.size
    }
}
