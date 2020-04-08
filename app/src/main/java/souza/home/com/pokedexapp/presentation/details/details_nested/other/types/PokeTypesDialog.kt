package souza.home.com.pokedexapp.presentation.details.details_nested.other.types


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.types.PokemonNested
import souza.home.com.pokedexapp.presentation.details.DetailsPokedexFragment

class PokeTypesDialog(private val pList: MutableList<PokemonNested>) : DialogFragment() {

    private lateinit var pokesList : MutableList<PokemonNested>
    private lateinit var adapter: PokesTypesDialogAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonDismiss: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view : View = activity?.layoutInflater?.inflate(R.layout.fragment_poke_types_dialog, null)!!

        recyclerView = view.findViewById(R.id.recycler_view_poke_types_alert)
        buttonDismiss = view.findViewById(R.id.button_dismiss_custom_dialog)

        pokesList = pList
        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        adapter = PokesTypesDialogAdapter(pokesList, view.context)
        layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

        setTransitionToPokeDetails()

        buttonDismiss.setOnClickListener {
            dismiss()
        }


        return alert.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setTransitionToPokeDetails(){
        adapter.onItemClick = {
            val urlChain = it.pokemon.url
            val pokeName = it.pokemon.name
            val pokePath = urlChain.substringAfterLast("n/").substringBeforeLast("/")
            val details = DetailsPokedexFragment(pokePath, pokeName)

            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, details)?.commit()
            dismiss()
        }
    }
}