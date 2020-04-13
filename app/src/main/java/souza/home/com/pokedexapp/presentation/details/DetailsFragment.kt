package souza.home.com.pokedexapp.presentation.details

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
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
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.presentation.details.viewpager.SectionsPagerAdapter
import souza.home.com.pokedexapp.presentation.view_utils.DynamicHeightViewPager
import souza.home.com.pokedexapp.utils.ColorFormat
import souza.home.com.pokedexapp.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY
import souza.home.com.pokedexapp.utils.Constants.Companion.OFFSCREEN_DEFAULT_VIEW_PAGER
import souza.home.com.pokedexapp.utils.Constants.Companion.TIME_BACKGROUND_ANIMATION
import souza.home.com.pokedexapp.utils.cropPokeUrl

class DetailsFragment(private var pokeId: Int, private var pokeName: String) : Fragment(){

    private lateinit var viewModel: DetailsPokedexViewModel
    private lateinit var tvPokeName: TextView
    private lateinit var tvPokeId: TextView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var galleryViewPager: GalleryViewPagerAdapter
    private lateinit var gallery : ViewPager
    private lateinit var mImages : MutableList<String>
    private var count = 0

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

        setToolbarBackButton(toolbar)
        setPokeAndIdText()
        initViewModel()
        initObservers(viewPager, tabs, view)

        return view
    }

    private fun setToolbarBackButton(toolbar: Toolbar){
        toolbar.setNavigationOnClickListener(View.OnClickListener { activity?.onBackPressed() })
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this, activity?.application?.let {
            DetailsViewModelFactory(pokeId, it)
        }).get(DetailsPokedexViewModel::class.java)
    }

    private fun setViewPager(viewPager: ViewPager, sectionsPagerAdapter: SectionsPagerAdapter, tabs: TabLayout){
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun setPokeAndIdText(){
        tvPokeName.text = pokeName.capitalize()
        val textId = FORMAT_ID_POKE_DISPLAY.format(pokeId)
        tvPokeId.text = context?.resources?.getString(R.string.text_view_placeholder_hash, textId)
    }

    private fun initObservers(viewPager: ViewPager, tabs: TabLayout, view: View){
        viewModel.apply {
            this.updateVariationsOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    if(count == 0){
                        val backgroundColor = ColorFormat.setColor(it.color?.name, pokeId)
                        animateBackground(backgroundColor)

                        val sectionsPagerAdapter =
                            fragmentManager?.let { fm -> SectionsPagerAdapter(fm, pokeId,
                                Integer.parseInt(cropPokeUrl(it.evolution_chain?.url!!)) // Here the !! is accepted because
                            ) // the pokemon has a evolution chain url.
                            }
                        sectionsPagerAdapter?.let { item -> setViewPager(viewPager, item, tabs) }
                        viewPager.offscreenPageLimit = OFFSCREEN_DEFAULT_VIEW_PAGER
                        count++
                    }
                }else{
                    val sectionsPagerAdapterEvolution = fragmentManager?.let { fm ->
                        SectionsPagerAdapter(fm, pokeId, 0) }
                    sectionsPagerAdapterEvolution?.let { item -> setViewPager(viewPager, item, tabs) }
                    val backgroundColor = ColorFormat.setColor("black", pokeId)
                    animateBackground(backgroundColor)
                }
            }
            )
            this.updatePropertiesOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if(it!=null) {
                    val imagesList = addSpritesToList(it)
                    addImagesToList(imagesList)
                    initGalleryViewPager(mImages, view)
                }
            })
        }
    }

    private fun addSpritesToList(listResult: PokeProperty) : MutableList<String>{
        val auxList = mutableListOf<String>()

        listResult.sprites?.front_default?.let { auxList.add(it) }
        listResult.sprites?.back_default?.let { auxList.add(it) }
        listResult.sprites?.front_female?.let { auxList.add(it) }
        listResult.sprites?.back_female?.let { auxList.add(it) }
        listResult.sprites?.front_shiny?.let { auxList.add(it) }
        listResult.sprites?.back_shiny?.let { auxList.add(it) }
        listResult.sprites?.front_shiny_female?.let { auxList.add(it) }
        listResult.sprites?.back_shiny_female?.let { auxList.add(it) }

        return auxList
    }

    private fun addImagesToList(it: MutableList<String>){
        mImages.addAll(it)
    }

    private fun animateBackground(colorV: Int){
        val backgroundColorAnimator = ObjectAnimator.ofObject(
            constraintLayout,
            "backgroundColor",
            ArgbEvaluator(),
            context?.let { ContextCompat.getColor(it, R.color.blue_poke) },
            context?.let { ContextCompat.getColor(it, colorV) })

        backgroundColorAnimator.duration = TIME_BACKGROUND_ANIMATION
        backgroundColorAnimator.start()
    }

    private fun initGalleryViewPager(travelGallery: MutableList<String>, view: View) {
        galleryViewPager = GalleryViewPagerAdapter(view.context, travelGallery)
        gallery.adapter = galleryViewPager
    }
}