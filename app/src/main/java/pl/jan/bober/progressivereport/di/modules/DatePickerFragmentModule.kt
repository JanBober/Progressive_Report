package pl.jan.bober.progressivereport.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.jan.bober.progressivereport.screens.fragment.DatePickerFragment

@Module
abstract class DatePickerFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeDatePickerFragment(): DatePickerFragment
}