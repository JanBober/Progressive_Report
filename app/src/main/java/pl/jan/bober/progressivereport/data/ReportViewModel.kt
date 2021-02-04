package pl.jan.bober.progressivereport.data

import android.os.AsyncTask
import pl.jan.bober.progressivereport.base.util.BaseViewModel
import pl.jan.bober.progressivereport.db.MainDatabase
import javax.inject.Inject

class ReportViewModel @Inject constructor(
    private val mainDatabase: MainDatabase
) : BaseViewModel() {

    val allReports = mainDatabase.reportDao().allReport

    fun insert(report: Report) {
        InsertReportAsyncTask(mainDatabase.reportDao()).execute(report)
    }

    fun update(report: Report) {
        UpdateReportAsyncTask(mainDatabase.reportDao()).execute(report)
    }

    fun delete(report: Report) {
        DeleteReportAsyncTask(mainDatabase.reportDao()).execute(report)
    }

    fun deleteAllReports() {
        DeleteAllReportsAsyncTask(mainDatabase.reportDao()).execute()
    }

    private class InsertReportAsyncTask(private val reportDao: ReportDao) :
        AsyncTask<Report, Void, Void>() {
        override fun doInBackground(vararg reports: Report): Void? {
            reportDao.insert(reports[0])
            return null
        }
    }

    private class UpdateReportAsyncTask(private val reportDao: ReportDao) :
        AsyncTask<Report, Void, Void>() {
        override fun doInBackground(vararg reports: Report): Void? {
            reportDao.update(reports[0])
            return null
        }
    }

    private class DeleteReportAsyncTask(private val reportDao: ReportDao) :
        AsyncTask<Report, Void, Void>() {
        override fun doInBackground(vararg reports: Report): Void? {
            reportDao.delete(reports[0])
            return null
        }
    }

    private class DeleteAllReportsAsyncTask(private val reportDao: ReportDao) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg voids: Void): Void? {
            reportDao.deleteAllReport()
            return null
        }
    }
}