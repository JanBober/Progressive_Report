package pl.jan.bober.progressivereport.base.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import pl.jan.bober.progressivereport.di.factory.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    fun <T : ViewModel> viewModelOf(viewModelClass: KClass<T>) =
        ViewModelProvider(this, factory).get(viewModelClass.java)

}