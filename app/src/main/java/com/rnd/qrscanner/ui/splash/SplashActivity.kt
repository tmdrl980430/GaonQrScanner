package com.rnd.qrscanner.ui.splash

import android.os.Handler
import android.os.Looper

import com.rnd.qrscanner.data.remote.auth.AuthService
import com.rnd.qrscanner.databinding.ActivitySplashBinding
import com.rnd.qrscanner.ui.BaseActivity
import com.rnd.qrscanner.ui.main.MainActivity


class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({
            autoLogin()
        }, 2000)
    }

    private fun autoLogin() {
        AuthService.autoLogin(this)
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        startActivityWithClear(MainActivity::class.java)
    }
}