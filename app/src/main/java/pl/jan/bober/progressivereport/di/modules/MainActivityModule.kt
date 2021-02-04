package pl.jan.bober.progressivereport.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.jan.bober.progressivereport.MainActivity
import pl.jan.bober.progressivereport.data.ReportViewModel
import pl.jan.bober.progressivereport.utils.annotations.ViewModelKey

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(ReportViewModel::class)
    abstract fun bindReportViewModel(viewModel: ReportViewModel): ViewModel
}