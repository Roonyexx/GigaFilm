package com.gigaprod.gigafilm.ui.custom

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CardStackLayoutManager : RecyclerView.LayoutManager() {

    private val maxVisibleCount = 3
    private val scaleFactor = 0.05f
    private val translationYFactor = 30

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val itemCount = itemCount.coerceAtMost(maxVisibleCount)
        for (i in itemCount - 1 downTo 0) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)

            val left = (getWidth() - width) / 2
            val top = (getHeight() - height) / 2

            layoutDecorated(view, left, top, left + width, top + height)

            val scale = 1 - i * scaleFactor
            val translationY = i * translationYFactor.toFloat()

            if (i == 0) {
                animateCard(view, scale, translationY)
            } else {
                view.scaleX = scale
                view.scaleY = scale
                view.translationY = translationY
            }
        }
    }

    private fun animateCard(view: View, scale: Float, translationY: Float) {
        view.animate()
            .scaleX(scale)
            .scaleY(scale)
            .translationY(translationY)
            .setDuration(300)
            .start()
    }

    override fun canScrollVertically(): Boolean = false
    override fun canScrollHorizontally(): Boolean = false
}
