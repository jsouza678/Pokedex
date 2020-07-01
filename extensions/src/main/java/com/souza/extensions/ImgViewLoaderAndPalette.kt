package com.souza.extensions

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.graphics.alpha
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.souza.utils.Constants.Companion.IMAGE_MAX_HEIGHT
import com.souza.utils.Constants.Companion.IMAGE_MAX_WIDTH

private var placeHolderId: Int = R.drawable.place_holder
private var errorImageId: Int = R.drawable.error_image

fun ImageView.loadImageUrlAndCard(
    url: String? = null,
    paletteCard: MaterialCardView,
    onLoadCompleted: () -> Unit = {},
    onError: () -> Unit = {}
) {
    val requestBuilder = Glide.with(context)
        .`as`(Drawable::class.java)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onError()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadCompleted()
                return false
            }
        })

    placeHolderId.let {
        requestBuilder
            .placeholder(placeHolderId)
            .override(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT)
            .error(errorImageId)
    }

    requestBuilder
        .load(url)
        .listener(
            GlidePalette.with(url)
                .use(BitmapPalette.Profile.MUTED_LIGHT)
                .intoCallBack { palette ->
                    val rgb = palette?.dominantSwatch?.rgb
                    if (rgb != null) {
                        val red = Color.red(rgb)
                        val green = Color.green(rgb)
                        val blue = Color.blue(rgb)
                        val alpha = 204

                        paletteCard.setCardBackgroundColor(Color.argb(alpha, red, green, blue))
                    }
                }
                .crossfade(true))
        .into(this)
}
