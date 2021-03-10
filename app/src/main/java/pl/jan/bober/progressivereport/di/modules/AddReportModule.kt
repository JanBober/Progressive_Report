package pl.jan.bober.progressivereport.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.jan.bober.progressivereport.screens.addreport.AddReport
import pl.jan.bober.progressivereport.screens.addreport.AddReportViewModel
import pl.jan.bober.progressivereport.utils.annotations.ViewModelKey
import pl.jan.bober.repositories.ReportRepository
import pl.jan.bober.usecases.PostReportUseCase
import javax.inject.Singleton

@Module(includes = [AddReportUseCases::class])
abstract class AddReportModule {

    @ContributesAndroidInjector
    abstract fun contributeAddReport(): AddReport

    @Binds
    @IntoMap
    @ViewModelKey(AddReportViewModel::class)
    abstract fun bindAddReportViewModel(viewModel: AddReportViewModel): ViewModel
}

@Module
class AddReportUseCases {
    @Provides
    @Singleton
    fun providePostReportUseCase(reportRepository: ReportRepository): PostReportUseCase {
        return PostReportUseCase(reportRepository)
    }
}