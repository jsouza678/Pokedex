package com.souza.pokedetail.presentation.pokedetails

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.souza.extensions.gone
import com.souza.extensions.observeOnce
import com.souza.pokedetail.R
import com.souza.pokedetail.databinding.FragmentDetailsPokedexBinding
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.model.PokeVariety
import com.souza.pokedetail.utils.ColorFormat
import com.souza.pokedetail.utils.Constants.Companion.ABSOLUTE_ZERO
import com.souza.pokedetail.utils.Constants.Companion.FORMAT_ID_POKE_DISPLAY
import com.souza.pokedetail.utils.Constants.Companion.OFFSCREEN_DEFAULT_VIEW_PAGER
import com.souza.pokedetail.utils.Constants.Companion.TIME_BACKGROUND_ANIMATION
import com.souza.pokedetail.utils.cropPokeUrl
import java.util.Locale
import kotlin.collections.ArrayList
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokeDetailsFragment(
    private val pokemonId: Int,
    private val pokemonName: String
) : Fragment() {

    private lateinit var pokeNameTextView: TextView
    private lateinit var pokeIdTextView: TextView
    private lateinit var emptyPokeImage: ImageView
    private lateinit var constraintLayoutDetail: ConstraintLayout
    private lateinit var viewPagerDetail: ViewPager
    private lateinit var tabsViewPagerDetail: TabLayout
    private lateinit var tabsCarousel: TabLayout
    private var pokeSprites: MutableList<String> = mutableListOf()
    private val galleryViewPagerAdapter by inject<PokeDetailsGalleryAdapter> { parametersOf(pokeSprites) }
    private lateinit var viewPagerGallery: ViewPager
    private val viewModel by viewModel<PokeDetailsViewModel> { parametersOf(pokemonId) }

    @ExperimentalStdlibApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailsPokedexBinding.inflate(layoutInflater)

        pokeNameTextView = binding.textViewPokeNameDetails
        pokeIdTextView = binding.textViewPokeIdDetails
        constraintLayoutDetail = binding.layoutDetails
        viewPagerGallery = binding.imageSliderDetails
        viewPagerDetail = binding.fragmentContainerDetails
        emptyPokeImage = binding.imageViewNoImagesDetails
        tabsViewPagerDetail = binding.tabLayoutDetails
        tabsCarousel = binding.tabLayoutCarouselDetails
        val toolbar = binding.toolbarDetails

        pokeSprites = ArrayList()

        setToolbarBackButton(toolbar)
        setPokeAndIdText()
        initObserverCheckIfItsEvolutionPoke(viewPagerDetail, tabsViewPagerDetail)

        return binding.root
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

    private fun initObserverCheckIfItsEvolutionPoke(viewPagerDetails: ViewPager, tabsDetails: TabLayout) {
        viewModel.apply {
            this.isNormalPoke.observe(viewLifecycleOwner, Observer {
                initGalleryViewPager()
                initObserverNormalPoke(viewPagerDetails, tabsDetails)
            })
            this.isEvolutionPoke.observe(viewLifecycleOwner, Observer {
                initGalleryViewPager()
                initObserverEvolutionPoke(viewPagerDetails, tabsDetails)
            })
        }
    }

    private fun initObserverNormalPoke(viewPager: ViewPager, tabsDetails: TabLayout) {
        viewModel.apply {
            this.updateVariationsOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer { pokeVariety ->
                pokeVariety?.let { it -> showDataNormalPoke(it, viewPager, tabsDetails) }
            })
            this.updatePropertiesOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer {
                loadImages(it)
            })
        }
    }

    private fun initObserverEvolutionPoke(viewPager: ViewPager, tabsDetails: TabLayout) {
        viewModel.apply {
            this.updatePropertiesOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer {
                showDataEvolutionPoke(viewPager, tabsViewPagerDetail)
                loadImages(it)
            })
        }
    }

    private fun loadImages(pokeProperty: PokeProperty) {
        val imagesList = addSpritesToList(pokeProperty)
        addImagesToList(imagesList)
    }

    private fun showDataNormalPoke(pokeVariety: PokeVariety, viewPager: ViewPager, tabs: TabLayout) {
        val backgroundColor = ColorFormat.setColor(pokeVariety.color?.name, pokemonId)
        animateBackground(backgroundColor)
        val pokeChainUriPath = pokeVariety.evolutionChain?.url?.let { path -> cropPokeUrl(path) }
        val pokeChainId = pokeChainUriPath?.let { id -> Integer.parseInt(id) }

        val sectionsPagerAdapter =
            fragmentManager?.let { fragmentManager ->
                pokeChainId?.let { pokeChainId ->
                    PokeDetailsViewPagerAdapter(
                        fragmentManager, pokemonId, pokeChainId
                    )
                }
            }
        sectionsPagerAdapter?.let { item -> setViewPager(viewPager, item, tabs) }
        viewPager.offscreenPageLimit = OFFSCREEN_DEFAULT_VIEW_PAGER
    }

    private fun showDataEvolutionPoke(viewPager: ViewPager, tabs: TabLayout) {
        val sectionsPagerAdapterEvolution = fragmentManager?.let { fragmentManager ->
            PokeDetailsViewPagerAdapter(fragmentManager, pokemonId, ABSOLUTE_ZERO)
        }
        sectionsPagerAdapterEvolution?.let { item -> setViewPager(viewPager, item, tabs) }
        val backgroundColor = ColorFormat.setColor(getString(R.string.black_color_name), pokemonId)
        animateBackground(backgroundColor)
    }

    private fun setViewPager(viewPager: ViewPager, aSectionsPagerAdapterPoke: PokeDetailsViewPagerAdapter, tabs: TabLayout) {
        viewPager.adapter = aSectionsPagerAdapterPoke
        tabs.setupWithViewPager(viewPager)
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

        if (auxList.isNullOrEmpty()) {
            turnOffGalleryViewPager()
            turnOnNoPokeImage()
        }

        return auxList
    }

    private fun addImagesToList(pokeSpritesList: MutableList<String>) {
        pokeSprites.addAll(pokeSpritesList)
        galleryViewPagerAdapter.notifyDataSetChanged()
    }

    private fun animateBackground(pokeColor: Int) {
        val backgroundColorAnimator = ObjectAnimator.ofObject(
            constraintLayoutDetail,
            getString(R.string.animator_gallery_color_property),
            ArgbEvaluator(),
            context?.let { ContextCompat.getColor(it, R.color.blue_poke) },
            context?.let { ContextCompat.getColor(it, pokeColor) })

        backgroundColorAnimator.duration = TIME_BACKGROUND_ANIMATION
        backgroundColorAnimator.start()
    }

    private fun turnOnNoPokeImage() {
        viewPagerGallery.gone()
    }

    private fun turnOffGalleryViewPager() {
        viewPagerGallery.gone()
    }
}
