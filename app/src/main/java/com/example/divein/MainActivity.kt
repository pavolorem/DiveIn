package com.example.divein

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.divein.databinding.LoginScreenBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {

    private var passwordNotVisible = 1
    private lateinit var binding: LoginScreenBinding


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppCenter.start(
            application, "41889810-950c-4296-b936-d44254a86f95",
            Analytics::class.java, Crashes::class.java
        )
        val intent = intent
        val str = intent.getStringExtra("Barcode")
        binding.inputUsername.setText(str)
        binding.inputPassword.setText(str)

        binding.passwordLogoLoginScreen.setOnClickListener {
            if (passwordNotVisible == 1) {
                binding.inputPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordNotVisible = 0
                binding.passwordLogoLoginScreen.setImageResource(R.drawable.password_logo_unlock)
            } else {
                binding.inputPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordLogoLoginScreen.setImageResource(R.drawable.password_logo_lock)
                passwordNotVisible = 1
            }
            binding.inputPassword.setSelection(binding.inputPassword.length())
        }
        binding.loginLogo.setOnClickListener {
            if (binding.inputUsername.text.toString() == "admin" && binding.inputPassword.text.toString() == "admin") {
                val i = Intent(
                    this@MainActivity,
                    WelcomeScreen::class.java
                )
                startActivity(i)
                finish()
            } else {
                Toast.makeText(
                    this@MainActivity, "Wrong username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.inputPassword.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (binding.inputUsername.text.toString() == "admin" && binding.inputPassword.text.toString() == "admin") {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val i = Intent(
                        this@MainActivity,
                        WelcomeScreen::class.java
                    )
                    startActivity(i)
                    finish()
                    return@setOnEditorActionListener true
                }
            } else {
                Toast.makeText(
                    this@MainActivity, "Wrong username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            false
        }

        binding.barcodeLoginLogo.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.CAMERA
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val scan = Intent(this@MainActivity, LiveBarcodeScanningActivity::class.java)
                startActivity(scan)
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(Manifest.permission.CAMERA),
                    permissionCamera
                )
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this@MainActivity, LiveBarcodeScanningActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.change_language, menu)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    companion object {
        private const val permissionCamera = 0
    }
}
