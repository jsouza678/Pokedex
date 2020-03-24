package souza.home.com.pokedexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import souza.home.com.pokedexapp.ui.home.HomePokedexFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textFragment = HomePokedexFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, textFragment).commit()


        //Toast.makeText(this, "main", Toast.LENGTH_SHORT).show()
    }

}
