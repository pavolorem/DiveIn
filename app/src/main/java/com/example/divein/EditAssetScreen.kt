package com.example.divein

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.divein.databinding.EditAssetItemBinding

class EditAssetScreen : AppCompatActivity() {

    private lateinit var binding: EditAssetItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditAssetItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbarEditAssetItemScreen.setNavigationOnClickListener {
            val intent = Intent(this, AssetScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val ws = Intent(applicationContext, AssetScreen::class.java)
        startActivity(ws)
        super.onBackPressed()
        finish()
    }
}