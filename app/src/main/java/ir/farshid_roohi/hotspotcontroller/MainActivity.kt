package ir.farshid_roohi.hotspotcontroller

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        hotspotSwitch.isChecked = hotspotStatus()

        hotspotSwitch.setOnCheckedChangeListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                Toast.makeText(this, "under api 26 worker", Toast.LENGTH_LONG).show()
            } else {
                configHotspot(it)
            }

        }
    }

    private fun hotspotStatus(): Boolean {
        val method = wifiManager::class.java.getMethod("isWifiApEnabled")
        method.isAccessible = true
        return (method.invoke(wifiManager) as Boolean)
    }

    private fun configHotspot(status: Boolean) {

        val wifiConfiguration: WifiConfiguration? = null

        if (!status) {
            wifiManager.isWifiEnabled = status
        }

        val method =
            wifiManager::class.java.getMethod("setWifiApEnabled", WifiConfiguration::class.java, Boolean::class.java)
        method.invoke(wifiManager, wifiConfiguration, status)
    }
}
