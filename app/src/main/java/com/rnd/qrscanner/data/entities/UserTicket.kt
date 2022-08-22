package com.rnd.qrscanner.data.entities

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class UserTicket(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("date") val date: String,
    @SerializedName("mealTickets") val mealTickets: ArrayList<MealTickets>
)

data class MealTickets(
    @SerializedName("mealTypeIdx") val userIdx: Int,
    @SerializedName("amount") val date: Int
)
