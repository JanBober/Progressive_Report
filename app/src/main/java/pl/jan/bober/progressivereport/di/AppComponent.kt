package pl.jan.bober.progressivereport.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.jan.bober.progressivereport.ReportApp
import pl.jan.bober.progressivereport.di.modules.ViewModelModule
import pl.jan.bober.progressivereport.di.modules.dataModule.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        DatabaseModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent : AndroidInjector<ReportApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Factory<ReportApp> {

        override fun create(application: ReportApp): AppComponent {
            seedApplication(application)
            return build()
        }

        @BindsInstance
        internal abstract fun seedApplication(application: ReportApp)

        internal abstract fun build(): AppComponent
    }
}