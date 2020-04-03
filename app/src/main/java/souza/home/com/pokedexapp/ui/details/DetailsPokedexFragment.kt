package souza.home.com.pokedexapp.ui.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty
import android.animation.ValueAnimator
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.network.model.varieties.PokeVarieties
import souza.home.com.pokedexapp.ui.home.HomePokedexStatus

class DetailsPokedexFragment(var poke: String) : Fragment() {

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
    private lateinit var evolutionArray: MutableList<PokeEvolution>
    private lateinit var varietiesArray: MutableList<PokeVarieties>
    private lateinit var pokemonsArray: MutableList<PokeVarieties>
    private lateinit var viewModel: DetailsPokedexViewModel
    private lateinit var pokemon: String
    private lateinit var adapterChain : CustomChainAdapter
    private lateinit var adapterSpinner : CustomSpinnerAdapter
    private var pokePath : String = ""
    private var urlChain : String = ""
    private var spinnerSelected: Int = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)
        this.pokemon = poke

        viewModel = ViewModelProviders.of(this, DetailsPokedexViewModelFactory(pokemon, activity!!.application))
            .get(DetailsPokedexViewModel::class.java)



        evolutionArray = ArrayList()
        varietiesArray = ArrayList()
        pokemonsArray = ArrayList()

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


        adapterSpinner = CustomSpinnerAdapter(view.context, pokemonsArray)
        adapterChain = CustomChainAdapter(view.context, evolutionArray)
        initSpinner()
        initChainEvolution()
        initObservers()


        //viewModel.getChainEvolution(poke, view.context, evolutionArray, lvChain)
        //viewModel.getVarieties(poke, view.context, varietiesArray, spVariations, evolutionArray, lvChain, tvName, tvHp, tvAttack, tvDeffense, tvSpecialAttack, tvSpecialDefense, tvSpeed, lvTypes, lvAbilities)

        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.stats.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    initStats(it)

                }
            })

            this.chain.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    adapterChain.submitList(it)

                }
            })

            this.varieties.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    adapterSpinner.submitList(it.varieties)

                    pokemonsArray = it.varieties

                    //adapterChain.submitList(it)

                }
            })

            this.status.observe(viewLifecycleOwner, Observer {
                /*when(it){
                    HomePokedexStatus.EMPTY -> Toast.makeText(context, "No variations", Toast.LENGTH_SHORT).show()
                    else-> Toast.makeText(context, "Show", Toast.LENGTH_SHORT).show()
                }*/
            })

        }

    }

    private fun initChainEvolution(){
        lvChain.adapter = adapterChain
    }

    /*

    private fun initType(){
        val adapterTypes = CustomTypeAdapter(context, item?.types!!)
        lvTypes.adapter = adapterTypes
    }

    private fun initAbilities(){
        val adapterAbilities = CustomAbilityAdapter(context, item?.abilities!!)

        lvAbilities.adapter = adapterAbilities
        textViewName.text = item?.name?.capitalize()
    }
*/


    private fun initStats(item: PokemonProperty){
        animateStats(Integer.valueOf(item.stats[5].base_stat), tvHp)
        animateStats(Integer.valueOf(item.stats[4].base_stat), tvAttack)
        animateStats(Integer.valueOf(item.stats[3].base_stat), tvDeffense)
        animateStats(Integer.valueOf(item.stats[2].base_stat), tvSpecialAttack)
        animateStats(Integer.valueOf(item.stats[1].base_stat), tvSpecialDefense)
        animateStats(Integer.valueOf(item.stats[0].base_stat), tvSpeed)
}

    private fun animateStats(item: Int, tv: TextView){
        val animator = ValueAnimator()
        animator.setObjectValues(0, item)// here you set the range, from 0 to "count" value
        animator.addUpdateListener {
                animation -> tv.text = animation.animatedValue.toString()
        }
        animator.duration = 600 // here you set the duration of the anim
        animator.start()
    }

    private fun initSpinner() {
        var check: Int = 0

        spVariations.adapter = adapterSpinner

        spVariations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spinnerSelected = position-1
                when(position){
                    0 ->  {

                    }
                    else-> {
                            urlChain = pokemonsArray[spinnerSelected].pokemon.url

                            pokePath = urlChain.substringAfterLast("n/").substringBeforeLast("/")

                            //Toast.makeText(context, pokePath, Toast.LENGTH_SHORT).show()

                            if(pokePath.trim() == pokemon.trim()){
                                Toast.makeText(context, "Same poke", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "ReloadScreen", Toast.LENGTH_SHORT).show()
                                //reload the fragment
                                //fragmentManager!!.beginTransaction().replace(R.id.nav_host_fragment, DetailsPokedexFragment(pokePath)).commit()
                            }
                    }
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                //faz nada
                //getChainEvolution(pokePath!!, context, evolutionArray, listViewChain)
            }

        }



        /*var urlChain = items?.evolution_chain?.url

            // calling chain evoltuion at selection
            var pokePath = urlChain?.substringAfterLast("n/")
            //Toast.makeText(context, pokePath, Toast.LENGTH_SHORT).show()

            getChainEvolution(pokePath!!, context, evolutionArray, listViewChain)*/

    }
}

