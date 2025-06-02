package com.mustly.biketours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mustly.biketours.databinding.ActivityMainV2Binding
import com.mustly.biketours.ui.DistanceAdapter
import com.mustly.biketours.ui.DistanceItemDecoration
import com.mustly.biketours.util.formatString
import com.mustly.biketours.util.setNoDoubleClickListener

/**
 * 界面设计参考：https://developer.android.com/design/ui/mobile/guides/styles/color?hl=zh-cn
 *  https://materialui.co/colors
 *  https://coocolors.com/
 * */
class MainV2Activity : AppCompatActivity() {
    var binding: ActivityMainV2Binding? = null

    private val viewModel: MainV2ViewModel by viewModels()

    var adapter: DistanceAdapter? = null

    val spanCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainV2Binding.inflate(layoutInflater).apply {
            binding = this
            setContentView(this.root)
            initView(this)
        }
        viewModel.init()
        viewModel.distanceList.observe(this) {
            adapter?.submitList(it)
        }
        viewModel.totalDistance.observe(this) {
            it?.let {
                binding?.tvTotalDistance?.text = (it.toFloat() / 1000L).formatString(1)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        adapter = null
    }

    private fun initView(mBinding: ActivityMainV2Binding) {
        initRV(mBinding)
        mBinding.tvSave.setNoDoubleClickListener {
            viewModel.addBikeRecord()
        }
    }

    private fun initRV(mBinding: ActivityMainV2Binding) {
        val rv = mBinding.recyclerView
        rv.addItemDecoration(DistanceItemDecoration(spanCount))
        rv.layoutManager = GridLayoutManager(this, spanCount)
        DistanceAdapter().apply {
            onItemChecked = { position, data ->
                viewModel.changeCheckedItem(position, data)
            }
            rv.adapter = this
            adapter = this
        }
    }
}