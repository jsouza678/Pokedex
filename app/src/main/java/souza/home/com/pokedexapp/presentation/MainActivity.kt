package souza.home.com.pokedexapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val textFragment = DetailsFragment("25", "pikachu")
        val textFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, textFragment).commit()
    }
}
