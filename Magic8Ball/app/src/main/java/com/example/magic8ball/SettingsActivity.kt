package com.example.magic8ball

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.magic8ball.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private var ballMode = 1
    private var getType = 1
    private var vibration = 1
    private var language = 2

    private var preference: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setPreferenceSettings()
        setLanguage()

        binding.ballModeGroup.setOnCheckedChangeListener { _, _ -> savePreference() }
        binding.getTypeGroup.setOnCheckedChangeListener { _, _ -> savePreference() }
        binding.vibrationGroup.setOnCheckedChangeListener { _, _ -> savePreference() }
        binding.languageGroup.setOnCheckedChangeListener { _, _ ->
            savePreference()
            setLanguage()
        }

    }

    private fun setPreferenceSettings(){
        preference = this.getSharedPreferences("ballSettings", Context.MODE_PRIVATE)

        when(preference!!.getInt("ballModeGroup", 1)){
            2 -> binding.diceBallRadio.toggle()
            3 -> binding.yesBallRadio.toggle()
            else -> binding.eightBallRadio.toggle()
        }

        if(preference!!.getInt("getTypeGroup", 1) == 1) {
            binding.shakeModeRadio.isChecked = true
            binding.clickModeRadio.isChecked = false
        } else{
            binding.shakeModeRadio.isChecked = false
            binding.clickModeRadio.isChecked = true
        }

        if(preference!!.getInt("vibrationGroup", 1) == 1) {
            binding.onVibrationModeRadio.isChecked = true
            binding.offVibrationModeRadio.isChecked = false
        }else{
            binding.onVibrationModeRadio.isChecked = false
            binding.offVibrationModeRadio.isChecked = true

        }

        if(preference!!.getInt("languageGroup", 1) == 1) {
            binding.uaLanguageRadio.isChecked = true
            binding.enLanguageRadio.isChecked = false
        }else{
            binding.uaLanguageRadio.isChecked = false
            binding.enLanguageRadio.isChecked = true
        }

    }

    private fun savePreference() {
        validateSetting()
        val editor: SharedPreferences.Editor?
        editor = preference!!.edit()
        editor.putInt("ballModeGroup", ballMode)
        editor.putInt("getTypeGroup", getType)
        editor.putInt("vibrationGroup", vibration)
        editor.putInt("languageGroup", language)
        editor.apply()

    }

    private fun validateSetting(){
        ballMode = when (binding.ballModeGroup.checkedRadioButtonId){
            R.id.diceBallRadio -> 2
            R.id.yesBallRadio -> 3
            else -> 1
        }

        getType = if(binding.getTypeGroup.checkedRadioButtonId == R.id.shakeModeRadio) 1 else 2

        vibration = if(binding.vibrationGroup.checkedRadioButtonId == R.id.onVibrationModeRadio) 1 else 2

        language = if(binding.languageGroup.checkedRadioButtonId == R.id.enLanguageRadio) 2 else 1
    }

    private fun setLanguage(){
        validateSetting()
        if(language == 1){
            binding.ballModeTextView.text = Utils.UA_GROUP_1
            binding.eightBallRadio.text = Utils.UA_TYPE_1
            binding.diceBallRadio.text = Utils.UA_TYPE_2
            binding.yesBallRadio.text = Utils.UA_TYPE_3
            binding.getModeTextView.text = Utils.UA_SHAKE_OR_CLICK
            binding.shakeModeRadio.text = Utils.UA_SHAKE
            binding.clickModeRadio.text = Utils.UA_CLICK
            binding.vibrationModeTextView.text = Utils.UA_VIBRATION
            binding.onVibrationModeRadio.text = Utils.UA_VIBRATION_ON
            binding.offVibrationModeRadio.text = Utils.UA_VIBRATION_OFF
            binding.languageTextView.text = Utils.UA_LANGUAGE
        }else{
            binding.ballModeTextView.text = Utils.EN_GROUP_1
            binding.eightBallRadio.text = Utils.EN_TYPE_1
            binding.diceBallRadio.text = Utils.EN_TYPE_2
            binding.yesBallRadio.text = Utils.EN_TYPE_3
            binding.getModeTextView.text = Utils.EN_SHAKE_OR_CLICK
            binding.shakeModeRadio.text = Utils.EN_SHAKE
            binding.clickModeRadio.text = Utils.EN_CLICK
            binding.vibrationModeTextView.text = Utils.EN_VIBRATION
            binding.onVibrationModeRadio.text = Utils.EN_VIBRATION_ON
            binding.offVibrationModeRadio.text = Utils.EN_VIBRATION_OFF
            binding.languageTextView.text = Utils.EN_LANGUAGE
        }
    }


}