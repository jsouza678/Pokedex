package souza.home.com.pokedexapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import souza.home.com.pokedexapp.ui.home.HomePokedexFragment
import souza.home.com.pokedexapp.ui.util.IOnBackPressed

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val textFragment = HomePokedexFragment()
        val textFragment = HomePokedexFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, textFragment).commit()

    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }
}
