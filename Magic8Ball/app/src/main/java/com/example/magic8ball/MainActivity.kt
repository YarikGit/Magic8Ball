package com.example.magic8ball

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.magic8ball.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.sqrt
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var preference: SharedPreferences? = null
    private var ballMode = 1
    private var getType = 1
    private var vibration = 1
    private var language = 2

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f
    private val sensitivity = 4
    private var isAsked = false
    private var isFirstShake = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.askButton.setOnClickListener {if (!isAsked) generateBall() }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)!!.registerListener(
            sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

    }

    private fun getSettings() {
        preference = this.getSharedPreferences("ballSettings", Context.MODE_PRIVATE)

        ballMode = preference!!.getInt("ballModeGroup", 1)
        getType = preference!!.getInt("getTypeGroup", 1)
        vibration = preference!!.getInt("vibrationGroup", 1)
        language = preference!!.getInt("languageGroup", 2)

        if (getType == 1) {
            binding.askButton.visibility = View.GONE
        } else {
            binding.askButton.visibility = View.VISIBLE
        }
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (getType == 1 && acceleration > sensitivity && !isAsked) {
                generateBall()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private fun vibratePhone() {
        if (vibration == 1) {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        300,
                        VibrationEffect.EFFECT_HEAVY_CLICK
                    )
                )
            } else {
                vibrator.vibrate(300)
            }
        }
    }

    private fun generateBall() {
        isAsked = true
        if (isFirstShake) {
            Handler(Looper.myLooper()!!).postDelayed({
                isFirstShake = false
                isAsked = false
            }, 1000)
            return
        }
        vibratePhone()
        binding.miniBallImageView.animate().alpha(1f).duration = 800

        Handler(Looper.myLooper()!!).postDelayed({
            binding.ballImageView.setImageResource(R.drawable.magic_8_ball)
            binding.miniBallImageView.animate().alpha(0f).duration = 800
            if (ballMode == 2) {
                when (Random.nextInt(6)) {
                    0 -> binding.ballImageView.setImageResource(R.drawable.b1_magic_8_ball)
                    1 -> binding.ballImageView.setImageResource(R.drawable.b2_magic_8_ball)
                    2 -> binding.ballImageView.setImageResource(R.drawable.b3_magic_8_ball)
                    3 -> binding.ballImageView.setImageResource(R.drawable.b4_magic_8_ball)
                    4 -> binding.ballImageView.setImageResource(R.drawable.b5_magic_8_ball)
                    5 -> binding.ballImageView.setImageResource(R.drawable.b6_magic_8_ball)
                    else -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball)
                }
            } else if (language == 2 && ballMode == 1) {
                when (Random.nextInt(20)) {
                    0 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_1)
                    1 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_2)
                    2 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_3)
                    3 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_4)
                    4 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_5)
                    5 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_6)
                    6 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_7)
                    7 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_8)
                    8 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_9)
                    9 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_10)
                    10 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_11)
                    11 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_12)
                    12 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_13)
                    13 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_14)
                    14 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_15)
                    15 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_16)
                    16 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_17)
                    17 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_18)
                    18 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_19)
                    19 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_20)
                    else -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball)
                }
            } else if (language == 1 && ballMode == 1) {
                when (Random.nextInt(20)) {
                    0 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_1)
                    1 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_2)
                    2 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_3)
                    3 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_4)
                    4 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_5)
                    5 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_6)
                    6 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_7)
                    7 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_8)
                    8 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_9)
                    9 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_10)
                    10 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_11)
                    11 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_12)
                    12 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_13)
                    13 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_14)
                    14 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_15)
                    15 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_16)
                    16 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_17)
                    17 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_18)
                    18 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_19)
                    19 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_20)
                    else -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball)
                }
            }else if (language == 1 && ballMode == 3) {
                when (Random.nextInt(2)) {
                    0 -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_yes)
                    else -> binding.ballImageView.setImageResource(R.drawable.u_magic_8_ball_no)
                }
            }else if (language == 2 && ballMode == 3) {
                when (Random.nextInt(2)) {
                    0 -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_yes)
                    else -> binding.ballImageView.setImageResource(R.drawable.magic_8_ball_no)
                }
            }

        }, 1000)
        Handler(Looper.myLooper()!!).postDelayed({
            isAsked = false
        }, 3000)

    }

    override fun onResume() {
        sensorManager?.registerListener(
            sensorListener, sensorManager!!.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            ), SensorManager.SENSOR_DELAY_NORMAL
        )

        getSettings()

        super.onResume()

    }

    override fun onStop() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        getSettings()
        setLanguage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setLanguage() {
        if (language == 1) {
            binding.askButton.text = Utils.UA_ASK_ME
        } else {
            binding.askButton.text = Utils.EN_ASK_ME
        }
    }

}