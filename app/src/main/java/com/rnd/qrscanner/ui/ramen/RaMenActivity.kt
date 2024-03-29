package com.rnd.qrscanner.ui.ramen

import android.content.Intent
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import com.rnd.qrscanner.data.entities.MealTickets
import com.rnd.qrscanner.data.entities.UserTicket
import com.rnd.qrscanner.data.remote.userTicket.UserTicketService
import com.rnd.qrscanner.databinding.ActivityKimbapBinding
import com.rnd.qrscanner.databinding.ActivityRamenBinding
import com.rnd.qrscanner.ui.BaseActivity
import com.rnd.qrscanner.ui.main.MainView
import org.json.JSONObject
import java.util.*


class RaMenActivity: BaseActivity<ActivityRamenBinding>(ActivityRamenBinding::inflate) , MainView{
   // private lateinit var navHostFragment: NavHostFragment

    private lateinit var tts: TextToSpeech

    override fun initAfterBinding() {
        clickBtn()
    }


    fun clickBtn(){

        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Qr Code Reader")
        integrator.setCameraId(1)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.setOrientationLocked(false); //세로
        integrator.initiateScan()

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.contents != null) {

                Log.d(
                    "result.contents",
                    "Scanned : ${result.contents} format: ${result.formatName}"
                )
                Log.d("result.contents", result.contents)
                Log.d("result.formatName", result.formatName)
            }

        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        val json = JSONObject(result.contents)
        Log.d("json", json.toString())

        val mealTicketsList = arrayListOf<MealTickets>()


        var mealTickets  = MealTickets(json.getInt("mealTypeIdx"), json.getInt("amount"))

        mealTicketsList.add(mealTickets)

        Log.d("mealTickets", mealTickets.toString())

        var userTicket = UserTicket(json.getInt("userIdx"),json.getString("date"), mealTicketsList)
        Log.d("userTicket", userTicket.toString())
        if(json.getInt("mealTypeIdx") == 6) {
            UserTicketService.useUserTicket(this, userTicket)
        } else if (json.getInt("mealTypeIdx") == 10){
            mealTickets  = MealTickets(6, json.getInt("amount"))

            mealTicketsList[0] = mealTickets

            Log.d("mealTickets" +
                    "", mealTickets.toString())

            userTicket = UserTicket(json.getInt("userIdx"),json.getString("date"), mealTicketsList)
            Log.d("userTicket", userTicket.toString())
            UserTicketService.useUserPoint(this, userTicket)
        }  else {

            tts = TextToSpeech(this) {status ->
                if(status == TextToSpeech.SUCCESS){
                    val speechResult = tts.setLanguage((Locale.getDefault()))

                    if(speechResult == TextToSpeech.LANG_MISSING_DATA || speechResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("blog", "Error!")
                    } else {
                        tts.speak(
                            "식권이 라면이 아닙니다.",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "Hello Speech"
                        )
                    }
                } else {
                    Log.i("blog", "Error!")
                }
            }

            val handler = Handler()
            handler.postDelayed(Runnable {
                startActivityWithClear(RaMenActivity::class.java)
            }, 3000) //딜레이 타임 조절
        }
        //super.onActivityResult()
    }

    override fun useUserTicketLoading() {
        binding.mainLoadingPb.visibility = View.VISIBLE
    }

    override fun useUserTicketSuccess() {
        binding.mainLoadingPb.visibility = View.GONE

        tts = TextToSpeech(this) {status ->
            if(status == TextToSpeech.SUCCESS){
                val speechResult = tts.setLanguage((Locale.getDefault()))

                if(speechResult == TextToSpeech.LANG_MISSING_DATA || speechResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("blog", "Error!")
                } else {
                    tts.speak(
                        "라면입니다.",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "Hello Speech"
                    )
                }
            } else {
                Log.i("blog", "Error!")
            }
        }

        val handler = Handler()
        handler.postDelayed(Runnable {
            startActivityWithClear(RaMenActivity::class.java)
        }, 3000) //딜레이 타임 조절
    }

    override fun useUserTicketFailure(code: Int, message: String) {
        if(code == 2040){
            tts = TextToSpeech(this) {status ->
                if(status == TextToSpeech.SUCCESS){
                    val speechResult = tts.setLanguage((Locale.getDefault()))

                    if(speechResult == TextToSpeech.LANG_MISSING_DATA || speechResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("blog", "Error!")
                    } else {
                        tts.speak(
                            "포인트가 부족합니다.",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "Hello Speech"
                        )
                    }
                } else {
                    Log.i("blog", "Error!")
                }
            }
        }
        binding.mainLoadingPb.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}