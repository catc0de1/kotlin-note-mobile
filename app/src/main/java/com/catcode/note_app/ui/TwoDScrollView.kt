package com.catcode.note_app.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.View
import android.view.ViewConfiguration

class TwoDScrollView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

  private var lastX = 0f
  private var lastY = 0f

  private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
  private var isDragging = false

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    val width = MeasureSpec.getSize(widthMeasureSpec)
    val height = MeasureSpec.getSize(heightMeasureSpec)

    val child = getChildAt(0)

    val childWidthSpec =
      MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
    val childHeightSpec =
      MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)

    child.measure(childWidthSpec, childHeightSpec)

    setMeasuredDimension(width, height)
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    val child = getChildAt(0)
    child.layout(0, 0, child.measuredWidth, child.measuredHeight)
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    when (event.actionMasked) {
      MotionEvent.ACTION_DOWN -> {
        lastX = event.x
        lastY = event.y
        return true
      }
      MotionEvent.ACTION_MOVE -> {
        val dx = (lastX - event.x).toInt()
        val dy = (lastY - event.y).toInt()

        val child = getChildAt(0)

        val maxScrollX = (child.measuredWidth - width).coerceAtLeast(0)
        val maxScrollY = (child.measuredHeight - height).coerceAtLeast(0)

        val newScrollX = (scrollX + dx).coerceIn(0, maxScrollX)
        val newScrollY = (scrollY + dy).coerceIn(0, maxScrollY)

        scrollTo(newScrollX, newScrollY)

        lastX = event.x
        lastY = event.y
        return true
      }
    }
    return super.onTouchEvent(event)
  }

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    when (ev.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            lastX = ev.x
            lastY = ev.y
            isDragging = false
            return false
        }

        MotionEvent.ACTION_MOVE -> {
            val dx = kotlin.math.abs(ev.x - lastX)
            val dy = kotlin.math.abs(ev.y - lastY)

            if (dx > touchSlop || dy > touchSlop) {
                isDragging = true
                parent.requestDisallowInterceptTouchEvent(true)
                return true
            }
        }
    }
    return false
  }
}
