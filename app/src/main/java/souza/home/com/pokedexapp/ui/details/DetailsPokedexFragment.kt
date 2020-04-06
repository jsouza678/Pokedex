package souza.home.com.pokedexapp.ui.details

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.ui.details.viewpager.SectionsPagerAdapter

class DetailsPokedexFragment(var pokeIdP: String, var pokeNameP: String) : Fragment(){

    private lateinit var viewModel: DetailsPokedexViewModel
    private lateinit var tvPoke: TextView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var galleryViewPager: GalleryViewPagerAdapter
    private lateinit var gallery : ViewPager

    private var pokeId: String = ""
    private var pokeName = ""
    private lateinit var mImages : MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)

        tvPoke = view.findViewById(R.id.tv_poke_name_detail)

        constraintLayout = view.findViewById(R.id.cl_details)
        gallery = view.findViewById(R.id.gallery_travel_detail_activity)

        mImages = ArrayList()
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


        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.color.observe(viewLifecycleOwner, Observer {
                setColor(it.color.name)
                //addImagesToList(it)
                //setCarousel()

            })

           this.poke.observe(viewLifecycleOwner, Observer {
                addImagesToList(it)
                initGalleryViewPager(mImages)
                   // mImages = viewModel.poke.value!!
            })


            this.status.observe(viewLifecycleOwner, Observer {
                when(it){

                }
            })

        }
    }

    private fun addImagesToList(it: MutableList<String>){
        mImages.addAll(it)
    }

    private fun ImageView.loadImage(uri: String?) {
        val options = RequestOptions()
            .placeholder(R.drawable.poke_load) // grey pokemon with load animation
            .override(320, 320)
            .error(R.drawable.poke_grey) // error pokemon with prohibited symbol

        Glide.with(this.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)
    }

    private fun setColor(color: String){

        var colorV = 0

        when(color){
            "red"-> colorV = R.color.poke_red
            "green"-> colorV = R.color.poke_green
            "blue"-> colorV = R.color.poke_blue
            "grey"-> colorV = R.color.poke_grey
            "black"-> colorV = R.color.poke_black
            "yellow"-> colorV = R.color.poke_yellow
            "white"-> colorV = R.color.poke_white
            "purple"-> colorV = R.color.poke_purple
            "pink"-> colorV = R.color.poke_pink
            "brown"-> colorV = R.color.poke_brown
            else-> colorV = R.color.poke_grey
        }

        val colorValue = ContextCompat.getColor(context!!, colorV)
        constraintLayout.setBackgroundColor(colorValue)
    }

    private fun initGalleryViewPager(travelGallery: MutableList<String>) {
        galleryViewPager = GalleryViewPagerAdapter(context!!, travelGallery)
        gallery.adapter = galleryViewPager
    }

}

class DynamicHeightViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec


        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }

        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}

