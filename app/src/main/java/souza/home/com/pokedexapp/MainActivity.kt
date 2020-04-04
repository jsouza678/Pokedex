package souza.home.com.pokedexapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import souza.home.com.pokedexapp.ui.details.DetailsPokedexFragment
import souza.home.com.pokedexapp.ui.home.HomePokedexFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textFragment = DetailsPokedexFragment("25", "pikachu")
        //al textFragment = HomePokedexFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, textFragment).commit()

    }
/*
    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }*/
}
