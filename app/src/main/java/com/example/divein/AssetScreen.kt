package com.example.divein

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.divein.databinding.AssetInfoBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AssetScreen : AppCompatActivity() {

    private lateinit var binding: AssetInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AssetInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbarAssetInfoScreen.setNavigationOnClickListener {
            val intent = Intent(this, WelcomeScreen::class.java)
            startActivity(intent)
            finish()
        }

        binding.addItemFloatingButtom.setOnClickListener {
            val ast = Intent(this, EditAssetScreen::class.java)
            startActivity(ast)
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_button -> {
                    val intent = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.inventory_button -> {
                    val intent = Intent(this, InventoryScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.import_button -> {
                    val builder =
                        MaterialAlertDialogBuilder(this@AssetScreen, R.style.AlertDialogDark)
                    builder.setMessage("Are you sure, that you want to import data?")
                    builder.setPositiveButton(
                        "Yes"
                    ) { _: DialogInterface?, _: Int ->
                        ImportDialog().show(supportFragmentManager, "Importing data frame")
                        val exp = Intent(this@AssetScreen, AssetScreen::class.java)
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
                            this@AssetScreen,
                            Manifest.permission.CAMERA
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        val intent =
                            Intent(this@AssetScreen, LiveBarcodeScanningActivityAsset::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        ActivityCompat.requestPermissions(
                            this@AssetScreen, arrayOf(Manifest.permission.CAMERA),
                            permissionCamera
                        )
                    }
                }
            }
            true
        }

        binding.nestedScrollViewAssetInfoScreen.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY + 20 && binding.addItemFloatingButtom.isExtended) {     // Scroll Down
                binding.addItemFloatingButtom.shrink()
            }

            if (scrollY < oldScrollY - 20 && !binding.addItemFloatingButtom.isExtended) {      // Scroll Up
                binding.addItemFloatingButtom.extend()
            }

            if (scrollY == 0) {     // At the top
                binding.addItemFloatingButtom.extend()
            }
        })

        binding.recyclerViewAssetInfoScreen.adapter

        binding.recyclerViewAssetInfoScreen.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 20 && binding.addItemFloatingButtom.isExtended) {
                    binding.addItemFloatingButtom.shrink()
                }

                if (dy < -20 && binding.addItemFloatingButtom.isExtended) {
                    binding.addItemFloatingButtom.extend()
                }

                if (!recyclerView.canScrollVertically(-1)) {
                    binding.addItemFloatingButtom.extend()
                }
            }
        })
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
            val intent = Intent(this@AssetScreen, LiveBarcodeScanningActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_asset_screen, menu)
        return true
    }

    companion object {
        const val permissionCamera = 0
    }
}
