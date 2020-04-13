package souza.home.com.pokedexapp.presentation.details.details_nested.others.types

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.poke_item_view.view.*
import souza.home.com.pokedexapp.R
import souza.home.com.extensions.loadUrl
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.utils.Constants.Companion.BASTION_POKE_IMAGE_BASE_URL
import souza.home.com.pokedexapp.utils.Constants.Companion.DEFAULT_IMAGE_FORMAT_BASTION
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY


class SearchDialogAdapter(private val pokes: MutableList<Poke>?, private val context: Context?) : RecyclerView.Adapter<SearchDialogAdapter.ViewHolder>() {

    var onItemClick: ((Poke) -> Unit)? = null
    private val imageResourceUrl = BASTION_POKE_IMAGE_BASE_URL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.poke_item_view, parent, false)
        return ViewHolder(view)
    }

    fun submitList(newData: MutableList<Poke>) {
        if (pokes != null) {
            if (pokes.isNotEmpty()) {
                pokes.clear()
            }
        }
        pokes?.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if(pokes != null) {
            return pokes.size
        }else{
            return 0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        pokes?.get(position)?.let { holder.itemBind(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokeName: TextView = itemView.text_view_name_poke_recycler
        private val pokeImage: ImageView = itemView.image_view_poke_sprite_recycler
        private val pokeId: TextView = itemView.text_view_id_poke_recycler
        private var formattedNumber: String = EMPTY_STRING
        private var pokemonId : Int = 0

        fun itemBind(pokes: Poke){
            pokeName.text = pokes.name
            pokemonId = pokes._id
            formattedNumber= FORMAT_ID_POKE_DISPLAY.format(pokemonId)
            pokeId.text = context?.resources?.getString(R.string.text_view_placeholder_hash, formattedNumber)
            pokeImage.loadUrl("$imageResourceUrl$pokemonId$DEFAULT_IMAGE_FORMAT_BASTION")
        }

        init{
            itemView.setOnClickListener{
                pokes?.get(adapterPosition)?.let { item -> onItemClick?.invoke(item) }
            }
        }
    }
}