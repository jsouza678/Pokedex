package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.TypeRoot
import souza.home.com.pokedexapp.data.pokedex.remote.response.TypeResponse
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.others.types.TypesDialog
import souza.home.com.pokedexapp.utils.cropAbilityUrl
import souza.home.com.pokedexapp.utils.cropTypeUrl

class OthersFragment(private val pokemonId: Int) : Fragment() {

    private val viewModel by viewModel<OthersViewModel> { parametersOf(pokemonId) }
    private lateinit var abilitiesListView: ListView
    private lateinit var typesListView: ListView
    private val adapterTypes by inject<OthersTypeAdapter> { parametersOf(typeRootArray) }
    private val adapterAbilities by inject<OthersAbilityAdapter> { parametersOf(abilitiesArray) }
    private var typeRootArray: MutableList<TypeRoot> = mutableListOf()
    private var abilitiesArray: MutableList<AbilitiesRoot> = mutableListOf()
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_others, container, false)
        bindViews(view)

        setupTypesList()
        setupAbilitiesList()
        initObserver()

        return view
    }

    private fun bindViews(view: View) {
        typesListView = view.findViewById(R.id.list_view_types_others)
        abilitiesListView = view.findViewById(R.id.list_view_abilities_others)
    }

    private fun initObserver() {
        viewModel.apply {
            this.updatePropertiesOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    it.types?.let { itemTypes -> adapterTypes.submitList(itemTypes) }
                    it.abilities?.let { itemAbilities -> adapterAbilities.submitList(itemAbilities) }
                }
            })
        }
    }

    private fun initObserverAbilitiesDetails() {
        viewModel.apply {
            this.statusAb.observe(viewLifecycleOwner, Observer {
                if (it == AbilityPokedexStatus.DONE) {
                    setupDialog(viewModel.abilityDesc.value)
                    openDialog()
                    this.statusAb.removeObservers(viewLifecycleOwner)
                }
            })
        }
    }

    private fun initObserverPokeInTypes() {
        viewModel.apply {
            this.statusAb.observe(viewLifecycleOwner, Observer {
                if (it == AbilityPokedexStatus.DONE) {
                    showPokesInTypesDialog(viewModel.pokeTypes.value!!)
                    this.statusAb.removeObservers(viewLifecycleOwner) }
            })
        }
    }

    private fun setupDialog(message: String?) {
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context).setTitle(getString(R.string.ability_description_dialog_pokemon_in_types)).setPositiveButton(getString(R.string.button_text_dismiss), null)
        materialAlertDialogBuilder.setMessage(message)
    }

    private fun openDialog() {
        materialAlertDialogBuilder.show()
    }

    private fun setupTypesList() {
        typesListView.adapter = adapterTypes
        typesListView.setOnItemClickListener { parent, view, position, id ->
            val elementId = adapterTypes.getItem(position).type?.url // The item that was clicked
            val typeId = elementId?.let { cropTypeUrl(it) }?.let { Integer.parseInt(it) }
            elementId?.let { typeId?.let { idType -> viewModel.getPokesInTypes(idType) } }

            initObserverPokeInTypes()
        }
    }

    private fun setupAbilitiesList() {
        abilitiesListView.adapter = adapterAbilities
        abilitiesListView.setOnItemClickListener { parent, view, position, id ->
            val chosenPokemonId = adapterAbilities.getItem(position).ability?.url // The item that was clicked
            val abilityId = chosenPokemonId?.let { cropAbilityUrl(it) }?.let { Integer.parseInt(it) }
            abilityId?.let { viewModel.getAbilityDesc(it) }

            initObserverAbilitiesDetails()
        }
    }

    private fun showPokesInTypesDialog(list: MutableList<TypeResponse>) {
        val pokeTypesDialog: TypesDialog = TypesDialog(list)

        fragmentManager?.let { pokeTypesDialog.show(it, getString(R.string.fragment_tag_pokemon_in_types_dialog)) }
    }
}
