package souza.home.com.pokedexapp.presentation

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val splashFragment = SplashScreen()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, splashFragment).commit()

        val textFragment = HomeFragment()

        Handler().postDelayed({
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, textFragment).commit()
        }, 1000)
    }
}
