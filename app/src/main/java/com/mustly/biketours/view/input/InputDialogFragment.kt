package com.mustly.biketours.view.input

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mustly.biketours.databinding.InputDialogBinding
import com.mustly.biketours.util.setNoDoubleClickListener

/**
 * 输入框 Dialog
 * */
class InputDialogFragment : DialogFragment() {

    var autoDismiss = true

    var onCancel: (() -> Unit)? = null

    var onConfirm: ((Editable?) -> Unit)? = null

    private var binding: InputDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 干掉标题
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val aty = activity

        if (aty == null) {
            Log.e(TAG, "onCreateDialog, context is null")
            return super.onCreateDialog(savedInstanceState)
        }

        return AlertDialog.Builder(aty)
            .setView(createView(aty))
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            // 页面展示时显示键盘
            binding?.editText?.requestFocus();
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun createView(aty: Activity): View {
        return InputDialogBinding.inflate(LayoutInflater.from(aty), null, false).apply {
            binding = this
            tvCancel.setNoDoubleClickListener {
                onCancel?.invoke()
                if (autoDismiss) {
                    dismiss()
                }
            }
            tvOk.setNoDoubleClickListener {
                onConfirm?.invoke(editText.text)
                if (autoDismiss) {
                    dismiss()
                }
            }
        }.root
    }

    companion object {
        const val TAG = "InputDialogFragment"
    }
}