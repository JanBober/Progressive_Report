package pl.jan.bober.progressivereport.screens.main

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.jan.bober.entities.model.Report
import pl.jan.bober.progressivereport.R
import pl.jan.bober.progressivereport.base.BaseViewModel
import pl.jan.bober.progressivereport.base.SingleLiveEvent
import pl.jan.bober.progressivereport.base.ktx.mutableLiveData
import pl.jan.bober.progressivereport.db.MainDatabase
import pl.jan.bober.progressivereport.manager.ResourcesProvider
import pl.jan.bober.usecases.DeleteReportUseCase
import pl.jan.bober.usecases.GetReportUseCase
import pl.jan.bober.usecases.PostReportUseCase
import pl.jan.bober.usecases.PutReportUseCase
import java.util.*
import javax.inject.Inject

class ReportViewModel @Inject constructor(
    private val mainDatabase: MainDatabase,
    private val getReportUseCase: GetReportUseCase,
    private val postReportUseCase: PostReportUseCase,
    private val putReportUseCase: PutReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {

    val name = mutableLiveData("")
    val date = mutableLiveData(resourcesProvider.getString(R.string.select_a_date))
    val time = mutableLiveData(resourcesProvider.getString(R.string.select_a_time))

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
        when {
            this.reports.value == null -> {
                this.reports.value = reports
                actionReport.value = Action.ShowReports(reports)
            }
            reports.size != this.reports.value!!.size -> {
                this.reports.value = reports
                actionReport.value = Action.RefreshReports(reports)
            }
            else -> {
                reports.forEach { report ->
                    if (!this.reports.value!!.contains(report)) {
                        this.reports.value = reports
                        actionReport.value = Action.RefreshReports(reports)
                    }
                }
            }
        }
    }

    fun saveReport() {
        if (name.value!!.isEmpty()) {
            errorReport.value = Errors.NameIsEmpty
            return
        }
        val report = Report().apply {
            name = this@ReportViewModel.name.value
            date = this@ReportViewModel.date.value
            time = this@ReportViewModel.time.value
            if (this@ReportViewModel.report.value != null) {
                id = this@ReportViewModel.report.value!!.id
            }
        }
        if (this.report.value==null) {
            insertReport(report)
        }
        else {
            updateReport(report)
        }
    }

    private fun insertReport(report: Report) {
        postReportUseCase
            .execute(report)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    actionReport.value = Action.AddReport
                },
                onError = {
                    errorReport.value = Errors.AddReportException(it)
                }
            ).addTo(disposables)
    }

    private fun updateReport(report: Report) {
        putReportUseCase
            .execute(report)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    actionReport.value = Action.UpDateReport
                },
                onError = {
                    errorReport.value = Errors.UpDateReportException(it)
                }
            ).addTo(disposables)
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

    fun doNotSave() {
        actionReport.value = Action.DoNotSaveReport
    }

    fun reportClicked(report: Report) {
        this.report.value = report
        name.value = report.name
        date.value = report.date
        time.value = report.time
        formReport()
    }

    fun addReport() {
        name.value = ""
        date.value = resourcesProvider.getString(R.string.select_a_date)
        time.value = resourcesProvider.getString(R.string.select_a_time)
        report.value = null
        formReport()
    }

    private fun formReport() {
        actionReport.value = Action.GoToFormReportFlipper
    }

    sealed class Action {
        data class ShowReports(val listOfReports: List<Report>) : Action()
        data class RefreshReports(val listOfReports: List<Report>) : Action()
        object AddReport : Action()
        object UpDateReport : Action()
        object DoNotSaveReport : Action()
        object GoToFormReportFlipper : Action()
        object DeleteReport : Action()
        data class ShowPrivateReports(val listOfPrivateReports: List<Report>) : Action()
        object AddPrivateReport : Action()
        object UpdatePrivateReport : Action()
        object DeletePrivateReport : Action()
        object DeletePrivateReports : Action()
    }

    sealed class Errors {
        data class ReportsDownloadException(val exception: Throwable) : Errors()
        data class AddReportException(val exception: Throwable) : Errors()
        data class UpDateReportException(val exception: Throwable) : Errors()
        object NameIsEmpty : Errors()
        data class PrivateReportException(val exception: Throwable) : Errors()
    }
}