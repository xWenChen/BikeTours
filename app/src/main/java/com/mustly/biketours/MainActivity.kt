package com.mustly.biketours

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mustly.biketours.databinding.ActivityMainBinding

/**
 * 界面设计参考：https://developer.android.com/design/ui/mobile/guides/styles/color?hl=zh-cn
 *  https://materialui.co/colors
 *  https://coocolors.com/
 * */
class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(this.root)
        }
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun initView() {
        val mBinding = binding ?: return

    }
}