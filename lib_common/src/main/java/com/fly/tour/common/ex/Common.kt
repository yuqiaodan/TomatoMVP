@file:Suppress("unused")

package com.fly.tour.common.ex

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import java.io.*


/**
 * Note:
 *
 * @author Sens [2016/7/21]
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> View.getView(id: Int): T = this.findViewById<T>(id)

@Suppress("UNCHECKED_CAST")
fun <T : View> Activity.getView(id: Int): T = this.findViewById<T>(id)

inline fun bg(crossinline action: Thread.() -> Unit) {
    Thread { Thread.currentThread().action() }.start()
}

inline fun fg(crossinline action: () -> Unit) {
    Looper.getMainLooper().run { if (Looper.myLooper() == this) action() else Handler(this).post { action() } }
}

fun threadSleepEx(millis: Long, ensureSleepTime: Boolean = true) {
    val startTime = System.currentTimeMillis()
    var millisTime = millis
    while (millisTime > 0) {
        try {
            Thread.sleep(millisTime)
            break
        } catch (e: Exception) {
            if (ensureSleepTime) {
                millisTime -= (System.currentTimeMillis() - startTime)
            } else {
                break
            }
        }
    }
}

fun getColor(context: Context, id: Int): Int {
    val version = Build.VERSION.SDK_INT
    return if (version >= 23) {
        ContextCompat.getColor(context, id)
    } else {
        context.resources.getColor(id)
    }
}

fun <T> serial(t: T, file: File) {
    var out: ObjectOutputStream? = null
    try {
        out = ObjectOutputStream(FileOutputStream(file))
        out.writeObject(t)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            out?.closeEx()
        } catch (e: Exception) {
        }
    }
}

fun <T> deserial(file: File): T? {
    var ins: ObjectInputStream? = null
    try {
        ins = ObjectInputStream(FileInputStream(file))
        return ins.readObject() as T
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            ins.closeEx()
        } catch (e: Exception) {
        }
    }

    return null
}
