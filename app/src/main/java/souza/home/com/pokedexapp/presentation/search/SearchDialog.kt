package souza.home.com.pokedexapp.presentation.search

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.viewmodel.ext.android.viewModel
import souza.home.com.extensions.gone
import souza.home.com.extensions.observeOnce
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.presentation.pokecatalog.PokeCatalogFragment
import souza.home.com.pokedexapp.presentation.pokedetails.DetailsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.DELAY_LONG
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW
import souza.home.com.pokedexapp.utils.isString

class SearchDialog : DialogFragment() {

    private lateinit var pokesList: MutableList<Pokemon>
    private lateinit var adapter: SearchDialogAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonDismiss: Button
    private lateinit var searchButtonDialog: ImageButton
    private lateinit var textInputArea: TextInputEditText
    private lateinit var constraintErrorLayout: ConstraintLayout
    private lateinit var constraintDefaultLayout: ConstraintLayout
    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity?.layoutInflater!!.inflate(R.layout.fragment_poke_search_dialog, null)
        bindViews(view)
        pokesList = mutableListOf()
        adapter = activity?.applicationContext?.let { SearchDialogAdapter(pokesList, it) }!!
        val textViewResult: TextView = view.findViewById(R.id.text_view_label_search_dialog)

        val alert = AlertDialog.Builder(context)
        alert.setView(view)
        textInputArea = view.findViewById<TextInputEditText>(R.id.input_edit_text_search_dialog)

        initSearchButtonClickListener(textViewResult)
        setTransitionToPokeDetails()
        initRecyclerview()
        setupButtonDismiss()

        return alert.create()
    }

    private fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_poke_search_dialog)
        buttonDismiss = view.findViewById(R.id.button_dismiss_custom_search_dialog)
        searchButtonDialog = view.findViewById(R.id.search_button_search_dialog)
        constraintErrorLayout = view.findViewById(R.id.container_layout_error_search_dialog)
        constraintDefaultLayout = view.findViewById(R.id.container_layout_default_search_dialog)
    }

    private fun initSearchButtonClickListener(textViewResult: TextView) {
        searchButtonDialog.setOnClickListener {
            val textSearch = textInputArea.text.toString()
            val checkString = isString(textSearch)

            if (textSearch == EMPTY_STRING) { textInputArea.error = getString(R.string.input_text_search_dialog) } else {
                if (checkString) {
                    initSearchById(textSearch, textViewResult)
                    textInputArea.text?.clear()
                } else { // is string now
                    if (textSearch.length < 20) {
                        if (!textSearch.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                            initSearchByName(textSearch, textViewResult)
                            textInputArea.text?.clear()
                        }
                    } else {
                        textInputArea.error = getString(R.string.input_text_search_dialog)
                        textInputArea.text?.clear()
                    }
                }
            }
        }
    }

    private fun initSearchById(textSearch: String, textViewResult: TextView) {
        viewModel.searchPokesById(Integer.parseInt(textSearch)).observeOnce(this@SearchDialog, Observer {
            if (it?.isEmpty()!!) {
                errorMessage()
            } else {
                adapter.submitList(it as MutableList<Pokemon>)
                val textResult = getString(R.string.pokemon_found_search_1) + it.size + getString(R.string.pokemon_found_search_3)
                textViewResult.text = textResult
            }
        })
    }

    private fun initSearchByName(textSearch: String, textViewResult: TextView) {
        viewModel.searchPokesByName(textSearch).observeOnce(this@SearchDialog, Observer {
            if (it?.isEmpty()!!) {
                errorMessage()
            } else {
                adapter.submitList(it as MutableList<Pokemon>)
                val textResult = getString(R.string.pokemon_found_search_1) + it.size + getString(R.string.pokemon_found_search_4)
                textViewResult.text = textResult
            }
        })
    }

    private fun setupButtonDismiss() {
        buttonDismiss.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_home_activity, PokeCatalogFragment())?.commit()
            dismiss()
            view?.let { view -> Snackbar.make(view, getString(R.string.redirect_search_to_home_message), BaseTransientBottomBar.LENGTH_SHORT).show() }
        }
    }

    private fun initRecyclerview() {
        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private fun toggleErrorVisibility() {
        constraintDefaultLayout.gone()
        constraintErrorLayout.visible()
    }

    private fun toggleBackErrorVisibility() {
        constraintDefaultLayout.visible()
        constraintErrorLayout.gone()
    }

    private fun errorMessage() {
        toggleErrorVisibility()
        Handler().postDelayed({
            toggleBackErrorVisibility()
        }, DELAY_LONG)
    }

    private fun setTransitionToPokeDetails() {
        adapter.onItemClick = {
            val idPoke = it.id
            val pokeName = it.name
            val details = pokeName?.let { it1 -> DetailsFragment(idPoke, it1) }

            if (details != null) {
                fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_home_activity, details)?.addToBackStack(null)?.commit()
            }
            dismiss()
        }
    }
}
