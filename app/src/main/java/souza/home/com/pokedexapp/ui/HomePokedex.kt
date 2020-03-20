package souza.home.com.pokedexapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import souza.home.com.pokedexapp.R

/**
 * A simple [Fragment] subclass.
 */
class HomePokedex : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_pokedex, container, false)
    }


}
