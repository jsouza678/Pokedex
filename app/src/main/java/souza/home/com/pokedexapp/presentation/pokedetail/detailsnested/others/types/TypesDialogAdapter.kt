package souza.home.com.pokedexapp.presentation.pokedetail.detailsnested.others.types

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_poke_item_view.view.*
import souza.home.com.extensions.loadUrl
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.response.NestedTypeResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.BASTION_POKE_IMAGE_BASE_URL
import souza.home.com.pokedexapp.utils.Constants.Companion.DEFAULT_IMAGE_FORMAT_BASTION
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY
import souza.home.com.pokedexapp.utils.cropPokeUrl

class TypesDialogAdapter(private val pokes: MutableList<NestedTypeResponse>?, private val context: Context) : RecyclerView.Adapter<TypesDialogAdapter.ViewHolder>() {

    var onItemClick: ((NestedTypeResponse) -> Unit)? = null
    private val imageResourceUrl = BASTION_POKE_IMAGE_BASE_URL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_poke_item_view, parent, false)
        return ViewHolder(view)
    }

    fun submitList(newData: MutableList<NestedTypeResponse>) {
        if (pokes != null) {
            if (pokes.isNotEmpty()) {
                pokes.clear()
            }
        }
        pokes?.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pokes!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        pokes?.get(position)?.let { holder.itemBind(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokeName: TextView = itemView.text_view_name_poke_recycler
        private val pokeImage: ImageView = itemView.image_view_poke_sprite_recycler
        private val pokeId: TextView = itemView.text_view_id_poke_recycler
        private var formatedNumber: String = EMPTY_STRING
        private var pokemonId: String = EMPTY_STRING

        fun itemBind(pokes: NestedTypeResponse) {
            pokeName.text = pokes.pokemon.name
            pokemonId = cropPokeUrl(pokes.pokemon._id)
            formatedNumber = FORMAT_ID_POKE_DISPLAY.format(Integer.parseInt(pokemonId))
            pokeId.text = context.resources.getString(R.string.text_view_placeholder_hash, formatedNumber)
            pokeImage.loadUrl("$imageResourceUrl$pokemonId$DEFAULT_IMAGE_FORMAT_BASTION")
        }

        init {
            itemView.setOnClickListener {
                pokes?.get(adapterPosition)?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }
    }
}
