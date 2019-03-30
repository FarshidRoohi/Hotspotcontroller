package ir.farshid_roohi.hotspotcontroller

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.widget.RemoteViews


/**
 * Created by Farshid Roohi.
 * HotspotController | Copyrights 3/31/19.
 */
class HotspotWidget : AppWidgetProvider() {



    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds)


        appWidgetIds!!.forEach {

            val remoteViews = RemoteViews(context!!.packageName, R.layout.hotspot_widget)
            if (!hotspotStatus()) {
                remoteViews.setImageViewResource(R.id.imgStatus, R.drawable.hotspot_on)
                remoteViews.setTextViewText(R.id.txtStatus, "")
                configHotspot(true)
            } else {
                remoteViews.setImageViewResource(R.id.imgStatus, R.drawable.hotspot_off)
                remoteViews.setTextViewText(R.id.txtStatus, "")
                configHotspot(false)

            }

            val intent = Intent(context, HotspotWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteViews.setOnClickPendingIntent(R.id.txtStatus, pendingIntent)
            appWidgetManager!!.updateAppWidget(it, remoteViews)
        }

    }

    private fun hotspotStatus(): Boolean {
        val wifiManager = App.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val method = wifiManager::class.java.getMethod("isWifiApEnabled")
        method.isAccessible = true
        return (method.invoke(wifiManager) as Boolean)
    }

    private fun configHotspot(status: Boolean) {
        val wifiManager = App.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val wifiConfiguration: WifiConfiguration? = null

        if (!status) {
            wifiManager.isWifiEnabled = status
        }

        val method =
            wifiManager::class.java.getMethod("setWifiApEnabled", WifiConfiguration::class.java, Boolean::class.java)
        method.invoke(wifiManager, wifiConfiguration, status)
    }

}