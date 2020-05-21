package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import souza.home.com.extensions.gone
import souza.home.com.extensions.observeOnce
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties
import souza.home.com.pokedexapp.presentation.pokedetails.DetailsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.LIMIT_NORMAL_POKES
import souza.home.com.pokedexapp.utils.cropPokeUrl

class AboutFragment(private val pokemonId: Int) : Fragment() {

    private val viewModel by viewModel<AboutViewModel> { parametersOf(pokemonId) }
    private lateinit var pokeVariationsSpinner: Spinner
    private lateinit var pokeDescriptionTextView: TextView
    private var pokemonsArray: MutableList<Varieties>? = mutableListOf()
    private val pokemonSpinnerAdapter by inject<AboutSpinnerAdapter> { parametersOf(pokemonsArray) }
    private lateinit var constraintDefault: ConstraintLayout
    private lateinit var constraintEvolution: ConstraintLayout
    private var uriEvolutionChain: Int = ABSOLUTE_ZERO
    private var urlEvolutionChain: String = EMPTY_STRING
    private var itemSelectedOnSpinner: Int = ABSOLUTE_ZERO
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poke_about, container, false)
        bindViews(view)

        if (pokemonId > LIMIT_NORMAL_POKES) {
            constraintDefault.gone()
            constraintEvolution.visible()
        }

        initSpinner()
        initDataObserver()

        return view
    }

    private fun bindViews(view: View) {
        pokeDescriptionTextView = view.findViewById(R.id.text_view_poke_desc_about)
        pokeVariationsSpinner = view.findViewById(R.id.spinner_variations_about)
        constraintDefault = view.findViewById(R.id.constraint_layout_default_about)
        constraintEvolution = view.findViewById(R.id.constraint_layout_mysterious_about)
    }

    private fun initSpinner() {
        pokeVariationsSpinner.adapter = pokemonSpinnerAdapter
        pokeVariationsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                itemSelectedOnSpinner = position - 1
                when (position) {
                    0 -> { } // Do Nothing. This is the hint position.
                    else -> { onSpinnerSelectedChange() }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun onSpinnerSelectedChange() {
        parseEvolutionPath()

        if (uriEvolutionChain == pokemonId) {
            turnOnSnackbarErrorPokeSelected()
        } else {
            turnOnSnackbarLoadingPokeSelected()
            val newPokeName = pokemonsArray?.get(itemSelectedOnSpinner)?.pokemon?.name
            val detailsFragment = newPokeName?.let { DetailsFragment(uriEvolutionChain, it) }

            detailsFragment?.let {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_host_fragment_home_activity, it)?.commit()
            }
        }
    }

    private fun turnOnSnackbarErrorPokeSelected() {
        view?.let { Snackbar.make(it, getString(R.string.choose_another_poke_spinner_error), BaseTransientBottomBar.LENGTH_SHORT).show() }
    }

    private fun turnOnSnackbarLoadingPokeSelected() {
        view?.let { Snackbar.make(it, R.string.snackbar_loading_poke_evolution, BaseTransientBottomBar.LENGTH_SHORT).show() }
    }

    private fun parseEvolutionPath() {
        urlEvolutionChain = pokemonsArray?.get(itemSelectedOnSpinner)?.pokemon?._id!!
        uriEvolutionChain = Integer.parseInt(cropPokeUrl(urlEvolutionChain))
    }

    private fun initDataObserver() {
        viewModel.apply {
            this.updateVariationsOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer {
                pokemonSpinnerAdapter.submitList(it?.varieties)
                pokemonsArray = it?.varieties!!
                pokeDescriptionTextView.text = it.description
                setupOnClickPokeDescriptionDialog(it.description)
            })
        }
    }

    private fun setupOnClickPokeDescriptionDialog(message: String?) {
        pokeDescriptionTextView.setOnClickListener {
            setupPokeDescriptionDialog(message)
            openPokeDescriptionDialog()
        }
    }

    private fun setupPokeDescriptionDialog(message: String?) {
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context).setTitle(getString(R.string.pokemon_description_dialog_pokemon_in_types)).setPositiveButton(getString(R.string.button_text_dismiss), null)
        materialAlertDialogBuilder.setMessage(message)
    }

    private fun openPokeDescriptionDialog() {
        materialAlertDialogBuilder.show()
    }
}
