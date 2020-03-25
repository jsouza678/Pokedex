package souza.home.com.pokedexapp.ui.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import souza.home.com.pokedexapp.R


/**
 * A simple [Fragment] subclass.
 */
class DetailsPokedexFragment : Fragment() {


    private lateinit var tvName : TextView
    private lateinit var tvHp : TextView
    private lateinit var tvAttack : TextView
    private lateinit var tvDeffense : TextView
    private lateinit var tvSpecialAttack: TextView
    private lateinit var tvSpecialDefense : TextView
    private lateinit var tvSpeed : TextView
    private lateinit var lvAbilities : ListView
    private lateinit var lvTypes : ListView
    private lateinit var lvChain : ListView
    private lateinit var spVariations : Spinner
    private lateinit var evolutionArray: ArrayList<String>
    private lateinit var varietiesArray: ArrayList<String>

    private val viewModel: DetailsPokedexViewModel by lazy{
        ViewModelProviders.of(this).get(DetailsPokedexViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)
        val poke: String = "25"

        evolutionArray = ArrayList()
        varietiesArray = ArrayList()
        tvName = view.findViewById(R.id.tv_detail_name)
        lvTypes = view.findViewById(R.id.lv_types)
        lvAbilities = view.findViewById(R.id.lv_abilities)
        lvChain = view.findViewById(R.id.lv_chain)
        spVariations = view.findViewById(R.id.spinner_variations)
        tvHp = view.findViewById(R.id.tv_poke_hp)
        tvAttack = view.findViewById(R.id.tv_poke_attack)
        tvDeffense = view.findViewById(R.id.tv_poke_deffense)
        tvSpecialAttack = view.findViewById(R.id.tv_poke_special_attack)
        tvSpecialDefense = view.findViewById(R.id.tv_poke_special_deffense)
        tvSpeed = view.findViewById(R.id.tv_poke_speed)


        //viewModel.getStats(poke, view.context, tvName, lvStats, tvHp, tvAttack, tvDeffense, tvSpecialAttack, tvSpecialDefense, tvSpeed)
        viewModel.getStats(poke, view.context, tvName, tvHp, tvAttack, tvDeffense, tvSpecialAttack, tvSpecialDefense, tvSpeed, lvTypes, lvAbilities)
        viewModel.getChainEvolution(poke, view.context, evolutionArray, lvChain)
        viewModel.getVarieties(poke, view.context, varietiesArray, spVariations, evolutionArray, lvChain, tvName, tvHp, tvAttack, tvDeffense, tvSpecialAttack, tvSpecialDefense, tvSpeed, lvTypes, lvAbilities)

        return view
    }



}
