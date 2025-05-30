package com.mustly.biketours

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mustly.biketours.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(this.root)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}