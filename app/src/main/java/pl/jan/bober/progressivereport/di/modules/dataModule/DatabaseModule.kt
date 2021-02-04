package pl.jan.bober.progressivereport.di.modules.dataModule

import androidx.room.Room
import dagger.Module
import dagger.Provides
import pl.jan.bober.progressivereport.ReportApp
import pl.jan.bober.progressivereport.db.MainDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun getDataBase(app: ReportApp): MainDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            MainDatabase::class.java,
            "export_report_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}