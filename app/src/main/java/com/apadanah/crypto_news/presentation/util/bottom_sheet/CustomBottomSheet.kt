package com.apadanah.crypto_news.presentation.util.bottom_sheet

import android.annotation.SuppressLint
import android.content.res.Resources
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


fun Fragment.showBottomSheetDialog(
    binding: ViewBinding,
    fullScreen: Boolean = true,
    expand: Boolean = true
): BottomSheetDialog {


    val dialog = BottomSheetDialog(requireContext())
    dialog.setOnShowListener {
        val bottomSheet: FrameLayout =
            dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                ?: return@setOnShowListener
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        if (fullScreen && bottomSheet.layoutParams != null) {
            showFullScreenBottomSheet(bottomSheet)
        }

        if (!expand) return@setOnShowListener

        //bottomSheet.setBackgroundResource(android.R.color.transparent)
        expandBottomSheet(bottomSheetBehavior)
    }

    dialog.setContentView(binding.root)

    return dialog
}

private fun showFullScreenBottomSheet(bottomSheet: FrameLayout) {
    val layoutParams = bottomSheet.layoutParams
    layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
    bottomSheet.layoutParams = layoutParams
}

private fun expandBottomSheet(bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) {
    bottomSheetBehavior.skipCollapsed = true
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
}