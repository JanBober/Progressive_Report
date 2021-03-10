package pl.jan.bober.progressivereport.screens.addreport

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.jan.bober.entities.model.Report
import pl.jan.bober.progressivereport.R
import pl.jan.bober.progressivereport.base.BaseViewModel
import pl.jan.bober.progressivereport.base.SingleLiveEvent
import pl.jan.bober.progressivereport.base.ktx.mutableLiveData
import pl.jan.bober.progressivereport.manager.ResourcesProvider
import pl.jan.bober.usecases.PostReportUseCase
import javax.inject.Inject

class AddReportViewModel @Inject constructor(
    private val postReportUseCase: PostReportUseCase,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {
    val name = mutableLiveData("")
    val date = mutableLiveData(resourcesProvider.getString(R.string.select_a_date))
    val time = mutableLiveData(resourcesProvider.getString(R.string.select_a_time))

    val actionReport = SingleLiveEvent<Action>()
    val errorReport = SingleLiveEvent<Errors>()

    fun insertReport() {
        val report = Report().apply {
            name = this@AddReportViewModel.name.value
            date = this@AddReportViewModel.date.value
            time = this@AddReportViewModel.time.value
        }
        if (name.value!!.isEmpty()) {
            errorReport.value = Errors.NameIsEmpty
            return
        }
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

    fun doNotSave() {
        actionReport.value = Action.DoNotSaveReport
    }

    sealed class Action {
        object AddReport : Action()
        object DoNotSaveReport : Action()
    }

    sealed class Errors {
        data class AddReportException(val exception: Throwable) : Errors()
        object NameIsEmpty : Errors()
    }
}