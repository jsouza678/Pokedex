package souza.home.com.pokedexapp.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.poke_item_view.view.*
import souza.home.com.pokedexapp.R
import souza.home.com.extensions.loadUrl
import souza.home.com.pokedexapp.data.pokedex.remote.model.Poke
import kotlin.random.Random


class HomePokedexAdapter(private val pokes: MutableList<Poke>?, private val context: Context) : RecyclerView.Adapter<HomePokedexAdapter.ViewHolder>() {

    var onItemClick: ((Poke) -> Unit)? = null
    private val imageResourceUrl = "https://pokeres.bastionbot.org/images/pokemon/"

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
        return pokes!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemBind(pokes!![position])

    }

    private fun setColor():  Int{

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
        private val pokeName: TextView = itemView.tv_name_poke
        private val pokeImage: ImageView = itemView.iv_poke_sprite
        private val pokeId: TextView = itemView.tv_id_poke
        private val pokeCv: CardView = itemView.cv_poke_item_home
        private var formatedNumber: String = ""
        private var pokemonId : String = ""

        fun itemBind(pokes: Poke){
            pokeName.text = pokes.name
            pokemonId = pokes.url//.substringAfter("n/").substringBefore('/')
            formatedNumber= "%03d".format(Integer.parseInt(pokemonId))
            pokeId.text = context.resources.getString(R.string.placeholder_tv_id, formatedNumber)
            pokeImage.loadUrl("$imageResourceUrl$pokemonId.png")
            pokeCv.setCardBackgroundColor(ContextCompat.getColor(context, setColor()))
        }

    init{
        itemView.setOnClickListener{
            onItemClick?.invoke(pokes!![adapterPosition])
            }
        }

    }

}