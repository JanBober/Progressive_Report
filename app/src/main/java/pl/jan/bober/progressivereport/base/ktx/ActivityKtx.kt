package pl.jan.bober.progressivereport.base.ktx

import android.app.Activity
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import pl.jan.bober.progressivereport.R

fun Activity.showToast(messenge: String, isLong: Boolean = true) =
    Toast.makeText(
        this,
        messenge,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    )
        .show()

fun Activity.showSnackBar(
    messenge: String,
    isLong: Boolean = true,
    @ColorRes colorRes: Int = R.color.colorAccent
) =
    Snackbar.make(
        findViewById<ViewGroup>(android.R.id.content).getChildAt(0),
        messenge,
        if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
    ).also {
        (it.view as? Snackbar.SnackbarLayout)
            ?.rootView
            ?.setBackgroundColor(ContextCompat.getColor(it.context, colorRes))
    }
        .show()

fun Activity.showSnackBar(messenge: String, isLong: Boolean = true, afterTimeOut: () -> Unit) =
    Snackbar.make(
        findViewById<ViewGroup>(android.R.id.content).getChildAt(0),
        messenge,
        if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
    ).addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            afterTimeOut()
        }
    })
        .show()