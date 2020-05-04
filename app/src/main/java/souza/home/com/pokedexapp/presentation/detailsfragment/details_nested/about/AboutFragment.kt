package souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.about

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
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import souza.home.com.extensions.gone
import souza.home.com.extensions.observeOnce
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties
import souza.home.com.pokedexapp.presentation.detailsfragment.DetailsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.LIMIT_NORMAL_POKES
import souza.home.com.pokedexapp.utils.cropPokeUrl

class AboutFragment(private val pokemon: Int) : Fragment() {

    private val viewModel by viewModel<AboutViewModel>{ parametersOf(pokemon)}
    private lateinit var spVariations: Spinner
    private lateinit var tvDesc: TextView
    private lateinit var varietiesArray: MutableList<Varieties>
    private lateinit var pokemonsArray: MutableList<Varieties>
    private lateinit var adapterSpinner: AboutSpinnerAdapter
    private lateinit var constraintDefault: ConstraintLayout
    private lateinit var constraintEvolution: ConstraintLayout
    private var pokePath: Int = ABSOLUTE_ZERO
    private var urlChain: String = EMPTY_STRING
    private var spinnerSelected: Int = ABSOLUTE_ZERO
    private lateinit var material: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_about, container, false)
        bindViews(view)
        varietiesArray = mutableListOf()
        pokemonsArray = mutableListOf()

        adapterSpinner =
            AboutSpinnerAdapter(
                view.context,
                pokemonsArray
            )

        if (pokemon > LIMIT_NORMAL_POKES) {
            constraintDefault.gone()
            constraintEvolution.visible()
        }

        initDataObserver()

        return view
    }

    private fun bindViews(view: View) {
        tvDesc = view.findViewById(R.id.text_view_poke_desc_about)
        spVariations = view.findViewById(R.id.spinner_variations_about)
        constraintDefault = view.findViewById(R.id.constraint_layout_default_about)
        constraintEvolution = view.findViewById(R.id.constraint_layout_mysterious_about)
    }

    private fun setOnClickTestDescription(message: String?) {
        tvDesc.setOnClickListener {
            openDialog(message)
        }
    }

    private fun openDialog(message: String?) {
        material = MaterialAlertDialogBuilder(context).setTitle(getString(R.string.pokemon_description_dialog_pokemon_in_types)).setPositiveButton(getString(R.string.button_text_dismiss), null)
        material.setMessage(message)
        material.show()
    }

    private fun initDataObserver() {
        viewModel.apply {
            this.updateVariationsOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer {
                initSpinner()
                adapterSpinner.submitList(it?.varieties)
                pokemonsArray = it?.varieties!!
                tvDesc.text = it.description
                setOnClickTestDescription(it.description) })
        }
    }

    private fun initSpinner() {
        spVariations.adapter = adapterSpinner
        spVariations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                spinnerSelected = position - 1
                when (position) {
                    0 -> { } // Do Nothing. This is the hint position.
                    else -> { onSpinnerSelectedChange() }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) { /* Do nothing when not selected" */ }
        }
    }

    private fun onSpinnerSelectedChange() {
        urlChain = pokemonsArray[spinnerSelected].pokemon._id

        pokePath = Integer.parseInt(cropPokeUrl(urlChain))

        if (pokePath == pokemon) {
            view?.let { Snackbar.make(it, getString(R.string.choose_another_poke_spinner_error), BaseTransientBottomBar.LENGTH_SHORT).show() }
        } else {
            view?.let { Snackbar.make(it, R.string.snackbar_loading_poke_evolution, BaseTransientBottomBar.LENGTH_SHORT).show() }
            val newPoke = pokemonsArray[spinnerSelected].pokemon.name
            val details = DetailsFragment(pokePath, newPoke)

            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment_home_activity, details)?.commit()
        }
    }
}
