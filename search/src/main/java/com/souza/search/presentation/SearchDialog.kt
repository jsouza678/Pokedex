package com.souza.search.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.souza.extensions.gone
import com.souza.extensions.observeOnce
import com.souza.extensions.visible
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.pokedetail.presentation.pokedetails.PokeDetailsActivity
import com.souza.search.R
import com.souza.search.databinding.FragmentPokeSearchDialogBinding
import com.souza.search.utils.Constants.Companion.DELAY_LONG
import com.souza.search.utils.Constants.Companion.EMPTY_STRING
import com.souza.search.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW
import com.souza.search.utils.isString
import org.koin.android.viewmodel.ext.android.viewModel

class SearchDialog : DialogFragment() {

    private lateinit var pokesList: MutableList<Pokemon>
    private lateinit var adapter: SearchDialogAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var buttonDismiss: Button
    private lateinit var searchButtonDialog: ImageButton
    private lateinit var textInputArea: TextInputEditText
    private lateinit var constraintErrorLayout: ConstraintLayout
    private lateinit var constraintDefaultLayout: ConstraintLayout
    private val viewModel by viewModel<SearchViewModel>()
    private val binding = FragmentPokeSearchDialogBinding.inflate(LayoutInflater.from(context))

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        buttonDismiss = binding.buttonDismissCustomSearchDialog
        searchButtonDialog = binding.searchButtonSearchDialog
        constraintErrorLayout = binding.containerLayoutErrorSearchDialog
        constraintDefaultLayout = binding.containerLayoutDefaultSearchDialog
        val textViewResult: TextView = binding.textViewLabelSearchDialog
        textInputArea = binding.inputEditTextSearchDialog

        pokesList = mutableListOf()
        adapter = SearchDialogAdapter(pokesList, requireContext())

        val alert = AlertDialog.Builder(context)
        alert.setView(binding.root)

        initSearchButtonClickListener(textViewResult)
        setTransitionToPokeDetails()
        initRecyclerview()
        setupButtonDismiss()

        return alert.create()
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
            if (it != null) {
                if (it.isEmpty()) {
                    errorMessage()
                } else {
                    adapter.submitList(it as MutableList<Pokemon>)
                    val textResult = getString(R.string.pokemon_found_search_1) + it.size + getString(R.string.pokemon_found_search_3)
                    textViewResult.text = textResult
                }
            }
        })
    }

    private fun initSearchByName(textSearch: String, textViewResult: TextView) {
        viewModel.searchPokesByName(textSearch).observeOnce(this@SearchDialog, Observer {
            if (it != null) {
                if (it.isEmpty()) {
                    errorMessage()
                } else {
                    adapter.submitList(it as MutableList<Pokemon>)
                    val textResult = getString(R.string.pokemon_found_search_1) + it.size + getString(R.string.pokemon_found_search_4)
                    textViewResult.text = textResult
                }
            }
        })
    }

    private fun setupButtonDismiss() {
        buttonDismiss.setOnClickListener {
            dismiss()
        }
    }

    private fun initRecyclerview() {
        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        binding.recyclerViewPokeSearchDialog.layoutManager = layoutManager

        binding.recyclerViewPokeSearchDialog.adapter = adapter
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
            val pokeId = it.id
            val pokeName = it.name

            val intent = Intent(activity, PokeDetailsActivity::class.java)
            intent.putExtra("pokeId", pokeId)
            intent.putExtra("pokeName", pokeName)
            activity?.startActivity(intent)
            dismiss()
        }
    }
}
