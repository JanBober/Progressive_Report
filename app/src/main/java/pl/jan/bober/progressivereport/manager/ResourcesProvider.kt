package pl.jan.bober.progressivereport.manager

import android.app.Application
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesProvider @Inject constructor(private val application: Application) {

    fun getString(@StringRes resId: Int): String = application.getString(resId)
}