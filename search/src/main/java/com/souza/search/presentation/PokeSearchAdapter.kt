package com.souza.search.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.souza.extensions.loadImageUrlAndPaletteColorToCardView
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.pokecatalog.utils.Constants
import com.souza.search.R
import com.souza.search.utils.Constants.Companion.BASTION_POKE_IMAGE_BASE_URL
import com.souza.search.utils.Constants.Companion.DEFAULT_IMAGE_FORMAT_BASTION
import com.souza.search.utils.Constants.Companion.EMPTY_STRING
import java.util.Locale
import kotlinx.android.synthetic.main.recycler_poke_item_view_search.view.card_view_poke_item
import kotlinx.android.synthetic.main.recycler_poke_item_view_search.view.image_view_poke_sprite_recycler
import kotlinx.android.synthetic.main.recycler_poke_item_view_search.view.text_view_id_poke_recycler
import kotlinx.android.synthetic.main.recycler_poke_item_view_search.view.text_view_name_poke_recycler

class PokeSearchAdapter :
    RecyclerView.Adapter<PokeSearchAdapter.ViewHolder>() {

    private val pokemons = mutableListOf<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null
    private val imageResourceUrl = BASTION_POKE_IMAGE_BASE_URL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_poke_item_view_search,
                parent,
                false)
        return ViewHolder(view)
    }

    fun submitList(newData: MutableList<Pokemon>) {
        if (pokemons.isNotEmpty()) {
            pokemons.clear()
        }
        pokemons.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        pokemons[position].let { holder.itemBind(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokeName: TextView = itemView.text_view_name_poke_recycler
        private val pokeImage: ImageView = itemView.image_view_poke_sprite_recycler
        private val pokeId: TextView = itemView.text_view_id_poke_recycler
        private var formattedNumber: String = EMPTY_STRING
        private var pokemonId: Int = 0
        private val pokeCardView: MaterialCardView = itemView.card_view_poke_item

        @SuppressLint("SetTextI18n")
        @ExperimentalStdlibApi
        fun itemBind(pokes: Pokemon) {
            pokeName.text = pokes.name.capitalize(Locale.getDefault())
            pokemonId = pokes.id
            formattedNumber = Constants.FORMAT_ID_POKE_DISPLAY.format(pokemonId)
            pokeId.text = "#$formattedNumber"
            pokeImage
                .loadImageUrlAndPaletteColorToCardView(
                    "$imageResourceUrl$pokemonId$DEFAULT_IMAGE_FORMAT_BASTION",
                    pokeCardView)
        }

        init {
            itemView.setOnClickListener {
                pokemons[adapterPosition].let { item -> onItemClick?.invoke(item) }
            }
        }
    }
}
