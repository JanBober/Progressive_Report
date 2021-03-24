package pl.jan.bober.progressivereport.di.modules.dataModule

import dagger.Module
import dagger.Provides
import io.swagger.client.api.ReportApi
import pl.jan.bober.repositories.ReportRepository
import pl.jan.bober.repositories.ReportRepositoryImpl
import javax.inject.Singleton

@Module
class ManagerModule {

    @Provides
    @Singleton
    internal fun provideReportRepository(
        reportApi: ReportApi
    ): ReportRepository {
        return ReportRepositoryImpl(reportApi)
    }
}