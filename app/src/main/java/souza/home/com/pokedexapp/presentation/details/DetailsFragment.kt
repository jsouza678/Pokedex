package souza.home.com.pokedexapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.details.viewpager.SectionsPagerAdapter
import android.animation.ObjectAnimator
import android.animation.ArgbEvaluator
import souza.home.com.pokedexapp.presentation.view_utils.DynamicHeightViewPager
import souza.home.com.pokedexapp.utils.Constants.Companion.LIMIT_NORMAL_POKES
import souza.home.com.pokedexapp.utils.Constants.Companion.TIME_BACKGROUND_ANIMATION


class DetailsFragment(private var pokeId: Int, private var pokeName: String) : Fragment(){

    private lateinit var viewModel: DetailsPokedexViewModel
    private lateinit var tvPokeName: TextView
    private lateinit var tvPokeId: TextView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var galleryViewPager: GalleryViewPagerAdapter
    private lateinit var gallery : ViewPager
    private lateinit var mImages : MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)

        tvPokeName = view.findViewById(R.id.text_view_poke_name_detail)
        tvPokeId = view.findViewById(R.id.text_view_poke_id_detail)
        constraintLayout = view.findViewById(R.id.layout_details)
        gallery = view.findViewById(R.id.image_slider_detail_fragment)
        val viewPager: DynamicHeightViewPager = view.findViewById(R.id.fragment_container_details)
        val tabs: TabLayout = view.findViewById(R.id.tab_layout_details_fragments)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_details_fragment)
        mImages = ArrayList()

        toolbar.setNavigationOnClickListener(View.OnClickListener { activity!!.onBackPressed() })

        tvPokeName.text = pokeName.capitalize()
        val textId = "%03d".format(pokeId)
        tvPokeId.text = context?.resources?.getString(R.string.text_view_placeholder_hash, textId)

        viewModel = ViewModelProviders.of(this,
            activity?.application?.let {
                DetailsViewModelFactory(
                    pokeId,
                    it
                )
            }
        )
            .get(DetailsPokedexViewModel::class.java)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                fragmentManager!!,
                pokeId
            )

        setViewPager(viewPager, sectionsPagerAdapter, tabs)
        initObservers()

        return view
    }

    private fun setViewPager(viewPager: ViewPager, sectionsPagerAdapter: SectionsPagerAdapter, tabs: TabLayout){
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun initObservers(){
        viewModel.apply {
            this.updateVariationsOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    setColor(it.color?.name, pokeId)
                    }

                }
            )
            this.updatePropertiesOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if(it!=null) {
                    val listResult = it
                    val auxList = mutableListOf<String>()

                    listResult.sprites?.front_default?.let { auxList.add(it) }
                    listResult.sprites?.back_default?.let { auxList.add(it) }
                    listResult.sprites?.front_female?.let { auxList.add(it) }
                    listResult.sprites?.back_female?.let { auxList.add(it) }
                    listResult.sprites?.front_shiny?.let { auxList.add(it) }
                    listResult.sprites?.back_shiny?.let { auxList.add(it) }
                    listResult.sprites?.front_shiny_female?.let { auxList.add(it) }
                    listResult.sprites?.back_shiny_female?.let { auxList.add(it) }

                    addImagesToList(auxList)
                    initGalleryViewPager(mImages)
                }
            })

            this.poke.observe(viewLifecycleOwner, Observer {

            })
        }
    }

    private fun addImagesToList(it: MutableList<String>){
        mImages.addAll(it)
    }

    private fun setColor(color: String?, pokeId: Int){

        var colorV = when(color){
            "red"-> R.color.poke_red
            "green"-> R.color.poke_green
            "blue"-> R.color.poke_blue
            "grey"-> R.color.poke_grey
            "black"-> R.color.poke_black
            "yellow"-> R.color.poke_yellow
            "white"-> R.color.poke_white
            "purple"-> R.color.poke_purple
            "pink"-> R.color.poke_pink
            "brown"-> R.color.poke_brown
            else-> R.color.poke_grey
        }

        if(pokeId> LIMIT_NORMAL_POKES ){
            colorV  = R.color.poke_black
        }

        val backgroundColorAnimator = ObjectAnimator.ofObject(
            constraintLayout,
            "backgroundColor",
            ArgbEvaluator(),
            context?.let { ContextCompat.getColor(it, R.color.blue_poke) },
            context?.let { ContextCompat.getColor(it, colorV) })

        backgroundColorAnimator.duration = TIME_BACKGROUND_ANIMATION
        backgroundColorAnimator.start()
    }

    private fun initGalleryViewPager(travelGallery: MutableList<String>) {
        galleryViewPager =
            GalleryViewPagerAdapter(
                context!!,
                travelGallery
            )
        gallery.adapter = galleryViewPager
    }
}
