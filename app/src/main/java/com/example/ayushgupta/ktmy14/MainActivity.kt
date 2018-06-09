package com.example.ayushgupta.ktmy14

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var tv1: TextView? = null
    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv1 = findViewById(R.id.tv1)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        if (getPermission()) {
            btn1?.setOnClickListener {
                tv1?.visibility = View.VISIBLE
                val mLocation = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val result = checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, 1, 2)
                if (result == PackageManager.PERMISSION_GRANTED)
                    mLocation.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                mLocation.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000.toLong(), 1f, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        val str = "${location?.latitude}, ${location?.longitude}"
                        tv1?.text = str
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    }

                    override fun onProviderEnabled(provider: String?) {
                    }

                    override fun onProviderDisabled(provider: String?) {
                    }

                })
            }
            btn2?.setOnClickListener {
                val mNotify = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val mNotification = NotificationCompat.Builder(this, "")
                mNotification.setTicker("Hello, i'm a notification")
                mNotification.setContentTitle("Sample Notification")
                mNotification.setContentText("This is notification body")
                mNotification.setSmallIcon(R.drawable.ic_attach_money_black_24dp)
                val mIntent = Intent(this, MainActivity::class.java)
                val pIntent = PendingIntent.getActivity(this, 101, mIntent, 0)
                mNotification.setContentIntent(pIntent)
                mNotification.setAutoCancel(true)
                mNotification.addAction(R.drawable.ic_attach_money_black_24dp, "Hello human", pIntent)
                mNotify.notify(102, mNotification.build())
            }
            btn3?.setOnClickListener {
                val mSensor = getSystemService(Context.SENSOR_SERVICE) as SensorManager
                val sensor = mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                mSensor.registerListener(object : SensorEventListener {
                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    }

                    override fun onSensorChanged(event: SensorEvent?) {
                        tv1?.visibility = View.VISIBLE
                        val str = "X: ${event?.values?.get(0)} Y: ${event?.values?.get(1)}"
                        tv1?.text = str
                    }

                },sensor,SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }

    private fun getPermission(): Boolean {
        val status = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val status1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (status == PackageManager.PERMISSION_DENIED || status1 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION), 100)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        } else {
            recreate()
        }
    }
}
