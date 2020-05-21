package souza.home.com.pokedexapp.presentation.pokedetails

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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.util.Locale
import kotlin.collections.ArrayList
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import souza.home.com.extensions.observeOnce
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.PropertiesPokedexStatus
import souza.home.com.pokedexapp.data.pokedex.VarietiesPokedexStatus
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.utils.ColorFormat
import souza.home.com.pokedexapp.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY
import souza.home.com.pokedexapp.utils.Constants.Companion.LIMIT_NORMAL_POKES
import souza.home.com.pokedexapp.utils.Constants.Companion.OFFSCREEN_DEFAULT_VIEW_PAGER
import souza.home.com.pokedexapp.utils.Constants.Companion.TIME_BACKGROUND_ANIMATION
import souza.home.com.pokedexapp.utils.cropPokeUrl

class DetailsFragment(
    private val pokemonId: Int,
    private val pokemonName: String
) : Fragment() {

    private lateinit var pokeNameTextView: TextView
    private lateinit var pokeIdTextView: TextView
    private lateinit var constraintLayoutDetail: ConstraintLayout
    private lateinit var viewPagerDetail: ViewPager
    private lateinit var tabsViewPagerDetail: TabLayout
    private lateinit var tabsCarousel: TabLayout
    private var pokeSprites: MutableList<String> = mutableListOf()
    private val galleryViewPagerAdapter by inject<DetailsGalleryAdapter> { parametersOf(pokeSprites) }
    private lateinit var viewPagerGallery: ViewPager
    private val viewModel by viewModel<DetailsViewModel> { parametersOf(pokemonId) }

    @ExperimentalStdlibApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)
        bindViews(view)
        viewPagerDetail = view.findViewById(R.id.fragment_container_details)
        tabsViewPagerDetail = view.findViewById<TabLayout>(R.id.tab_layout_details)
        tabsCarousel = view.findViewById<TabLayout>(R.id.tab_layout_carousel_details)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_details)
        pokeSprites = ArrayList()

        setToolbarBackButton(toolbar)
        setPokeAndIdText()
        initGalleryViewPager()

        if (pokemonId> LIMIT_NORMAL_POKES) { showDataEvolutionPoke(viewPagerDetail, tabsViewPagerDetail)
        } else { bindRequestVarietiesStatus(VarietiesPokedexStatus.DONE, viewPagerDetail, tabsViewPagerDetail) }

        if (pokemonId> LIMIT_NORMAL_POKES) { showDataEvolutionPoke(viewPagerDetail, tabsViewPagerDetail)
            initObserverData(viewModel, viewPagerDetail, tabsViewPagerDetail)
        } else { bindRequestPropertiesStatus(PropertiesPokedexStatus.DONE, viewPagerDetail, tabsViewPagerDetail) }

        return view
    }

    private fun bindViews(view: View) {
        pokeNameTextView = view.findViewById(R.id.text_view_poke_name_details)
        pokeIdTextView = view.findViewById(R.id.text_view_poke_id_details)
        constraintLayoutDetail = view.findViewById(R.id.layout_details)
        viewPagerGallery = view.findViewById(R.id.image_slider_details)
    }

    private fun setToolbarBackButton(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    @ExperimentalStdlibApi
    private fun setPokeAndIdText() {
        pokeNameTextView.text = pokemonName.capitalize(Locale.getDefault())
        val textId = FORMAT_ID_POKE_DISPLAY.format(pokemonId)
        pokeIdTextView.text = context?.resources?.getString(R.string.text_view_placeholder_hash, textId)
    }

    private fun initGalleryViewPager() {
        viewPagerGallery.adapter = galleryViewPagerAdapter
        tabsCarousel.setupWithViewPager(viewPagerGallery, true)
    }

/*
    private fun initObserverStatus(viewPager: ViewPager, tabs: TabLayout) {
        viewModel.apply {
            this.checkRequestVariationsStatus().observe(viewLifecycleOwner, Observer {
                if (pokeId> LIMIT_NORMAL_POKES) { showDataEvolutionPoke(viewPager, tabs)
                } else { bindRequestVarietiesStatus(it, viewPager, tabs) }
            })
            this.checkRequestPropertiesStatus().observe(viewLifecycleOwner, Observer {
                if (pokeId> LIMIT_NORMAL_POKES) { showDataEvolutionPoke(viewPager, tabs)
                    initObserverData(viewModel, viewPager, tabs)
                } else { bindRequestPropertiesStatus(it, viewPager, tabs) }
            })
        }
    }*/

    private fun bindRequestVarietiesStatus(
        varietiesPokedexStatus: VarietiesPokedexStatus,
        viewPager: ViewPager,
        tabs: TabLayout
    ) {
        when (varietiesPokedexStatus) {
            VarietiesPokedexStatus.LOADING -> {}
            VarietiesPokedexStatus.DONE -> initObserverData(viewModel, viewPager, tabs)
            VarietiesPokedexStatus.EMPTY -> initObserverData(viewModel, viewPager, tabs)

            else -> {
                initObserverData(viewModel, viewPager, tabs)
                showError()
            }
        }
    }

    private fun bindRequestPropertiesStatus(
        propertiesPokedexStatus: PropertiesPokedexStatus,
        viewPager: ViewPager,
        tabs: TabLayout
    ) {
        when (propertiesPokedexStatus) {
            PropertiesPokedexStatus.LOADING -> {}
            PropertiesPokedexStatus.DONE -> initObserverData(viewModel, viewPager, tabs)
            PropertiesPokedexStatus.EMPTY -> showError()
            else -> {
                initObserverData(viewModel, viewPager, tabs)
                showError()
            }
        }
    }

    private fun initObserverData(viewModel: DetailsViewModel, viewPager: ViewPager, tabsDetails: TabLayout) {
        viewModel.apply {
            this.updateVariationsOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer { pokeVariety ->
                pokeVariety?.let { it -> showDataNormalPoke(it, viewPager, tabsDetails) }
            })
            this.updatePropertiesOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer {
                loadImages(it)
            })
        }
    }

    private fun loadImages(pokeProperty: PokeProperty) {
        val imagesList = addSpritesToList(pokeProperty)
        addImagesToList(imagesList)
        // view?.let { it1 -> initGalleryViewPager(mImages, it1) }
    }

    private fun showError() {
        view?.let { Snackbar.make(it, getString(R.string.no_conectivity), BaseTransientBottomBar.LENGTH_SHORT).show() }
    }

    private fun setViewPager(viewPager: ViewPager, sectionsPagerAdapter: DetailsViewPagerAdapter, tabs: TabLayout) {
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun showDataNormalPoke(pokeVariety: PokeVariety, viewPager: ViewPager, tabs: TabLayout) {
        val backgroundColor = ColorFormat.setColor(pokeVariety.color?.name, pokemonId)
        animateBackground(backgroundColor)
        val pokeChainUriPath = pokeVariety.evolutionChain?.url?.let { path -> cropPokeUrl(path) }
        val pokeChainId = pokeChainUriPath?.let { id -> Integer.parseInt(id) }

        val sectionsPagerAdapter =
            fragmentManager?.let { fragmentManager ->
                pokeChainId?.let { pokeChainId ->
                    DetailsViewPagerAdapter(
                        fragmentManager, pokemonId, pokeChainId
                    )
                }
            }
        sectionsPagerAdapter?.let { item -> setViewPager(viewPager, item, tabs) }
        viewPager.offscreenPageLimit = OFFSCREEN_DEFAULT_VIEW_PAGER
    }

    private fun showDataEvolutionPoke(viewPager: ViewPager, tabs: TabLayout) {
        val sectionsPagerAdapterEvolution = fragmentManager?.let { fragmentManager ->
            DetailsViewPagerAdapter(fragmentManager, pokemonId, 0)
        }
        sectionsPagerAdapterEvolution?.let { item -> setViewPager(viewPager, item, tabs) }
        val backgroundColor = ColorFormat.setColor(getString(R.string.black_color_name), pokemonId)
        animateBackground(backgroundColor)
    }

    private fun addSpritesToList(listResult: PokeProperty): MutableList<String> {
        val auxList = mutableListOf<String>()

        listResult.sprites?.frontDefault?.let { auxList.add(it) }
        listResult.sprites?.backDefault?.let { auxList.add(it) }
        listResult.sprites?.frontFemale?.let { auxList.add(it) }
        listResult.sprites?.backFemale?.let { auxList.add(it) }
        listResult.sprites?.frontShiny?.let { auxList.add(it) }
        listResult.sprites?.backShiny?.let { auxList.add(it) }
        listResult.sprites?.frontShinyFemale?.let { auxList.add(it) }
        listResult.sprites?.backShinyFemale?.let { auxList.add(it) }

        return auxList
    }

    private fun addImagesToList(pokeSpritesList: MutableList<String>) {
        pokeSprites.addAll(pokeSpritesList)
        galleryViewPagerAdapter.notifyDataSetChanged()
    }

    private fun animateBackground(pokeColor: Int) {
        val backgroundColorAnimator = ObjectAnimator.ofObject(
            constraintLayoutDetail,
            "backgroundColor",
            ArgbEvaluator(),
            context?.let { ContextCompat.getColor(it, R.color.blue_poke) },
            context?.let { ContextCompat.getColor(it, pokeColor) })

        backgroundColorAnimator.duration = TIME_BACKGROUND_ANIMATION
        backgroundColorAnimator.start()
    }
}
