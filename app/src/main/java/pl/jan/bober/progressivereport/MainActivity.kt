package pl.jan.bober.progressivereport

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import pl.jan.bober.progressivereport.base.util.BaseActivity
import pl.jan.bober.progressivereport.data.ReportAdapter
import pl.jan.bober.progressivereport.data.ReportViewModel
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
                onClick = {}
            }
        binding.recyclerView.adapter = reportAdapter
//        reportAdapter.setReportList(reportViewModel.allReports.value!!)
    }
}
