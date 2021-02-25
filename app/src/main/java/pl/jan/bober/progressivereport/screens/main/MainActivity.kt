package pl.jan.bober.progressivereport.screens.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import pl.jan.bober.progressivereport.R
import pl.jan.bober.progressivereport.base.ktx.showSnackBar
import pl.jan.bober.progressivereport.base.util.BaseActivity
import pl.jan.bober.progressivereport.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var reportAdapter: ReportAdapter
    private lateinit var reportViewModel: ReportViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportViewModel = viewModelOf(ReportViewModel::class)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
            .also {
                it.lifecycleOwner = this
                it.viewModel = reportViewModel
            }
        reportAdapter = ReportAdapter()
            .apply {
                onClick = { reportViewModel.reportClicked(it) }
            }
        binding.recyclerView.adapter = reportAdapter
        reportViewModel.fetchReport()
        activeOutput()

//        reportAdapter.setReportList(reportViewModel.allReports.value!!)
    }

    private fun activeOutput() {
        reportViewModel.actionReport.observe(this) {
            when (it) {
                is ReportViewModel.Action.ShowReports ->
                    reportAdapter.setReportList(it.listOfReports)
                ReportViewModel.Action.DeleteReport -> {
                    reportViewModel.fetchReport()
//                    reportAdapter.actualizedReportList()
                    showSnackBar("delete Report")
                }
                is ReportViewModel.Action.ShowPrivateReports -> {
                    // TODO: 19.02.2021 insert list listOfReports and listOfPrivateReports
                }
                ReportViewModel.Action.AddPrivateReport -> {
                    showSnackBar("")
                    // TODO: 19.02.2021 add message
                }
                ReportViewModel.Action.UpdatePrivateReport -> {
                    showSnackBar("")
                    // TODO: 19.02.2021 add message
                }
                ReportViewModel.Action.DeletePrivateReport -> {
                    showSnackBar("")
                    // TODO: 19.02.2021 add message
                }
                ReportViewModel.Action.DeletePrivateReports -> {
                    showSnackBar("")
                    // TODO: 19.02.2021 add message
                }
            }
        }
        reportViewModel.errorReport.observe(this) {
            when (it) {
                is ReportViewModel.Errors.ReportsDownloadException ->
                    it.exception.message?.let {
                        message -> showSnackBar(message)
                    }
                is ReportViewModel.Errors.PrivateReportException ->
                    it.exception.message?.let {
                        message -> showSnackBar(message)
                    }
            }
        }
    }
}
