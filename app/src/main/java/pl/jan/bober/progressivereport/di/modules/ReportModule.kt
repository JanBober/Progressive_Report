package pl.jan.bober.progressivereport.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.jan.bober.progressivereport.screens.main.MainActivity
import pl.jan.bober.progressivereport.screens.main.ReportViewModel
import pl.jan.bober.progressivereport.utils.annotations.ViewModelKey
import pl.jan.bober.repositories.ReportRepository
import pl.jan.bober.usecases.DeleteReportUseCase
import pl.jan.bober.usecases.GetReportUseCase
import javax.inject.Singleton

@Module(includes = [ReportUseCases::class])
abstract class ReportModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(ReportViewModel::class)
    abstract fun bindReportViewModel(viewModel: ReportViewModel): ViewModel
}

@Module
class ReportUseCases {
    @Provides
    @Singleton
    fun provideGetReportUseCase(reportRepository: ReportRepository): GetReportUseCase {
        return GetReportUseCase(reportRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteReportUseCase(reportRepository: ReportRepository): DeleteReportUseCase {
        return DeleteReportUseCase(reportRepository)
    }
}