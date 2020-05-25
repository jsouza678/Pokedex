package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.souza.extensions.observeOnce
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.model.ability.AbilitiesRoot
import com.souza.pokedetail.data.pokedex.remote.model.type.TypeRoot
import com.souza.pokedetail.data.pokedex.remote.response.TypeResponse
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.others.types.TypesDialog
import com.souza.pokedetail.utils.cropAbilityUrl
import com.souza.pokedetail.utils.cropTypeUrl
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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
        val view = inflater.inflate(R.layout.fragment_poke_others, container, false)
        bindViews(view)
        initObservers()

        return view
    }

    private fun bindViews(view: View) {
        typesListView = view.findViewById(R.id.list_view_types_others)
        abilitiesListView = view.findViewById(R.id.list_view_abilities_others)
    }

    private fun initObservers() {
        viewModel.apply {
            this.updatePropertiesOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer {
                it.types?.let { itemTypes -> adapterTypes.submitList(itemTypes)
                    setupTypesList() }
                it.abilities?.let { itemAbilities -> adapterAbilities.submitList(itemAbilities)
                    setupAbilitiesList() }
            })
        }
    }

    private fun initObserverAbilitiesDetails(id: Int) {
        viewModel.apply {
            this.getAbilityDesc(id)?.observeOnce(viewLifecycleOwner, Observer { setupAbilityDescriptionDialog(it) })
        }
    }

    private fun initObserverPokeInTypes(id: Int) {
        viewModel.apply {
            this.getPokesInTypes(id)?.observeOnce(viewLifecycleOwner, Observer { showPokesInTypesDialog(it.pokesInTypes!!) })
        }
    }

    private fun setupAbilityDescriptionDialog(message: String?) {
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context).setTitle(getString(R.string.ability_description_dialog_pokemon_in_types)).setPositiveButton(getString(R.string.button_text_dismiss), null)
        materialAlertDialogBuilder.setMessage(message)
        openAbilityDescriptionDialog()
    }

    private fun openAbilityDescriptionDialog() {
        materialAlertDialogBuilder.show()
    }

    private fun setupTypesList() {
        typesListView.adapter = adapterTypes
        typesListView.setOnItemClickListener { _, _, position, _ ->
            val selectedType = adapterTypes.getItem(position).type?.url
            val typeId = selectedType?.let { cropTypeUrl(it) }?.let { Integer.parseInt(it) }

            typeId?.let { initObserverPokeInTypes(it) }
        }
    }

    private fun setupAbilitiesList() {
        abilitiesListView.adapter = adapterAbilities
        abilitiesListView.setOnItemClickListener { _, _, position, _ ->
            val selectedAbility = adapterAbilities.getItem(position).ability?.url
            val abilityId = selectedAbility?.let { cropAbilityUrl(it) }?.let { Integer.parseInt(it) }

            abilityId?.let { initObserverAbilitiesDetails(it) }
        }
    }

    private fun showPokesInTypesDialog(list: MutableList<TypeResponse>) {
        val pokeTypesDialog = list.let { TypesDialog(it) }
        fragmentManager?.let { pokeTypesDialog.show(it, getString(R.string.fragment_tag_pokemon_in_types_dialog)) }
    }
}
