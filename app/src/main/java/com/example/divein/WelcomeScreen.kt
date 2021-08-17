package com.example.divein

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.divein.databinding.WelcomeScreenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class WelcomeScreen : AppCompatActivity() {

    private lateinit var binding: WelcomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.assetInfoLogo.setOnClickListener {
            val ast = Intent(this@WelcomeScreen, AssetScreen::class.java)
            startActivity(ast)
        }

        binding.importDataLogo.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this@WelcomeScreen, R.style.AlertDialogDark)
            builder.setMessage("Are you sure, that you want to import data?")
            builder.setPositiveButton(
                "Yes"
            ) { _: DialogInterface?, _: Int ->
                ImportDialog().show(supportFragmentManager, "Importing data frame")
                val exp = Intent(this@WelcomeScreen, InventoryScreen::class.java)
                startActivity(exp)
                super.onBackPressed()
                finish()
            }
            builder.setNegativeButton(
                "No"
            ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        binding.exportLogo.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this@WelcomeScreen, R.style.AlertDialogDark)
            builder.setMessage("Are you sure, that you want to export data?")
            builder.setPositiveButton(
                "Yes"
            ) { _: DialogInterface?, _: Int ->
                ExportDialog().show(supportFragmentManager, "Exporting data frame")
                val exp = Intent(this@WelcomeScreen, WelcomeScreen::class.java)
                startActivity(exp)
                super.onBackPressed()
                finish()
            }
            builder.setNegativeButton(
                "No"
            ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        binding.logoutLogo.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this@WelcomeScreen, R.style.AlertDialogDark)
            builder.setMessage("Are you sure, that you want to logout?")
            builder.setPositiveButton(
                "Yes"
            ) { _: DialogInterface?, _: Int ->
                val i = Intent(
                    applicationContext,
                    MainActivity::class.java
                )
                startActivity(i)
                super.onBackPressed()
                finish()
            }
            builder.setNegativeButton(
                "No"
            ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        binding.inventoryLogo.setOnClickListener {
            val ins = Intent(this@WelcomeScreen, InventoryScreen::class.java)
            startActivity(ins)
        }
    }

    override fun onBackPressed() {
        val builder = MaterialAlertDialogBuilder(this@WelcomeScreen, R.style.AlertDialogDark)
        builder.setMessage("Are you sure, that you want to logout?")
        builder.setPositiveButton(
            "Yes"
        ) { _: DialogInterface?, _: Int ->
            val i = Intent(
                applicationContext,
                MainActivity::class.java
            )
            startActivity(i)
            super.onBackPressed()
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
}
