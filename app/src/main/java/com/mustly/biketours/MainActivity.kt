package com.mustly.biketours

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.mustly.biketours.databinding.ActivityMainBinding
import com.mustly.biketours.ui.DistanceAdapter
import com.mustly.biketours.ui.DistanceItemDecoration

/**
 * 界面设计参考：https://developer.android.com/design/ui/mobile/guides/styles/color?hl=zh-cn
 *  https://materialui.co/colors
 *  https://coocolors.com/
 * */
class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    private val viewModel: MainViewModel by viewModels()

    var adapter: DistanceAdapter? = null

    val spanCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(this.root)
            initView(this)
        }
        viewModel.init()
        viewModel.distanceList.observe(this) {
            adapter?.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        adapter = null
    }

    private fun initView(mBinding: ActivityMainBinding) {
        val rv = mBinding.recyclerView
        rv.addItemDecoration(DistanceItemDecoration(spanCount))
        rv.layoutManager = GridLayoutManager(this, spanCount)
        DistanceAdapter().apply {
            rv.adapter = this
            adapter = this
        }
    }
}