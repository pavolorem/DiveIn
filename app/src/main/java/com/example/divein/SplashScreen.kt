@file:Suppress("DEPRECATION")

package com.example.divein

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler

class SplashScreen : Activity() {
    /** Duration of wait  */
    private val splash = 1000
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        Handler().postDelayed({
            val mainIntent = Intent(this@SplashScreen, MainActivity::class.java)
            this@SplashScreen.startActivity(mainIntent)
            finish()
        }, splash.toLong())
    }
}