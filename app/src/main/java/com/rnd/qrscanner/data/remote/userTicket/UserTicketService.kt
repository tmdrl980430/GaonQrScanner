package com.rnd.qrscanner.data.remote.userTicket

import android.util.Log
import com.rnd.qrscanner.ApplicationClass.Companion.TAG
import com.rnd.qrscanner.ApplicationClass.Companion.retrofit
import com.rnd.qrscanner.data.entities.UserTicket
import com.rnd.qrscanner.ui.main.MainView

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserTicketService {

    fun useUserTicket(mainView: MainView , userTicketInfo : UserTicket) {
        val userTicketService = retrofit.create(UserTicketRetrofitInterface::class.java)

        mainView.useUserTicketLoading()

        userTicketService.useUserTicket(userTicketInfo, "use").enqueue(object : Callback<UserTicketResponse> {

            override fun onResponse(call: Call<UserTicketResponse>, response: Response<UserTicketResponse>) {
                Log.d("RESPONSE_BODY", response.body().toString())
                val resp = response.body()!!

                when(resp.code){
                    1000 -> mainView.useUserTicketSuccess()
                    else -> mainView.useUserTicketFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<UserTicketResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                mainView.useUserTicketFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })

    }

    fun useUserPoint(mainView: MainView , userTicketInfo : UserTicket) {
        val userTicketService = retrofit.create(UserTicketRetrofitInterface::class.java)

        mainView.useUserTicketLoading()

        userTicketService.useUserTicket(userTicketInfo, "point").enqueue(object : Callback<UserTicketResponse> {

            override fun onResponse(call: Call<UserTicketResponse>, response: Response<UserTicketResponse>) {
                Log.d("RESPONSE_BODY", response.body().toString())
                val resp = response.body()!!

                when(resp.code){
                    1000 -> mainView.useUserTicketSuccess()
                    else -> mainView.useUserTicketFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<UserTicketResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                mainView.useUserTicketFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })





    }
}