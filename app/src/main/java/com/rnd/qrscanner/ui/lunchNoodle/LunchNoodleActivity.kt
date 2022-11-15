package com.rnd.qrscanner.ui.lunchNoodle

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.rnd.qrscanner.data.entities.MealTickets
import com.rnd.qrscanner.data.entities.UserTicket
import com.rnd.qrscanner.data.remote.userTicket.UserTicketService
import com.rnd.qrscanner.databinding.ActivityLunchnoodleBinding
import com.rnd.qrscanner.databinding.ActivityMainBinding
import com.rnd.qrscanner.ui.BaseActivity
import com.rnd.qrscanner.ui.main.MainView
import org.json.JSONObject
import java.util.*


class LunchNoodleActivity: BaseActivity<ActivityLunchnoodleBinding>(ActivityLunchnoodleBinding::inflate) , MainView{
   // private lateinit var navHostFragment: NavHostFragment

    private lateinit var tts: TextToSpeech

    override fun initAfterBinding() {
//        navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//        val navController: NavController = navHostFragment.findNavController()
//
//        binding.mainBottomNavigation.setupWithNavController(navController)
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
                Toast.makeText(
                    this, "Scanned : ${result.contents} format: ${result.formatName}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(
                    "result.contents",
                    "Scanned : ${result.contents} format: ${result.formatName}"
                )
                Log.d("result.contents", result.contents)
                Log.d("result.formatName", result.formatName)
            }
//            if(result.barcodeImagePath != null) {
//                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
//                binding.scannedBitmap.setImageBitmap((bitmap))
//            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        val json = JSONObject(result.contents)
        Log.d("json", json.toString())

        val mealTicketsList = arrayListOf<MealTickets>()


        val mealTickets  = MealTickets(json.getInt("mealTypeIdx"), json.getInt("amount"))

        mealTicketsList.add(mealTickets)

        Log.d("mealTickets", mealTickets.toString())

        val userTicket = UserTicket(json.getInt("userIdx"),json.getString("date"), mealTicketsList)
        Log.d("userTicket", userTicket.toString())
        UserTicketService.useUserTicket(this, userTicket)

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
                        "석식입니다.",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "Hello Speech"
                    )
                }
            } else {
                Log.i("blog", "Error!")
            }
        }

        startActivityWithClear(LunchNoodleActivity::class.java)
    }

    override fun useUserTicketFailure(code: Int, message: String) {
        binding.mainLoadingPb.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }
}