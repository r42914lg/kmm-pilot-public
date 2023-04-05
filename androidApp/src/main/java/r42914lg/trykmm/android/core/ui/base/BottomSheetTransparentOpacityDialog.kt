package r42914lg.trykmm.android.core.ui.base

import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.NonNull
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * Base class for all Bottom Sheet Dialogs in the app
 * To ensure opacity implementations should:
 *      - override/implement onOpacityChanged() method
 *      - call openBsd() when view is created
 */

abstract class BottomSheetTransparentOpacityDialog : BottomSheetTransparentDialog() {

    // Actual peek height from bottom of screen in pixels
    private var peekPixelsFromBottom: Int = 0

    // Metrics used to determine display dimensions
    private val metrics: DisplayMetrics by lazy {
        val dMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dMetrics)
        dMetrics
    }

    /**
     * Should be called from onViewCreated
     * @param rootView - root layout
     */
    protected fun openBsd(rootView: View, peek: Float = PERCENT_OPEN_FROM_BOTTOM) {

        val bottomSheetBehavior = BottomSheetBehavior.from(rootView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        peekPixelsFromBottom = (metrics.heightPixels * peek).toInt()
        bottomSheetBehavior.peekHeight = peekPixelsFromBottom
        rootView.layoutParams.height = peekPixelsFromBottom

        rootView.requestFocus()

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, newState: Int) {}

            override fun onSlide(@NonNull view: View, slideOffset: Float) {
                val locationOnScreen: IntArray = intArrayOf(0, 0)
                view.getLocationOnScreen(locationOnScreen)
                onOpacityChanged(calculateAlpha(
                    metrics.heightPixels - locationOnScreen[1].toFloat()))
            }
        })
    }

    protected fun getDisplayPxlHeight() = metrics.heightPixels

    private fun calculateAlpha(heightFromBottom: Float): Float =
        heightFromBottom / peekPixelsFromBottom

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onOpacityChanged(0f)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onOpacityChanged(1f)
    }

    /**
     * Implementations should adjust opacity based on parameter value
     * @param alpha - opacity
     */
    abstract fun onOpacityChanged(alpha: Float)

    companion object {
        const val PERCENT_OPEN_FROM_BOTTOM = 0.98f
    }
}