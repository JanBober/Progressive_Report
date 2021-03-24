package pl.jan.bober.progressivereport.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.jan.bober.progressivereport.screens.fragment.TimePickerFragment

@Module
abstract class TimePickerFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeTimePickerFragment(): TimePickerFragment
}