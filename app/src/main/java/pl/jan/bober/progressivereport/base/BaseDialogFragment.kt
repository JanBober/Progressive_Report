package pl.jan.bober.progressivereport.base

import dagger.android.support.DaggerDialogFragment
import pl.jan.bober.progressivereport.di.factory.ViewModelFactory
import javax.inject.Inject

abstract class BaseDialogFragment : DaggerDialogFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
}