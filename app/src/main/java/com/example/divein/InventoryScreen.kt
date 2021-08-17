package com.example.divein

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.divein.databinding.InventoryScreenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InventoryScreen : AppCompatActivity() {

    private lateinit var binding: InventoryScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InventoryScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbarInventoryScreen.setNavigationOnClickListener {
            val intent = Intent(this, WelcomeScreen::class.java)
            startActivity(intent)
            finish()
        }

        binding.physicalClassificationButton.setOnClickListener {
            PhysicalClassificationFragment().show(supportFragmentManager, "Select physical level")
        }

        binding.logicalClassificationButton.setOnClickListener {
            LogicalClassificationFragment().show(supportFragmentManager, "Select logical level")
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_button -> {
                    val intent = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.export_button -> {
                    val builder =
                        MaterialAlertDialogBuilder(this@InventoryScreen, R.style.AlertDialogDark)
                    builder.setMessage("Are you sure, that you want to export data?")
                    builder.setPositiveButton(
                        "Yes"
                    ) { _: DialogInterface?, _: Int ->
                        ExportDialog().show(supportFragmentManager, "Exporting data frame")
                        val exp = Intent(this@InventoryScreen, WelcomeScreen::class.java)
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
                R.id.barcode_scan -> {
                    if (ContextCompat.checkSelfPermission(
                            this@InventoryScreen,
                            Manifest.permission.CAMERA
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        val intent = Intent(
                            this@InventoryScreen,
                            LiveBarcodeScanningActivityInventory::class.java
                        )
                        startActivity(intent)
                        finish()
                    } else {
                        ActivityCompat.requestPermissions(
                            this@InventoryScreen, arrayOf(Manifest.permission.CAMERA),
                            permissionCamera
                        )
                    }
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        val ws = Intent(applicationContext, WelcomeScreen::class.java)
        startActivity(ws)
        super.onBackPressed()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent =
                Intent(this@InventoryScreen, LiveBarcodeScanningActivityInventory::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val permissionCamera = 0
    }
}