package souza.home.com.pokedexapp.presentation.details.details_nested.others

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
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesMain
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.Types
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.NestedType
import souza.home.com.pokedexapp.presentation.details.details_nested.NestedViewModelFactory
import souza.home.com.pokedexapp.presentation.details.details_nested.others.types.TypesDialog
import souza.home.com.pokedexapp.utils.cropAbilityUrl
import souza.home.com.pokedexapp.utils.cropTypeUrl


class OthersFragment(var pokemon: Int) : Fragment() {

    private lateinit var viewModel: PokeOthersViewModel
    private lateinit var poke: String
    private lateinit var lvAbilities : ListView
    private lateinit var lvTypes : ListView
    private lateinit var adapterTypes: TypeAdapter
    private lateinit var adapterAbilities: AbilityAdapter
    private lateinit var typesArray: MutableList<Types>
    private lateinit var abilitiesArray: MutableList<AbilitiesMain>
    private lateinit var material : MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_others, container, false)
        lvTypes = view.findViewById(R.id.lv_types)
        lvAbilities = view.findViewById(R.id.lv_abilities)
        typesArray = ArrayList()
        abilitiesArray = ArrayList()

        initializeAdapters(view)

        viewModel = ViewModelProviders.of(activity!!,
            NestedViewModelFactory(
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

    private fun initializeAdapters(view: View){
        adapterTypes =
            TypeAdapter(
                view.context,
                typesArray
            )

        adapterAbilities =
            AbilityAdapter(
                view.context,
                abilitiesArray
            )
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
            val elementId = adapterTypes.getItem(position).type.url// The item that was clicked

            val typeId = elementId?.let { cropTypeUrl(it) }?.let { Integer.parseInt(it) }

            elementId?.let { typeId?.let { idType -> viewModel.getPokesInTypes(idType) } }

            initOutsideObserverTypes()
        }
    }

    private fun initAbilities(){
        lvAbilities.adapter = adapterAbilities

        lvAbilities.setOnItemClickListener { parent, view, position, id ->
            val elementId = adapterAbilities.getItem(position).ability.url// The item that was clicked

            val abilityId = Integer.parseInt( cropAbilityUrl(elementId))

            viewModel.getAbilityDesc(abilityId)

            initOutsideObserverAbilities()
        }
    }

    private fun showCustomTypesDialog(list: MutableList<NestedType>){
        val pokeTypesDialog: TypesDialog = TypesDialog(list)

        fragmentManager?.let { pokeTypesDialog.show(it, "my_fragment") }
    }
}