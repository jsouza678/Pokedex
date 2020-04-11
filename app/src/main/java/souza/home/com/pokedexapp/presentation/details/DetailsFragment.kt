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


class DetailsFragment(private var pokeIdP: String, private var pokeNameP: String) : Fragment(){

    private lateinit var viewModel: DetailsPokedexViewModel
    private lateinit var tvPokeName: TextView
    private lateinit var tvPokeId: TextView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var galleryViewPager: GalleryViewPagerAdapter
    private lateinit var gallery : ViewPager

    private var pokeId: String = ""
    private var pokeName = ""
    private lateinit var mImages : MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)

        tvPokeName = view.findViewById(R.id.tv_poke_name_detail)
        tvPokeId = view.findViewById(R.id.tv_poke_id_detail)
        constraintLayout = view.findViewById(R.id.cl_details)
        gallery = view.findViewById(R.id.gallery_travel_detail_activity)
        val viewPager: DynamicHeightViewPager = view.findViewById(R.id.view_pager)
        val tabs: TabLayout = view.findViewById(R.id.tabs)

        mImages = ArrayList()
        pokeId = pokeIdP
        pokeName = pokeNameP

        tvPokeName.text = pokeName.capitalize()
        val textId = "%03d".format(Integer.parseInt(pokeId))
        tvPokeId.text = context?.resources?.getString(R.string.placeholder_tv_id, textId)

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


        val toolbar = view.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener { activity!!.onBackPressed() })

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
                setColor(it.color?.name, pokeIdP)
                }
                }
            )

           this.poke.observe(viewLifecycleOwner, Observer {
                //addImagesToList(it)
                //initGalleryViewPager(mImages)
            })
        }
    }

    private fun addImagesToList(it: MutableList<String>){
        mImages.addAll(it)
    }

    private fun setColor(color: String?, pokeId: String){

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

        if(Integer.parseInt(pokeId)> LIMIT_NORMAL_POKES ){
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

    override fun onStop() {
        super.onStop()
        this.viewModel.status.removeObservers(viewLifecycleOwner)
        this.viewModel.poke.removeObservers(viewLifecycleOwner)
        this.viewModel.color.removeObservers(viewLifecycleOwner)
    }
}
