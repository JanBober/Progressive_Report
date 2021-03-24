package pl.jan.bober.progressivereport.base.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["text"],
        requireAll = true
    )
    fun onDefaultTextView(textView: TextView, text: String) {
        textView.text = text
    }
}