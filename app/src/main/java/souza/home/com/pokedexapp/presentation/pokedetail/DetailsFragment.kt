package souza.home.com.pokedexapp.presentation.pokedetail

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

class DetailsFragment(private val pokeId: Int, private val pokeName: String) : Fragment() {

    private lateinit var tvPokeName: TextView
    private lateinit var tvPokeId: TextView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout
    private var mImages: MutableList<String> = mutableListOf()
    private val galleryViewPagerAdapter by inject<DetailsGalleryAdapter> { parametersOf(mImages) }
    private lateinit var viewPagerGallery: ViewPager
    private val viewModel by viewModel<DetailsViewModel> { parametersOf(pokeId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_pokedex, container, false)
        bindViews(view)
        viewPager = view.findViewById(R.id.fragment_container_details)
        tabs = view.findViewById<TabLayout>(R.id.tab_layout_details)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_details)
        mImages = ArrayList()

        setToolbarBackButton(toolbar)
        setPokeAndIdText()
        initGalleryViewPager()
        // initObserverStatus(viewPager, tabs)

        if (pokeId> LIMIT_NORMAL_POKES) { showDataEvolutionPoke(viewPager, tabs)
        } else { bindRequestVarietiesStatus(VarietiesPokedexStatus.DONE, viewPager, tabs) }

        if (pokeId> LIMIT_NORMAL_POKES) { showDataEvolutionPoke(viewPager, tabs)
            initObserverData(viewModel, viewPager, tabs)
        } else { bindRequestPropertiesStatus(PropertiesPokedexStatus.DONE, viewPager, tabs) }

        return view
    }

    private fun bindViews(view: View) {
        tvPokeName = view.findViewById(R.id.text_view_poke_name_details)
        tvPokeId = view.findViewById(R.id.text_view_poke_id_details)
        constraintLayout = view.findViewById(R.id.layout_details)
        viewPagerGallery = view.findViewById(R.id.image_slider_details)
    }

    private fun setToolbarBackButton(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun setPokeAndIdText() {
        tvPokeName.text = pokeName.capitalize()
        val textId = FORMAT_ID_POKE_DISPLAY.format(pokeId)
        tvPokeId.text = context?.resources?.getString(R.string.text_view_placeholder_hash, textId)
    }

    private fun initGalleryViewPager() {
        viewPagerGallery.adapter = galleryViewPagerAdapter
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
            // In case of ERROR. Runs normally, because it has cache on some pokes.
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
            // In case of ERROR. Runs normally, because it has cache on some pokes.
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

    private fun loadImages(it: PokeProperty) {
        val imagesList = addSpritesToList(it)
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

    private fun showDataNormalPoke(it: PokeVariety, viewPager: ViewPager, tabs: TabLayout) {
        val backgroundColor = ColorFormat.setColor(it.color?.name, pokeId)
        animateBackground(backgroundColor)
        val pokeChainUriPath = it.evolution_chain?.url?.let { path -> cropPokeUrl(path) }
        val pokeChainId = pokeChainUriPath?.let { id -> Integer.parseInt(id) }

        val sectionsPagerAdapter =
            fragmentManager?.let { fm ->
                pokeChainId?.let { it ->
                    DetailsViewPagerAdapter(
                        fm, pokeId, it
                    )
                }
            }
        sectionsPagerAdapter?.let { item -> setViewPager(viewPager, item, tabs) }
        viewPager.offscreenPageLimit = OFFSCREEN_DEFAULT_VIEW_PAGER
    }

    private fun showDataEvolutionPoke(viewPager: ViewPager, tabs: TabLayout) {
        val sectionsPagerAdapterEvolution = fragmentManager?.let { fm ->
            DetailsViewPagerAdapter(fm, pokeId, 0)
        }
        sectionsPagerAdapterEvolution?.let { item -> setViewPager(viewPager, item, tabs) }
        val backgroundColor = ColorFormat.setColor(getString(R.string.black_color_name), pokeId)
        animateBackground(backgroundColor)
    }

    private fun addSpritesToList(listResult: PokeProperty): MutableList<String> {
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

    private fun addImagesToList(it: MutableList<String>) {
        mImages.addAll(it)
        galleryViewPagerAdapter.notifyDataSetChanged()
    }

    private fun animateBackground(colorV: Int) {
        val backgroundColorAnimator = ObjectAnimator.ofObject(
            constraintLayout,
            "backgroundColor",
            ArgbEvaluator(),
            context?.let { ContextCompat.getColor(it, R.color.blue_poke) },
            context?.let { ContextCompat.getColor(it, colorV) })

        backgroundColorAnimator.duration = TIME_BACKGROUND_ANIMATION
        backgroundColorAnimator.start()
    }
}
