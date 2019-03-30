package ir.farshid_roohi.hotspotcontroller

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by Farshid Roohi.
 * HotspotController | Copyrights 3/31/19.
 */
class App : Application() {
    companion object {
      @SuppressLint("StaticFieldLeak")
      lateinit var  context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}