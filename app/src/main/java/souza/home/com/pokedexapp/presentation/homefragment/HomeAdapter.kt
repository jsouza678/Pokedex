package souza.home.com.pokedexapp.presentation.homefragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random
import kotlinx.android.synthetic.main.recycler_poke_item_view.view.*
import souza.home.com.extensions.loadUrl
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.utils.Constants.Companion.BASTION_POKE_IMAGE_BASE_URL
import souza.home.com.pokedexapp.utils.Constants.Companion.DEFAULT_IMAGE_FORMAT_BASTION
import souza.home.com.pokedexapp.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY

class HomeAdapter(private val pokes: MutableList<Poke>?, private val context: Context) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var onItemClick: ((Poke) -> Unit)? = null
    private val imageResourceUrl = BASTION_POKE_IMAGE_BASE_URL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_poke_item_view, parent, false)
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
        return pokes!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemBind(pokes!![position])
    }

    private fun setColor(): Int {

        val random: Random = Random

        val choosed = random.nextInt(9)

        val color = arrayOf(
            R.color.poke_red_mid_translucent,
            R.color.poke_green_mid_translucent,
            R.color.poke_blue_mid_translucent,
            R.color.poke_gray_mid_translucent,
            R.color.poke_black_mid_translucent,
            R.color.poke_yellow_mid_translucent,
            R.color.poke_white_mid_translucent,
            R.color.poke_purple_mid_translucent,
            R.color.poke_pink_mid_translucent,
            R.color.poke_brown_mid_translucent)

        return color[choosed]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokeName: TextView = itemView.text_view_name_poke_recycler
        private val pokeImage: ImageView = itemView.image_view_poke_sprite_recycler
        private val pokeId: TextView = itemView.text_view_id_poke_recycler
        private val pokeCv: CardView = itemView.card_view_poke_item
        private var formatedNumber: String = ""
        private var pokemonId: Int = 0

        fun itemBind(pokes: Poke) {
            pokeName.text = pokes.name
            pokemonId = pokes._id
            formatedNumber = FORMAT_ID_POKE_DISPLAY.format(pokemonId)
            pokeId.text = context.resources.getString(R.string.text_view_placeholder_hash, formatedNumber)
            pokeImage.loadUrl("$imageResourceUrl$pokemonId$DEFAULT_IMAGE_FORMAT_BASTION")
            pokeCv.setCardBackgroundColor(ContextCompat.getColor(context, setColor()))
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(pokes!![adapterPosition])
            }
        }
    }
}
