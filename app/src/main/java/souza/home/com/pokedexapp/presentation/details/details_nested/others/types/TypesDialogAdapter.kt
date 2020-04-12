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
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.NestedType


class TypesDialogAdapter(private val pokes: MutableList<NestedType>?, private val context: Context) : RecyclerView.Adapter<TypesDialogAdapter.ViewHolder>() {

    var onItemClick: ((NestedType) -> Unit)? = null
    private val imageResourceUrl = "https://pokeres.bastionbot.org/images/pokemon/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.poke_item_view, parent, false)
        return ViewHolder(view)
    }

    fun submitList(newData: MutableList<NestedType>) {
        if (pokes!!.isNotEmpty()) {
            pokes.clear()
        }
        pokes.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pokes!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemBind(pokes!![position])

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokeName: TextView = itemView.text_view_name_poke_recycler
        private val pokeImage: ImageView = itemView.image_view_poke_sprite_recycler
        private val pokeId: TextView = itemView.text_view_id_poke_recycler
        private var formatedNumber: String = ""
        private var pokemonId : String = ""

        fun itemBind(pokes: NestedType){
            pokeName.text = pokes.pokemon.name
            pokemonId = pokes.pokemon._id.substringAfter("n/").substringBefore('/')
            formatedNumber= "%03d".format(Integer.parseInt(pokemonId))
            pokeId.text = context.resources.getString(R.string.text_view_placeholder_hash, formatedNumber)
            pokeImage.loadUrl("$imageResourceUrl$pokemonId.png")
        }

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(pokes!![adapterPosition])
            }
        }

    }

}