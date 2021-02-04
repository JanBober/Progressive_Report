package pl.jan.bober.progressivereport

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.jan.bober.progressivereport.di.DaggerAppComponent

class ReportApp : DaggerApplication() {

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}