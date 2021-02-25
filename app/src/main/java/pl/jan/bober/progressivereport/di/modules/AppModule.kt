package pl.jan.bober.progressivereport.di.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import pl.jan.bober.progressivereport.ReportApp

@Module
internal abstract class AppModule {

    @Binds
    abstract fun bindApplication(application: ReportApp): Application
}