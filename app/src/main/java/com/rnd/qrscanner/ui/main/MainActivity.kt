package com.rnd.qrscanner.ui.main

import android.speech.tts.TextToSpeech
import android.view.View
import com.rnd.qrscanner.databinding.ActivityMainBinding
import com.rnd.qrscanner.ui.BaseActivity
import com.rnd.qrscanner.ui.breakfast.BreakFastActivity
import com.rnd.qrscanner.ui.dinner.DinnerActivity
import com.rnd.qrscanner.ui.chicken.ChickenActivity
import com.rnd.qrscanner.ui.kimbap.KimBapActivity
import com.rnd.qrscanner.ui.lunch.LunchActivity
import com.rnd.qrscanner.ui.lunchKorea.LunchKoreaActivity
import com.rnd.qrscanner.ui.hotdog.HotdogActivity
import com.rnd.qrscanner.ui.lunchNoodle.LunchNoodleActivity
import com.rnd.qrscanner.ui.ramen.RaMenActivity


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), View.OnClickListener{
   // private lateinit var navHostFragment: NavHostFragment

    private lateinit var tts: TextToSpeech

    override fun initAfterBinding() {
        binding.mainBreakFastBtn.setOnClickListener(this)
        binding.mainLunchBtn.setOnClickListener(this)
        binding.mainLunchKoreaBtn.setOnClickListener(this)
        binding.mainLunchNoodleBtn.setOnClickListener(this)
        binding.mainDinnerBtn.setOnClickListener(this)
        binding.mainKimBapBtn.setOnClickListener(this)
        binding.mainRamenBtn.setOnClickListener(this)
        binding.mainHotDogBtn.setOnClickListener(this)
        binding.mainChickenBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.mainBreakFastBtn -> startNextActivity(BreakFastActivity::class.java)
            binding.mainLunchBtn -> startNextActivity(LunchActivity::class.java)
            binding.mainLunchKoreaBtn -> startNextActivity(LunchKoreaActivity::class.java)
            binding.mainLunchNoodleBtn -> startNextActivity(LunchNoodleActivity::class.java)
            binding.mainDinnerBtn -> startNextActivity(DinnerActivity::class.java)
            binding.mainRamenBtn -> startNextActivity(RaMenActivity::class.java)
            binding.mainKimBapBtn -> startNextActivity(KimBapActivity::class.java)
            binding.mainHotDogBtn -> startNextActivity(HotdogActivity::class.java)
            binding.mainChickenBtn -> startNextActivity(ChickenActivity::class.java)
        }
    }



}