package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
    ): ImageView(context, attr, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = 0xFFFFFF
        private const val DEFAULT_BORDER_WIDTH = 2
    }
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH

    init{
        if(attr != null){
            val a = context.obtainStyledAttributes(attr, R.styleable.CircleImageView)
            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH)
            a.recycle()
        }
    }

    fun getBorderColor(): Int{
        return borderColor
    }

    fun setBorderColor(@ColorRes colorId: Int){
        borderColor = colorId
//        invalidate()
    }
    fun setBorderColor(hex: String){

    }
    @Dimension fun getBorderWidth(): Int{
        return borderWidth
    }
    fun setBorderWidth(@Dimension dp: Int){
        borderWidth = dp
    }



}