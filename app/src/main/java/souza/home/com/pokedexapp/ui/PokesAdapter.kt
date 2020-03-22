package souza.home.com.pokedexapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.poke_item_view.view.*
import souza.home.com.pokedexapp.network.PokeProperty


class PokesAdapter(private val pokes: MutableList<PokeProperty>?, private val context: Context) : RecyclerView.Adapter<PokesAdapter.ViewHolder>() {

    var onItemClick: ((PokeProperty) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokesAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(souza.home.com.pokedexapp.R.layout.poke_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokes!!.size
    }

    override fun onBindViewHolder(holder: PokesAdapter.ViewHolder, position: Int) {
        var name = pokes?.get(position)?.name

        holder.poke_name.text = name

    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poke_name = itemView.tv_name_poke


    init{
        itemView.setOnClickListener{
            onItemClick?.invoke(pokes!![adapterPosition])
        }
    }

    }


}