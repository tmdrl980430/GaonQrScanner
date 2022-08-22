package com.rnd.qrscanner.data.remote.userTicket

import com.rnd.qrscanner.data.entities.UserTicket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Query


interface UserTicketRetrofitInterface {

    @PATCH("mealtickets")
    fun useUserTicket(
        @Body userTicket: UserTicket,
        @Query("type") type : String?
    ): Call<UserTicketResponse>
}