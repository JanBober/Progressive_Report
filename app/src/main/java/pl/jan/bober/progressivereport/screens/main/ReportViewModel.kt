package pl.jan.bober.progressivereport.screens.main

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.jan.bober.entities.model.Report
import pl.jan.bober.progressivereport.base.BaseViewModel
import pl.jan.bober.progressivereport.base.SingleLiveEvent
import pl.jan.bober.progressivereport.db.MainDatabase
import pl.jan.bober.usecases.DeleteReportUseCase
import pl.jan.bober.usecases.GetReportUseCase
import java.util.*
import javax.inject.Inject

class ReportViewModel @Inject constructor(
    private val mainDatabase: MainDatabase,
    private val getReportUseCase: GetReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase
) : BaseViewModel() {

    val reports = MutableLiveData<List<Report>>()
    val privateReports = MutableLiveData<List<Report>>()
    val report = MutableLiveData<Report>()

    val actionReport = SingleLiveEvent<Action>()
    val errorReport = SingleLiveEvent<Errors>()

    fun getPrivateReport() {
        mainDatabase.reportDao().getReports()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    privateReports.value = it
                    actionReport.value = Action.ShowPrivateReports(privateReports.value!!)
                },
                onError = {
                    errorReport.value = Errors.PrivateReportException(it)
                }
            ).addTo(disposables)
    }

    fun insertPrivateReport(report: Report) {
        mainDatabase.reportDao().insert(report)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    actionReport.value = Action.AddPrivateReport
                },
                onError = {
                    errorReport.value = Errors.PrivateReportException(it)
                }
            ).addTo(disposables)
    }

    fun updatePrivateReport(report: Report) {
        mainDatabase.reportDao().insert(report)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    actionReport.value = Action.UpdatePrivateReport
                },
                onError = {
                    errorReport.value = Errors.PrivateReportException(it)
                }
            ).addTo(disposables)
    }

    fun deletePrivateReport(report: Report) {
        mainDatabase.reportDao().delete(report)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    actionReport.value = Action.DeletePrivateReport
                },
                onError = {
                    errorReport.value = Errors.PrivateReportException(it)
                }
            ).addTo(disposables)
    }

    fun deletePrivateAllReports() {
        mainDatabase.reportDao().deleteAllReport()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    actionReport.value = Action.DeletePrivateReports
                },
                onError = {
                    errorReport.value = Errors.PrivateReportException(it)
                }
            ).addTo(disposables)
    }

    fun fetchReport() {
        getReportUseCase
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    refreshReports(it)
                },
                onError = {
                    errorReport.value = Errors.ReportsDownloadException(it)
                }
            ).addTo(disposables)
    }

    private fun refreshReports(reports: List<Report>) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (this.reports.value == null) {
                this.reports.value = reports
                actionReport.value = Action.ShowReports(reports)
                return@postDelayed
            }
            else if (reports.size != this.reports.value!!.size) {
                this.reports.value = reports
                actionReport.value = Action.RefreshReports(reports)
                return@postDelayed
            }
            else {
                reports.forEach { report ->
                    if (!this.reports.value!!.contains(report)) {
                        this.reports.value = reports
                        actionReport.value = Action.RefreshReports(reports)
                        return@postDelayed
                    }
                }
                actionReport.value = Action.Run
            }
        }, 5000)
    }

    fun deleteReport(reportId: Long) {
        deleteReportUseCase
            .execute(reportId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    actionReport.value = Action.DeleteReport
                },
                onError = {
                    errorReport.value = Errors.ReportsDownloadException(it)
                }
            ).addTo(disposables)
    }

    fun reportClicked(report: Report) {
        this.report.value = report
    }

    fun addReport() {
        actionReport.value = Action.GoToAddReportActivity
    }

    sealed class Action {
        data class ShowReports(val listOfReports: List<Report>) : Action()
        data class RefreshReports(val listOfReports: List<Report>) : Action()
        object Run : Action()
        object GoToAddReportActivity : Action()
        object DeleteReport : Action()
        data class ShowPrivateReports(val listOfPrivateReports: List<Report>) : Action()
        object AddPrivateReport : Action()
        object UpdatePrivateReport : Action()
        object DeletePrivateReport : Action()
        object DeletePrivateReports : Action()
    }

    sealed class Errors {
        data class ReportsDownloadException(val exception: Throwable) : Errors()
        data class PrivateReportException(val exception: Throwable) : Errors()
    }
}