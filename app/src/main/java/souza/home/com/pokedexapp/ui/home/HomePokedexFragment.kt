package souza.home.com.pokedexapp.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import souza.home.com.pokedexapp.R


/**
 * A simple [Fragment] subclass.
 */
class HomePokedexFragment : Fragment() {

    private val viewModel: HomePokedexViewModel by lazy{
        ViewModelProviders.of(this).get(HomePokedexViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_pokedex, container, false)

        //Toast.makeText(view.context, "Called", Toast.LENGTH_SHORT).show()
        val recyclerView = view.findViewById<RecyclerView>(R.id.poke_recycler_view)

        viewModel.loadFirstPage(recyclerView, view.context)

        return view
    }
}
