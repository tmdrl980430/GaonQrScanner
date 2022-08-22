package com.rnd.qrscanner.ui.main

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.rnd.qrscanner.data.entities.MealTickets
import com.rnd.qrscanner.data.entities.UserTicket
import com.rnd.qrscanner.data.remote.userTicket.UserTicketService
import com.rnd.qrscanner.databinding.ActivityMainBinding
import com.rnd.qrscanner.ui.BaseActivity
import org.json.JSONObject


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) , MainView{
   // private lateinit var navHostFragment: NavHostFragment

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
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.setOrientationLocked(false); //세로
        integrator.initiateScan()

    }

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
        Log.d("useUserTicketSuccess", "useUserTicketSuccess")
        startActivityWithClear(MainActivity::class.java)
    }

    override fun useUserTicketFailure(code: Int, message: String) {
        binding.mainLoadingPb.visibility = View.GONE
    }
}