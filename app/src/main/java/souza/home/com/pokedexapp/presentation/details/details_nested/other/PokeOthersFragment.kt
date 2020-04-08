package souza.home.com.pokedexapp.presentation.details.details_nested.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.PokeAbilities
import souza.home.com.pokedexapp.data.pokedex.remote.model.types.PokeTypes
import souza.home.com.pokedexapp.data.pokedex.remote.model.types.PokemonNested
import souza.home.com.pokedexapp.presentation.details.details_nested.PokedexViewModelFactory
import souza.home.com.pokedexapp.presentation.details.types.PokeTypesDialog


class PokeOthersFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeOthersViewModel
    private lateinit var poke: String
    private lateinit var lvAbilities : ListView
    private lateinit var lvTypes : ListView
    private lateinit var adapterTypes: CustomTypeAdapter
    private lateinit var adapterAbilities: CustomAbilityAdapter
    private lateinit var typesArray: MutableList<PokeTypes>
    private lateinit var abilitiesArray: MutableList<PokeAbilities>
    private lateinit var material : MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_others, container, false)
        poke = pokemon

        lvTypes = view.findViewById(R.id.lv_types)
        lvAbilities = view.findViewById(R.id.lv_abilities)
        typesArray = ArrayList()
        abilitiesArray = ArrayList()

        adapterTypes =
            CustomTypeAdapter(
                view.context,
                typesArray
            )
        adapterAbilities =
            CustomAbilityAdapter(
                view.context,
                abilitiesArray
            )


        viewModel = ViewModelProviders.of(activity!!,
            PokedexViewModelFactory(
                pokemon,
                activity!!.application
            )
        )
            .get(PokeOthersViewModel::class.java)

        initType()
        initAbilities()
        initObservers()

        return view
    }


    private fun initObservers(){

        viewModel.apply {

            this.status.observe(viewLifecycleOwner, Observer {
                if (it == DetailsPokedexStatus.DONE) {
                    adapterTypes.submitList(viewModel.other.value?.types!!)
                    adapterAbilities.submitList(viewModel.other.value?.abilities!!)
                }
            })}
    }

    private fun initOutsideObserverAbilities(){

            viewModel.apply {
                this.statusAb.observe(viewLifecycleOwner, Observer {
                    if (it == AbilityPokedexStatus.DONE) {
                        openDialog(viewModel.abilityDesc.value)
                        this.statusAb.removeObservers(viewLifecycleOwner)
                    }
                })
            }
    }

    private fun initOutsideObserverTypes(){

            viewModel.apply {
                this.statusAb.observe(viewLifecycleOwner, Observer {
                  if (it == AbilityPokedexStatus.DONE){ //Toast.makeText(context, "${viewModel.pokeTypes.value}", Toast.LENGTH_SHORT).show()
                    showCustomTypesDialog(viewModel.pokeTypes.value!!)
                    this.statusAb.removeObservers(viewLifecycleOwner)}
                })
            }
    }

    private fun openDialog(message: String?){
        material = MaterialAlertDialogBuilder(context).setTitle("Ability Description").setPositiveButton("Dismiss", null)
        material.setMessage(message)
        material.show()
    }

    private fun initType(){
        lvTypes.adapter = adapterTypes

        lvTypes.setOnItemClickListener { parent, view, position, id ->
            var elementId = adapterTypes.getItem(position).type.url// The item that was clicked
            elementId = elementId?.substringAfterLast("e/")?.substringBeforeLast("/")

            viewModel.getPokesInTypes(elementId!!)

            initOutsideObserverTypes()
        }
    }

    private fun initAbilities(){
        lvAbilities.adapter = adapterAbilities

        lvAbilities.setOnItemClickListener { parent, view, position, id ->
            var elementId =
                adapterAbilities.getItem(position).ability.url// The item that was clicked
            elementId = elementId.substringAfterLast("y/").substringBeforeLast("/")

            viewModel.getAbilityDesc(elementId)

            initOutsideObserverAbilities()

        }
    }

    private fun showCustomTypesDialog(list: MutableList<PokemonNested>){
        val pokeTypesDialog: PokeTypesDialog = PokeTypesDialog(list)

        pokeTypesDialog.show(fragmentManager!!, "my_fragment")
    }
}