package souza.home.com.pokedexapp.presentation.pokedetail.detailsnested.others.types

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.response.NestedTypeResponse
import souza.home.com.pokedexapp.presentation.pokedetail.DetailsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW
import souza.home.com.pokedexapp.utils.cropPokeUrl

class TypesDialog(private val pList: MutableList<NestedTypeResponse>) : DialogFragment() {

    private lateinit var pokesList: MutableList<NestedTypeResponse>
    private lateinit var adapter: TypesDialogAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonDismiss: Button
    private lateinit var textViewResult: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity?.layoutInflater?.inflate(R.layout.fragment_poke_types_dialog, null)!!

        recyclerView = view.findViewById(R.id.recycler_view_poke_search_dialog)
        buttonDismiss = view.findViewById(R.id.button_dismiss_custom_search_dialog)
        textViewResult = view.findViewById(R.id.text_view_label_search_dialog)

        pokesList = pList
        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        adapter = TypesDialogAdapter(pokesList, view.context)
        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

        setTransitionToPokeDetails()
        val listSize = getString(R.string.pokemon_found_search_1) + pokesList.size + getString(R.string.pokemon_found_search_2)
        textViewResult.text = listSize

        buttonDismiss.setOnClickListener {
            dismiss()
        }

        return alert.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private fun setTransitionToPokeDetails() {
        adapter.onItemClick = {
            val urlChain = it.pokemon._id
            val pokeName = it.pokemon.name
            val pokePath = Integer.parseInt(cropPokeUrl(urlChain))
            val details = DetailsFragment(pokePath, pokeName)

            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_home_activity, details)?.commit()
            dismiss()
        }
    }
}
