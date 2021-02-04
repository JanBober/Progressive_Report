//package pl.jan.bober.progressivereport.data
//
//import android.content.Context
//import android.os.AsyncTask
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.sqlite.db.SupportSQLiteDatabase
//
//@Database(entities = [Report::class],version = 1)
//abstract class ReportDatabase : RoomDatabase() {
//    abstract fun reportDao(): ReportDao
//    private class PopulateDbAsyncTask internal constructor(db: ReportDatabase) :
//            AsyncTask<Void, Void, Void>() {
//
//        private val reportDao: ReportDao = db.reportDao()
//
//        override fun doInBackground(vararg voids: Void): Void? {
//            reportDao.insert(
//                Report("Pomidory","poniedziałek, 12 sierpnia 2019","7:20","User1","Lokal1"))
//            return null
//        }
//    }
//
//    companion object {
//        private var instance: ReportDatabase? = null
//
//        @Synchronized
//        fun getInstance(context: Context): ReportDatabase {
//            if (instance == null) {
//                instance = Room.databaseBuilder(context.applicationContext,
//                    ReportDatabase::class.java,"export_report_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
//                    .build()
//            }
//            return instance as ReportDatabase
//        }
//
//        private val roomCallback = object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                PopulateDbAsyncTask(instance!!).execute()
//            }
//        }
//    }
//}