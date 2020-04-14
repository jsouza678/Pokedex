package souza.home.com.pokedexapp.presentation.details.details_nested.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesMain
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.NestedType
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.Types
import souza.home.com.pokedexapp.presentation.details.details_nested.NestedViewModelFactory
import souza.home.com.pokedexapp.presentation.details.details_nested.others.types.TypesDialog
import souza.home.com.pokedexapp.utils.cropAbilityUrl
import souza.home.com.pokedexapp.utils.cropTypeUrl

class OthersFragment(var pokemon: Int) : Fragment() {

    private lateinit var viewModel: OthersViewModel
    private lateinit var lvAbilities: ListView
    private lateinit var lvTypes: ListView
    private lateinit var adapterTypes: OthersTypeAdapter
    private lateinit var adapterAbilities: OthersAbilityAdapter
    private lateinit var typesArray: MutableList<Types>
    private lateinit var abilitiesArray: MutableList<AbilitiesMain>
    private lateinit var material: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_others, container, false)
        bindViews(view)
        typesArray = ArrayList()
        abilitiesArray = ArrayList()

        initializeAdapters(view)

        viewModel = activity?.let {
            ViewModelProviders.of(
                it,
                NestedViewModelFactory(
                    pokemon,
                    activity!!.application
                )
            )
                .get(OthersViewModel::class.java)
        }!!

        initType()
        initAbilities()
        initObservers()

        return view
    }

    private fun bindViews(view: View) {
        lvTypes = view.findViewById(R.id.list_view_types_others)
        lvAbilities = view.findViewById(R.id.list_view_abilities_others)
    }

    private fun initializeAdapters(view: View) {
        adapterTypes =
            OthersTypeAdapter(
                view.context,
                typesArray
            )

        adapterAbilities =
            OthersAbilityAdapter(
                view.context,
                abilitiesArray
            )
    }

    private fun initObservers() {
        viewModel.apply {
            this.internetStatus.observe(viewLifecycleOwner, Observer {
                if (it == true) {
                    initLoadData()
                } else {
                    view?.let { view -> Snackbar.make(view, getString(R.string.no_internet_connection), BaseTransientBottomBar.LENGTH_SHORT).show() }
                }
            })
        }
    }

    private fun initLoadData() {
        viewModel.apply {
            this.updatePropertiesOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    it.types?.let { itemTypes -> adapterTypes.submitList(itemTypes) }
                    it.abilities?.let { itemAbilities -> adapterAbilities.submitList(itemAbilities) }
                }
            })
        }
    }

    private fun initOutsideObserverAbilities() {
        viewModel.apply {
            this.statusAb.observe(viewLifecycleOwner, Observer {
                if (it == AbilityPokedexStatus.DONE) {
                    openDialog(viewModel.abilityDesc.value)
                    this.statusAb.removeObservers(viewLifecycleOwner)
                }
            })
        }
    }

    private fun initOutsideObserverTypes() {
        viewModel.apply {
            this.statusAb.observe(viewLifecycleOwner, Observer {
                if (it == AbilityPokedexStatus.DONE) {
                    showCustomTypesDialog(viewModel.pokeTypes.value!!)
                    this.statusAb.removeObservers(viewLifecycleOwner) }
            })
        }
    }

    private fun openDialog(message: String?) {
        material = MaterialAlertDialogBuilder(context).setTitle(getString(R.string.ability_description_dialog_pokemon_in_types)).setPositiveButton(getString(R.string.button_text_dismiss), null)
        material.setMessage(message)
        material.show()
    }

    private fun initType() {
        lvTypes.adapter = adapterTypes

        lvTypes.setOnItemClickListener { parent, view, position, id ->
            val elementId = adapterTypes.getItem(position).type.url // The item that was clicked

            val typeId = elementId?.let { cropTypeUrl(it) }?.let { Integer.parseInt(it) }

            elementId?.let { typeId?.let { idType -> viewModel.getPokesInTypes(idType) } }

            initOutsideObserverTypes()
        }
    }

    private fun initAbilities() {
        lvAbilities.adapter = adapterAbilities

        lvAbilities.setOnItemClickListener { parent, view, position, id ->
            val elementId = adapterAbilities.getItem(position).ability.url // The item that was clicked

            val abilityId = Integer.parseInt(cropAbilityUrl(elementId))

            viewModel.getAbilityDesc(abilityId)

            initOutsideObserverAbilities()
        }
    }

    private fun showCustomTypesDialog(list: MutableList<NestedType>) {
        val pokeTypesDialog: TypesDialog = TypesDialog(list)

        fragmentManager?.let { pokeTypesDialog.show(it, getString(R.string.fragment_tag_pokemon_in_types_dialog)) }
    }
}
