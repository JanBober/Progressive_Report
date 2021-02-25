package pl.jan.bober.progressivereport.di.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import pl.jan.bober.progressivereport.ReportApp

@Module(
    includes = [
        ReportModule::class
    ]
)
abstract class ViewModelModule {

    @Binds
    abstract fun bindApplication(application: ReportApp): Application
}