package com.black.util

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.black.R

object TextUtil {

    private val NONE_BREAK_TEXT =
        arrayOf(
            arrayOf(" ", "\u00A0"),
            arrayOf("-", "\u2011"),
            arrayOf("/", "\u2044")
        )

    /**
     * 줄바꿈 글자 단위.
     */
    fun getCUnitText(text: String): String? {
        var text = text
        try {
            for (strings in NONE_BREAK_TEXT) {
                text = text.replace(strings[0].toRegex(), strings[1])
            }
        } catch (e: Exception) {
        }
        return text
    }

    /**
     * 지정 텍스트 볼트 처리.
     */
    fun setSpannableBoldText(
        textView: TextView?,
        color: Int,
        vararg args: String
    ) {
        if (textView == null || args == null) {
            return
        }
        val message = textView.text.toString()
        var startIndex = 0
        var endIndex = 0
        val spannableString = SpannableString(message)
        for (i in args.indices) {
            val str = args[i]
            if (!message.contains(str)) {
                return
            }
            startIndex = message.indexOf(str)
            endIndex = startIndex + str.length
            spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, 0)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.text = spannableString
    }

    /**
     * 지정 텍스트 컬러 바꿈 처리.
     */
    fun setSpannableColorText(
        textView: TextView?,
        color: Int,
        vararg args: String
    ) {
        if (textView == null || args == null) {
            return
        }
        val message = textView.text.toString()
        var startIndex = 0
        var endIndex = 0
        val spannableString = SpannableString(message)
        for (i in args.indices) {
            val str = args[i]
            if (!message.contains(str)) {
                return
            }
            startIndex = message.indexOf(str)
            endIndex = startIndex + str.length
            spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, 0)
        }
        textView.text = spannableString
    }


    /**
     * hteml 문자 처리
     */
    fun fromHtml(source: String): Spanned? {
        return try {
            val convert =
                source.replace("span style=\"color:", "font color=\"").replace("\\", "")
                    .replace("\"", "'").replace("</span>", "</font>")
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Html.fromHtml(convert)
            } else Html.fromHtml(convert, Html.FROM_HTML_MODE_LEGACY)
        } catch (e: Exception) {
            SpannableString("")
        }
    }

    /**
     * 가격 정보 관련.
     * 숫자, 한글 멀티 폰트 처리.
     */
    fun NumberFontText(context: Context, textView: TextView, number: String, won: String) {

        val numberfont = ResourcesCompat.getFont(context, R.font.roboto_medium)
        val wonfont = ResourcesCompat.getFont(context, R.font.notosanskr_medium)

        var text = SpannableStringBuilder(number+won)
        text.setSpan(CustomTypefaceSpan("", numberfont!!), 0, number.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        text.setSpan(CustomTypefaceSpan("", wonfont!!), number.length, text.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        textView.text = text
    }

    class CustomTypefaceSpan(family: String?, private val newType: Typeface) : TypefaceSpan(family) {

        override fun updateDrawState(ds: TextPaint) {
            applyCustomTypeFace(ds, newType)
        }

        override fun updateMeasureState(paint: TextPaint) {
            applyCustomTypeFace(paint, newType)
        }

        companion object {
            private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
                val oldStyle: Int
                val old = paint.typeface
                oldStyle = old?.style ?: 0
                val fake = oldStyle and tf.style.inv()
                if (fake and Typeface.BOLD != 0) {
                    paint.isFakeBoldText = true
                }
                if (fake and Typeface.ITALIC != 0) {
                    paint.textSkewX = -0.25f
                }
                paint.typeface = tf
            }
        }
    }

}