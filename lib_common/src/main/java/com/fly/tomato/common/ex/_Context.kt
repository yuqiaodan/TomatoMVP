@file:Suppress("unused")

package com.fly.tomato.common.ex

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.Toast
import java.util.*

/**
 * Note:
 *
 * @author Sens [2016/7/21]
 */

fun Context.px2dip(px: Float): Float = px / resources.displayMetrics.density + 0.5f

fun Context.px2dip(px: Int): Int = px2dip(px.toFloat()).toInt()

fun Context.dip2px(dp: Float): Float = dp * resources.displayMetrics.density + 0.5f

fun Context.dip2px(dp: Int): Int = dip2px(dp.toFloat()).toInt()

fun Context.sp2px(sp: Float): Float = sp * resources.displayMetrics.density

fun Context.sp2px(sp: Int): Int = sp2px(sp.toFloat()).toInt()

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Context.getService(name: String): T = getSystemService(name) as T

fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

/**
 * 获取屏幕的Size
 *
 * @return point.x:屏幕宽度 point.y:屏幕高度
 */
fun Context.getScreenSize(): Point {
    val manager: WindowManager = getService(Context.WINDOW_SERVICE)
    val p = Point()
    manager.defaultDisplay.getSize(p)

    return when (resources.configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> Point(Math.max(p.x, p.y), Math.min(p.x, p.y)) // 横屏
        else -> Point(Math.min(p.x, p.y), Math.max(p.x, p.y)) // 竖屏
    }
}

fun Context.getColor(context: Context, id: Int): Int {
    val version = Build.VERSION.SDK_INT
    return if (version >= 23) {
        ContextCompat.getColor(context, id)
    } else {
        context.resources.getColor(id)
    }
}

fun Context.addViewTreeObserver(view: View, action: () -> Unit) {
    view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            action()
        }

    })
}

fun Context.postDelay(run:()->Unit, delayMillis: Long) {
    Handler(Looper.getMainLooper()).postDelayed({ run() }, delayMillis)
}


fun getRandom(min: Int, max: Int): Int {
    val random = Random()
    return random.nextInt(max - min + 1) + min
}
