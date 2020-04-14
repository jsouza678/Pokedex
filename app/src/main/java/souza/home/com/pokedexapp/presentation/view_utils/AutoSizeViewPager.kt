package souza.home.com.pokedexapp.presentation.view_utils

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO

class AutoSizeViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var heightSpec = heightMeasureSpec

        var height = ABSOLUTE_ZERO
        for (i in ABSOLUTE_ZERO until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(ABSOLUTE_ZERO, MeasureSpec.UNSPECIFIED))
            val childHeight = child.measuredHeight
            if (childHeight > height) height = childHeight
        }
        if (height != ABSOLUTE_ZERO) {
            heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}
