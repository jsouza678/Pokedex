package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.model.ability.AbilitiesRoot
import java.util.Locale

class OthersAbilityAdapter(
    private val context: Context,
    private val pokeAbilities: MutableList<AbilitiesRoot>
) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun submitList(newData: MutableList<AbilitiesRoot>) {
        if (pokeAbilities.isNotEmpty()) {
            pokeAbilities.clear()
        }
        pokeAbilities.addAll(newData)
        this.notifyDataSetChanged()
    }

    @ExperimentalStdlibApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val abilityItem = pokeAbilities[position]
        val rowView = inflater.inflate(R.layout.list_item_row, parent, false)
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
