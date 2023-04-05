package r42914lg.trykmm.android.ui.main.mainPage.address

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ListAddressDecorator(context: Context, resId: Int) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable = ContextCompat.getDrawable(context, resId)!!

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val dividerLeft = 0

        val dividerRight: Int = parent.width - 16

        for (i in 0 until parent.childCount) {
            if (i != parent.childCount - 1) {
                val child: View = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop: Int = child.bottom + params.bottomMargin + 18
                val dividerBottom: Int = dividerTop + mDivider.intrinsicHeight
                mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                mDivider.draw(c)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = 100
        }
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = 15
        }
    }
}