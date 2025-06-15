package com.mustly.biketours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustly.biketours.databinding.ActivityMainV2Binding
import com.mustly.biketours.ui.*
import com.mustly.biketours.util.TimeUtils
import com.mustly.biketours.util.formatString
import com.mustly.biketours.util.setNoDoubleClickListener
import com.mustly.biketours.util.stringRes

/**
 * 界面设计参考：https://developer.android.com/design/ui/mobile/guides/styles/color?hl=zh-cn
 *  https://materialui.co/colors
 *  https://coocolors.com/
 * */
class MainV2Activity : AppCompatActivity() {
    var binding: ActivityMainV2Binding? = null

    private val viewModel: MainV2ViewModel by viewModels()

    var adapter: DistanceAdapter? = null
    var recordAdapter: BikeRecordAdapter? = null

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
        viewModel.todayRecords.observe(this) { list ->
            recordAdapter?.submitList(list)
        }
        viewModel.totalDistance.observe(this) {
            it?.let {
                binding?.tvTotalDistance?.text = (it.toFloat() / 1000L).formatString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        adapter = null
        recordAdapter = null
    }

    private fun initView(mBinding: ActivityMainV2Binding) {
        initDistanceRV(mBinding)
        initRecordsRV(mBinding)
        mBinding.tvSave.setNoDoubleClickListener {
            viewModel.addBikeRecord(this)
        }
        // 先以end时间作为骑行时间
        initTodayRecordTitle(mBinding)
    }

    private fun initTodayRecordTitle(mBinding: ActivityMainV2Binding) {
        val dayStr = TimeUtils.parseDateText(System.currentTimeMillis())
        mBinding.tvDate.text = "$dayStr ${R.string.today_title.stringRes}"
    }

    private fun initDistanceRV(mBinding: ActivityMainV2Binding) {
        val rv = mBinding.rvDistance
        rv.addItemDecoration(DistanceItemDecoration(spanCount))
        rv.layoutManager = GridLayoutManager(this, spanCount)
        DistanceAdapter().apply {
            onItemChecked = { position, data ->
                if (data.viewTYpe == ViewType.CUSTOM) {
                    viewModel.showInputDialog(this@MainV2Activity)
                } else {
                    viewModel.changeCheckedItem(position, data)
                }
            }
            rv.adapter = this
            adapter = this
        }
    }

    private fun initRecordsRV(mBinding: ActivityMainV2Binding) {
        val rv = mBinding.rvTodayRecords
        rv.addItemDecoration(RecordItemDecoration())
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        BikeRecordAdapter().apply {
            rv.adapter = this
            recordAdapter = this
        }
    }
}