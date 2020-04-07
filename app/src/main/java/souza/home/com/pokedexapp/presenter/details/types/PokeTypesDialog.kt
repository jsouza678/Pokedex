package souza.home.com.pokedexapp.presenter.details.types


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.types.PokemonNested

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

        buttonDismiss.setOnClickListener {
            dismiss()
        }


        return alert.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
