package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorLight(context: Context) : SensorEventListener {
    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var mLight: Sensor? = null
    var lightValue = 0f

    init {
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        mLight?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            lightValue = event.values[0]
        }
    }

    fun removeListener() {
        sensorManager.unregisterListener(this)
    }
}