package  com.fly.tomato.common.ex

import android.text.TextUtils
import org.json.JSONObject

/**
 * Created by Zh.m on 2018/11/2
 * Describe:
 */

fun JSONObject.constructe(content: String): JSONObject? {
    if (TextUtils.isEmpty(content)) {
        return null
    }

    return JSONObject(content)
}

fun JSONObject.parseString(key: String): String? {
    if (has(key)) {
        return getString(key)
    }

    return null
}