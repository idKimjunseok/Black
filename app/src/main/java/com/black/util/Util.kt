package com.black.util

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.black.R
import com.black.databinding.CellToastBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

object Util {

    fun View.onThrottleClick(action: (v: View) -> Unit) {
        val listener = View.OnClickListener { action(it) }
        setOnClickListener(OnThrottleClickListener(listener))
    }

    // with interval setting
    fun View.onThrottleClick(action: (v: View) -> Unit, interval: Long) {
        val listener = View.OnClickListener { action(it) }
        setOnClickListener(OnThrottleClickListener(listener, interval))
    }

    fun hideKeyboard(view: View) {
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun showKeyboard(view: View) {
        if (view.requestFocus()) {
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun String.showToast(context: Context) {
        this.showToast(context, Gravity.BOTTOM, Toast.LENGTH_SHORT)
    }


    fun String.showToast(context: Context, gravity: Int, duration: Int) {
        val viewDataBinding = CellToastBinding.inflate(LayoutInflater.from(context))
        viewDataBinding.toastText.text = this

        Toast(context).apply {
            view = viewDataBinding.root
            setDuration(duration)
            setGravity(gravity, 0, 16.dp)
        }.show()
    }

    fun Int.color(context: Context) =
        ContextCompat.getColor(context, this)

    fun Int.colorStateList(context: Context) =
        ContextCompat.getColorStateList(context, this)

    fun Int.drawable(context: Context) =
        ContextCompat.getDrawable(context, this)

    fun <T> ArrayList<T>.clearAndAddAll(items: ArrayList<T>) {
        this.clear()
        this.addAll(items)
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Float.dp: Float
        get() = (this * Resources.getSystem().displayMetrics.density)

    fun isNotNull(obj: Any?): Boolean {
        return obj != null
    }

    fun isEmpty(obj: Any?): Boolean {
        if (obj == null) return true
        if (obj is String) {
            return if (obj.toString() == "null") false else TextUtils.isEmpty(obj.toString().trim { it <= ' ' })
        }
        if (obj is Map<*, *>) return obj.isEmpty()
        if (obj is List<*>) return obj.isEmpty()
        return if (obj is Array<*>) obj.size == 0 else false
    }

    fun Context.webViewSetPath() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName(this)
            if(processName != packageName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

    fun getProcessName(context: Context): String? {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(info in manager.runningAppProcesses) {
            if(info.pid == android.os.Process.myPid()) {
                return info.processName
            }
        }
        return ""
    }

    /**
     * ?????? ??????
     * 100 -> 100m
     * 1000 -> 1.0km
     */
    fun getDistance(context: Context, distance: Int): String {
        if (distance > 1000) {
            var distance_km = String.format("%.1f", (distance.toDouble() / 1000))
            return distance_km.toString() + context.getString(R.string.distance_km)
        } else {
            return distance.toString() + context.getString(R.string.distance_m)
        }
    }

    /**
     * zip ??????.
     * zip ?????? ??? filter?????? ?????? Int ?????? 1??? array??? ?????????
     * map ?????? ?????? array??? ????????? ???
     */
    fun getSummary(): String {
        var summary = ""
        var summarydata = arrayOf("???", "???", "???", "???", "???", "???", "???")
        var onday = arrayOf(0, 1, 1, 0, 1, 0, 1)

        onday.zip(summarydata).filter {pair -> pair.first == 1 }.map {
            pair -> summary = summary.plus(if (summary.isEmpty()) "${pair.second}" else ", ${pair.second}")}
        return summary
    }

    /**
     * ?????? ?????? ??????
     * ???,???,???
     * ??? (????????? ?????????)
     *
     */
    fun setadd(s: String): String {
        var addr = ""
        val p1 = Pattern.compile("([???-???]+(???|???))")
        val p2 = Pattern.compile("([???-???]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(???|???|???|???|???|???|???|???))(^???|)")
        val m1: Matcher = p1.matcher(s)
        val m2: Matcher = p2.matcher(s)

        var count = 0
        var addrs = ArrayList<String>()

        while (m2.find()) {
            addrs.add(m2.group(1) + " ")
            count++
        }
        if (count > 2) {
            for (i in (count - 2) until addrs.size) {
                addr += addrs[i]
            }
        } else {
            for (i  in addrs) {
                addr += i
            }
        }
        return addr
    }

    /**
     * ?????? ??????
     * ????????? ??????.
     * ???: (?????????)
     *
     */
    fun setSearchaddr(s: String): String {
        //|???|???
        var addr = ""
        val p2 = Pattern.compile("([???-???]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(???|???|???|???))(^???|)")
        val m2: Matcher = p2.matcher(s)

        var count = 0
        var addrs = ArrayList<String>()

        while (m2.find()) {
            addrs.add(m2.group(1) + " ")
            count++
        }

        if (count > 2) {
            for (i in (count - 2) until addrs.size) {
                addr += addrs[i]
            }
        } else {
            for (i  in addrs) {
                addr += i
            }
        }
        return addr
    }
}