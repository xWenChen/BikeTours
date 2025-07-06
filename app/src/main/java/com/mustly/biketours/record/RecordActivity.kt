package com.mustly.biketours.record

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustly.biketours.databinding.ActivityRecordBinding
import com.mustly.biketours.record.ui.RecordAdapter
import com.mustly.biketours.ui.RecordItemDecoration

class RecordActivity : AppCompatActivity() {
    private var binding: ActivityRecordBinding? = null
    private val viewModel: RecordViewModel by viewModels()
    private var recordAdapter: RecordAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityRecordBinding.inflate(this.layoutInflater).apply {
            binding = this
            setContentView(this.root)
            initView(this)
        }
        viewModel.init()
        viewModel.recordList.observe(this) {
            it ?: return@observe
            val list = it.dataList ?: return@observe
            recordAdapter?.apply {
                submit(list)
                if (it.needDiff) {
                    it.diffResult?.dispatchUpdatesTo(this)
                } else {
                    notifyDataSetChanged()
                }
            }
            it.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        recordAdapter = null
    }

    private fun initView(mBinding: ActivityRecordBinding) {
        mBinding.rv.apply {
            layoutManager = LinearLayoutManager(this@RecordActivity)
            adapter = RecordAdapter().apply {
                recordAdapter = this
            }
            addItemDecoration(RecordItemDecoration())
        }
    }
}