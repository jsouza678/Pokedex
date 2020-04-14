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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import souza.home.com.extensions.gone
import souza.home.com.extensions.observeOnce
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.presentation.details.DetailsFragment
import souza.home.com.pokedexapp.presentation.home.HomeFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.DELAY_POST_2000
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW
import souza.home.com.pokedexapp.utils.isString

class SearchDialog : DialogFragment() {

    private lateinit var pokesList : MutableList<Poke>
    private lateinit var adapter: SearchDialogAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonDismiss: Button
    private lateinit var searchButtonDialog: ImageButton
    private lateinit var textInputArea: TextInputEditText
    private lateinit var constraintErrorLayout: ConstraintLayout
    private lateinit var constraintDefaultLayout: ConstraintLayout
    private lateinit var viewModel: SearchDialogViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view : View = activity!!.layoutInflater.inflate(R.layout.fragment_poke_search_dialog, null)
        pokesList = mutableListOf()
        bindViews(view)
        adapter = activity?.applicationContext?.let { SearchDialogAdapter(pokesList, it) }!!
        val textViewResult: TextView = view.findViewById(R.id.text_view_custom_alert_dialog_label)

        val alert = AlertDialog.Builder(context)
        alert.setView(view)
        textInputArea = view.findViewById<TextInputEditText>(R.id.input_edit_text_search_fragment)

        initViewModel()
        initSearchButtonClickListener(textViewResult)
        setTransitionToPokeDetails()
        initRecyclerview()
        setupButtonDismiss()

        return alert.create()
    }

    private fun bindViews(view: View){
        recyclerView = view.findViewById(R.id.recycler_view_poke_search_alert)
        buttonDismiss = view.findViewById(R.id.button_dismiss_custom_search_dialog)
        searchButtonDialog = view.findViewById(R.id.search_button_dialog)
        constraintErrorLayout = view.findViewById(R.id.container_layout_error)
        constraintDefaultLayout = view.findViewById(R.id.container_layout_default)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this,
            activity?.application?.let { SearchFactory(it) })
            .get(SearchDialogViewModel::class.java)
    }

    private fun initSearchButtonClickListener(textViewResult: TextView){
        searchButtonDialog.setOnClickListener {
            val textSearch = textInputArea.text.toString()
            val checkString = isString(textSearch)

            if (textSearch == EMPTY_STRING) { textInputArea.error = getString(R.string.input_text_search_dialog) }
            else {
                if (checkString) {
                    initSearchById(textSearch, textViewResult)
                    textInputArea.text?.clear()
                }else{  // is string now
                    if(textSearch.length < 40){
                        initSearchByName(textSearch, textViewResult)
                        textInputArea.text?.clear()
                    }else{
                        textInputArea.error = getString(R.string.input_text_search_dialog)
                        textInputArea.text?.clear()
                    }
                }
            }
        }
    }

    private fun initSearchById(textSearch : String, textViewResult: TextView){
        viewModel.searchForItemsById(Integer.parseInt(textSearch)).observeOnce(this@SearchDialog, Observer {
            if(it?.isEmpty()!!){
                errorMessage()
            }else{
                adapter.submitList(it as MutableList<Poke>)
                val textResult = getString(R.string.pokemon_found_search_1) + it.size + getString(R.string.pokemon_found_search_3)
                textViewResult.text = textResult
            }
        })
    }

    private fun initSearchByName(textSearch : String, textViewResult: TextView){
        viewModel.searchForItemsByName(textSearch).observeOnce(this@SearchDialog, Observer {
            if(it?.isEmpty()!!){
                errorMessage()
            }else{
                adapter.submitList(it as MutableList<Poke>)
                val textResult = getString(R.string.pokemon_found_search_1) + it.size + getString(R.string.pokemon_found_search_4)
                textViewResult.text = textResult
            }
        })
    }

    private fun setupButtonDismiss(){
        buttonDismiss.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, HomeFragment())?.commit()
            dismiss()
            view?.let { view -> Snackbar.make(view, getString(R.string.redirect_search_to_home_message), BaseTransientBottomBar.LENGTH_SHORT).show() }
        }
    }

    private fun initRecyclerview(){
        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private fun toggleErrorVisibility(){
        constraintDefaultLayout.gone()
        constraintErrorLayout.visible()
    }

    private fun toggleBackErrorVisibility(){
        constraintDefaultLayout.visible()
        constraintErrorLayout.gone()
    }

    private fun errorMessage(){
        toggleErrorVisibility()
        Handler().postDelayed({
            toggleBackErrorVisibility()
        }, DELAY_POST_2000)
    }

    private fun setTransitionToPokeDetails(){
        adapter.onItemClick = {
            val idPoke = it._id
            val pokeName = it.name
            val details = DetailsFragment(idPoke, pokeName)

            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, details)?.addToBackStack(null)?.commit()
            dismiss()
        }
    }
}