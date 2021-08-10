package com.fly.tomato.common.util

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.fly.tomato.common.BaseApplication
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

/**
 * 保存图片工具类 将bitmap对象保存到本地相册
 *
 **/

class SaveImageUtil {

    //读写权限！
    companion object {
        val instance: SaveImageUtil by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { SaveImageUtil() }
    }

    //保存图片的文件夹地址
    var appDir: File? = null
    private val TAG = "图片保存"
    val context = BaseApplication.context
    var mediaScanIntent: Intent? = null
    val saveSucCode = 2211

    //检查保存的文件夹是否存在 不存在则创建一个
    private fun checkDir() {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            //如果有外部内存卡可进行读写 则建在外部内存卡上
            appDir = File(
                Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_PICTURES + File.separator + "Images"
            )
            if (!appDir!!.exists()) {
                appDir!!.mkdir()
            }
        } else {//否则将文件夹建在 APP内部存储上
            appDir =
                File(context.filesDir.absolutePath + File.separator + "Images")
            if (!appDir!!.exists()) {
                appDir!!.mkdir()
            }
        }
        Log.d(TAG, "图片文件夹地址${appDir?.absolutePath}")
    }

    //保存bitmap到指定文件夹 并发出广播通知系统刷新媒体库
    fun saveBitmap(bitmap: Bitmap, imageName: String) {
        //正在保存
        checkDir()

        val file = File(appDir, "$imageName.jpg")
        //准备好发出广播 通知系统媒体 刷新相册 在相册中显示出图片
        mediaScanIntent =
            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file))

        Log.d(TAG, "图片地址${file.absolutePath}")
        thread {
            try {
                val fileOutputStream = FileOutputStream(file)
                /**
                 * quality:100
                 * 为不压缩
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
                val msg = Message()
                msg.what = saveSucCode
                msg.obj = file.absolutePath
                handler.sendMessage(msg)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.d(TAG, "保存失败1${e.message}")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d(TAG, "保存失败2${e.message}")
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        //接收信息
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //判断信息识别码 根据不同的识别码进行不同动作
            when (msg.what) {
                saveSucCode -> {
                    context.sendBroadcast(mediaScanIntent)
                    val path: String? = msg.obj as? String
                    //Toast.makeText(context, "${App.context.getText(R.string.save_success)} ${App.context.getText(R.string.image_path)}:$path", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}