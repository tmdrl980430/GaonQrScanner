package com.rnd.qrscanner.data.remote.userTicket

import com.google.gson.annotations.SerializedName


data class UserTicketResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)