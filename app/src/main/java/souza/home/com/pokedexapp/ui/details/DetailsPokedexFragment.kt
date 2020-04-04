package souza.home.com.pokedexapp.ui.details


import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.synnapps.carouselview.CarouselView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.ui.details.viewpager.SectionsPagerAdapter

class DetailsPokedexFragment(var pokeIdP: String, var pokeNameP: String) : Fragment(){

    private lateinit var tvName : TextView

    private lateinit var viewModel: DetailsPokedexViewModel
    private lateinit var tvPoke: TextView


    private var pokeId: String = ""
    private var pokeName = ""


    private val mImages = intArrayOf(
        R.drawable.master,
        R.drawable.back,
        R.drawable.female,
        R.drawable.back_female,
        R.drawable.shiny,
        R.drawable.shiny_female,
        R.drawable.back_shiny,
        R.drawable.back_shiny_female
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)

        tvPoke = view.findViewById(R.id.tv_poke_name_detail)

        pokeId = pokeIdP
        pokeName = pokeNameP

        tvPoke.text = pokeName.capitalize()

        viewModel = ViewModelProviders.of(this,
            DetailsPokedexViewModelFactory(
                pokeId,
                activity!!.application
            )
        )
            .get(DetailsPokedexViewModel::class.java)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                view.context,
                fragmentManager!!,
                pokeId
            )

        val viewPager: DynamicHeightViewPager = view.findViewById(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val carouselView = view.findViewById<CarouselView>(R.id.carousel)
        carouselView.pageCount = mImages.size

        carouselView.setImageListener{ position, imageView ->
            imageView.setImageResource(
                mImages[position]
            )
        }

        return view
    }

/*
    override fun onBackPressed(): Boolean {
        val count: Int = fragmentManager!!.backStackEntryCount

        return if (count == 0) {
            //additional code
            true
        } else {
            fragmentManager?.popBackStack()
            false
        }
    }

*/

}

class DynamicHeightViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec

        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }

        if (height != 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }}

