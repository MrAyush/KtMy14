package com.example.ayushgupta.ktmy14

import android.app.Fragment
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class WifiFrag : Fragment() {
    var s1: Switch? = null
    var btn5: Button? = null
    var btn6: Button? = null
    var lview: ListView? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.wifi_frag, container, false)
        val ll1: LinearLayout? = view?.findViewById(R.id.ll1)
        ll1?.visibility = View.VISIBLE
        val mWifi = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        s1 = view?.findViewById(R.id.s1)
        btn5 = view?.findViewById(R.id.btn5)
        btn6 = view?.findViewById(R.id.btn6)
        lview = view?.findViewById(R.id.lview)
        val wifi = mWifi.wifiState
        s1?.isChecked = !(wifi == 0 || wifi == 1)
        s1?.setOnCheckedChangeListener { _, isChecked ->
            mWifi.isWifiEnabled = isChecked
        }
        btn5?.setOnClickListener {
            val list = mWifi.scanResults
            val mlist = mutableListOf<String>()
            for (l in list) {
                mlist.add("${l.SSID}\n${l.frequency}")
            }
            val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, mlist)
            lview?.adapter = adapter
        }
        btn6?.setOnClickListener {
            val list = mWifi.configuredNetworks
            val mlist = mutableListOf<String>()
            for (l in list) {
                mlist.add("${l.SSID}\n${l.status}")
            }
            val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, mlist)
            lview?.adapter = adapter
        }
        return view!!
    }
}