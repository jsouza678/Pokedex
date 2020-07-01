package com.souza.pokecatalog.presentation.pokecatalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.souza.pokecatalog.R
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.pokecatalog.utils.Constants.Companion.ABSOLUTE_ZERO
import com.souza.pokecatalog.utils.Constants.Companion.BASTION_POKE_IMAGE_BASE_URL
import com.souza.pokecatalog.utils.Constants.Companion.DEFAULT_IMAGE_FORMAT_BASTION
import com.souza.pokecatalog.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY
import com.souza.pokecatalog.utils.loadImageUrlAndCard
import com.souza.pokedetail.utils.Constants.Companion.EMPTY_STRING
import java.util.Locale
import kotlinx.android.synthetic.main.recycler_poke_item_view.view.card_view_poke_item
import kotlinx.android.synthetic.main.recycler_poke_item_view.view.image_view_poke_sprite_recycler
import kotlinx.android.synthetic.main.recycler_poke_item_view.view.text_view_id_poke_recycler
import kotlinx.android.synthetic.main.recycler_poke_item_view.view.text_view_name_poke_recycler

class PokeCatalogAdapter() : RecyclerView.Adapter<PokeCatalogAdapter.ViewHolder>() {

    private val pokes = mutableListOf<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null
    private val imageResourceUrl = BASTION_POKE_IMAGE_BASE_URL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_poke_item_view, parent, false)
        return ViewHolder(view)
    }

    fun submitList(newData: MutableList<Pokemon>) {
        if (pokes.isNotEmpty()) {
            pokes.clear()
        }
        pokes.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pokes.size
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBind(pokes[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokeName: TextView = itemView.text_view_name_poke_recycler
        private val pokeImage: ImageView = itemView.image_view_poke_sprite_recycler
        private val pokeId: TextView = itemView.text_view_id_poke_recycler
        private val pokeCv: MaterialCardView = itemView.card_view_poke_item
        private var formattedNumber: String = EMPTY_STRING
        private var pokemonId: Int = ABSOLUTE_ZERO

        @ExperimentalStdlibApi
        fun itemBind(pokes: Pokemon) {
            pokeName.text = pokes.name.capitalize(Locale.getDefault())
            pokemonId = pokes.id
            formattedNumber = FORMAT_ID_POKE_DISPLAY.format(pokemonId)
            pokeId.text = "#$formattedNumber"
            pokeImage.loadImageUrlAndCard("$imageResourceUrl$pokemonId$DEFAULT_IMAGE_FORMAT_BASTION", pokeCv)
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(pokes[adapterPosition])
            }
        }
    }
}
