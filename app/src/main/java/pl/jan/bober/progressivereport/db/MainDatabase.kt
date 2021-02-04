package pl.jan.bober.progressivereport.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.jan.bober.progressivereport.data.Report
import pl.jan.bober.progressivereport.data.ReportDao

@Database(entities = [Report::class],version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun reportDao(): ReportDao
}