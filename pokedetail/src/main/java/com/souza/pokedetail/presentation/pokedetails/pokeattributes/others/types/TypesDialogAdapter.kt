package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others.types

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.souza.extensions.loadImageUrlAndPaletteColorToCardView
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.response.TypeResponse
import com.souza.pokedetail.utils.Constants.Companion.BASTION_POKE_IMAGE_BASE_URL
import com.souza.pokedetail.utils.Constants.Companion.DEFAULT_IMAGE_FORMAT_BASTION
import com.souza.pokedetail.utils.Constants.Companion.EMPTY_STRING
import com.souza.pokedetail.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY
import com.souza.pokedetail.utils.cropPokeUrl
import kotlinx.android.synthetic.main.recycler_poke_item_view_detail.view.*

class TypesDialogAdapter(
    private val pokeTypes: MutableList<TypeResponse>?,
    private val context: Context
) : RecyclerView.Adapter<TypesDialogAdapter.ViewHolder>() {

    var onItemClick: ((TypeResponse) -> Unit)? = null
    private val imageResourceUrl = BASTION_POKE_IMAGE_BASE_URL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_poke_item_view_detail, parent, false)
        return ViewHolder(view)
    }

    fun submitList(newData: MutableList<TypeResponse>) {
        if (pokeTypes != null) {
            if (pokeTypes.isNotEmpty()) {
                pokeTypes.clear()
            }
        }
        pokeTypes?.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pokeTypes!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        pokeTypes?.get(position)?.let { holder.itemBind(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokeNameTextView: TextView = itemView.text_view_name_poke_recycler
        private val pokeImageImageView: ImageView = itemView.image_view_poke_sprite_recycler
        private val pokeIdTextView: TextView = itemView.text_view_id_poke_recycler
        private var formattedNumber: String = EMPTY_STRING
        private var pokemonId: String = EMPTY_STRING
        private val pokemonCv: MaterialCardView = itemView.card_view_poke_item

        fun itemBind(pokes: TypeResponse) {
            pokeNameTextView.text = pokes.pokemon?.name
            pokemonId = cropPokeUrl(pokes.pokemon?.id!!)
            formattedNumber = FORMAT_ID_POKE_DISPLAY.format(Integer.parseInt(pokemonId))
            pokeIdTextView.text = context.resources.getString(R.string.text_view_placeholder_hash, formattedNumber)
            pokeImageImageView.loadImageUrlAndPaletteColorToCardView("$imageResourceUrl$pokemonId$DEFAULT_IMAGE_FORMAT_BASTION", pokemonCv)
        }

        init {
            itemView.setOnClickListener {
                pokeTypes?.get(adapterPosition)?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }
    }
}
