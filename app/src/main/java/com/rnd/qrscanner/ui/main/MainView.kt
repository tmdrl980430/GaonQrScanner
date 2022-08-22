package com.rnd.qrscanner.ui.main

interface MainView {
    fun useUserTicketLoading()
    fun useUserTicketSuccess()
    fun useUserTicketFailure(code: Int, message: String)
}