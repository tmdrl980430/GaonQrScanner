package com.rnd.qrscanner.ui.main

import android.bluetooth.BluetoothAdapter.ERROR
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.rnd.qrscanner.data.entities.MealTickets
import com.rnd.qrscanner.data.entities.UserTicket
import com.rnd.qrscanner.data.remote.userTicket.UserTicketService
import com.rnd.qrscanner.databinding.ActivityMainBinding
import com.rnd.qrscanner.ui.BaseActivity
import com.rnd.qrscanner.ui.breakfast.BreakFastActivity
import com.rnd.qrscanner.ui.dinner.DinnerActivity
import com.rnd.qrscanner.ui.lunch.LunchActivity
import com.rnd.qrscanner.ui.lunchKorea.LunchKoreaActivity
import com.rnd.qrscanner.ui.lunchNoodle.LunchNoodleActivity
import org.json.JSONObject
import java.util.*


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), View.OnClickListener{
   // private lateinit var navHostFragment: NavHostFragment

    private lateinit var tts: TextToSpeech

    override fun initAfterBinding() {
        binding.mainBreakFastBtn.setOnClickListener(this)
        binding.mainLunchBtn.setOnClickListener(this)
        binding.mainLunchKoreaBtn.setOnClickListener(this)
        binding.mainLunchNoodleBtn.setOnClickListener(this)
        binding.mainDinnerBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.mainBreakFastBtn -> startNextActivity(BreakFastActivity::class.java)
            binding.mainLunchBtn -> startNextActivity(LunchActivity::class.java)
            binding.mainLunchKoreaBtn -> startNextActivity(LunchKoreaActivity::class.java)
            binding.mainLunchNoodleBtn -> startNextActivity(LunchNoodleActivity::class.java)
            binding.mainDinnerBtn -> startNextActivity(DinnerActivity::class.java)
        }
    }



}