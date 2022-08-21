package com.rnd.qrscanner.ui.splash

interface SplashView {
    fun onAutoLoginLoading()
    fun onAutoLoginSuccess()
    fun onAutoLoginFailure(code: Int, message: String)
}