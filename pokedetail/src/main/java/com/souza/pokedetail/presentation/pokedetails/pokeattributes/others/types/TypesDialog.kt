package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others.types

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.souza.pokedetail.R
import com.souza.pokedetail.data.pokedex.remote.response.TypeResponse
import com.souza.pokedetail.databinding.FragmentPokeTypesDialogBinding
import com.souza.pokedetail.presentation.pokedetails.PokeDetailsFragment
import com.souza.pokedetail.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW
import com.souza.pokedetail.utils.cropPokeUrl

class TypesDialog(private val pokeTypes: MutableList<TypeResponse>) : DialogFragment() {

    private lateinit var adapter: TypesDialogAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonDismiss: Button
    private lateinit var textViewResult: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentPokeTypesDialogBinding.inflate(LayoutInflater.from(context))
        val listSize =
            getString(R.string.pokemon_found_search_1) + pokeTypes.size + getString(R.string.pokemon_found_search_2)
        val typesAlertDialog = AlertDialog.Builder(activity)
        recyclerView = binding.recyclerViewPokeSearchDialog
        buttonDismiss = binding.buttonDismissCustomSearchDialog
        textViewResult = binding.textViewLabelSearchDialog
        adapter = TypesDialogAdapter(pokeTypes, requireContext())
        setupRecyclerView()
        setupDismissButtonOnClick()
        setTransitionToPokeDetails()
        textViewResult.text = listSize

        typesAlertDialog.setView(binding.root)
        return typesAlertDialog.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private fun setupDismissButtonOnClick() {
        buttonDismiss.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerView() {
        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setTransitionToPokeDetails() {
        adapter.onItemClick = {
            val urlChain = it.pokemon!!.id
            val pokeName = it.pokemon.name
            val pokePath = Integer.parseInt(cropPokeUrl(urlChain))
            val details = pokeName?.let { it1 -> PokeDetailsFragment(pokePath, it1) }

            details?.let { it1 ->
                fragmentManager?.beginTransaction()?.replace(
                    R.id.nav_host_fragment_details_activity,
                    it1
                )?.commit()
            }
            dismiss()
        }
    }
}
