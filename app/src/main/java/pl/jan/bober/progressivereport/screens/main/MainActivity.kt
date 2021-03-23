package pl.jan.bober.progressivereport.screens.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import pl.jan.bober.progressivereport.R
import pl.jan.bober.progressivereport.base.BaseActivity
import pl.jan.bober.progressivereport.base.ktx.showSnackBar
import pl.jan.bober.progressivereport.base.ktx.showToast
import pl.jan.bober.progressivereport.databinding.ActivityMainBinding
import pl.jan.bober.progressivereport.screens.fragment.DatePickerFragment
import pl.jan.bober.progressivereport.screens.fragment.TimePickerFragment
import java.text.DateFormat
import java.util.*

class MainActivity : BaseActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private lateinit var reportAdapter: ReportAdapter
    private lateinit var reportViewModel: ReportViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var pubnub: PubNub

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
        binding.getReports.recyclerView.adapter = reportAdapter
        reportViewModel.fetchReport()
        activeOutput()
        binding()
        initPubNub()

//        reportAdapter.setReportList(reportViewModel.allReports.value!!)
    }
    private fun sendMessage() {
        pubnub.publish()
            .message("notification")
            .channel("global_channel")
            .async(object : PNCallback<PNPublishResult?>() {
                override fun onResponse(result: PNPublishResult?, status: PNStatus) {
                    if (status.isError) {
                        showSnackBar("pub status code: ${status.statusCode}")
                    }
                }
            })
    }

    private fun initPubNub() {
        val pnConfiguration = PNConfiguration()
        pnConfiguration.publishKey = ENTER_YOUR_PUB_KEY
        pnConfiguration.subscribeKey = ENTER_YOUR_SUB_KEY
        pnConfiguration.isSecure = true
        pubnub = PubNub(pnConfiguration)
        pubnub.addListener(object : SubscribeCallback() {
            override fun status(pub: PubNub?, status: PNStatus?) {}
            override fun message(pub: PubNub?, message: PNMessageResult) {
                runOnUiThread {
                    try {
                        reportViewModel.fetchReport()
                    } catch (e: java.lang.Exception) {
                        showSnackBar("$e")
                    }
                }
            }

            override fun presence(pub: PubNub?, presence: PNPresenceEventResult?) {}
        })
        pubnub.subscribe()
            .channels(listOf("global_channel"))
            .execute()
    }

    private fun activeOutput() {
        reportViewModel.actionReport.observe(this) {
            when (it) {
                is ReportViewModel.Action.ShowReports -> {
                    reportAdapter.setReportList(it.listOfReports)
                    showSnackBar("Download Report")
                }
                is ReportViewModel.Action.RefreshReports -> {
                    reportAdapter.setReportList(it.listOfReports)
                    showSnackBar("Refresh Report")
                }
                ReportViewModel.Action.AddReport -> {
                    startMainActivityFlipper()
                    sendMessage()
                }
                ReportViewModel.Action.UpDateReport -> {
                    startMainActivityFlipper()
                    sendMessage()
                }
                ReportViewModel.Action.DeleteReport ->
                    sendMessage()
                ReportViewModel.Action.DoNotSaveReport ->
                    startMainActivityFlipper()
                ReportViewModel.Action.GoToFormReportFlipper ->
                    startAddReportFlipper()
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
                    it.exception.message?.let { message ->
                        showSnackBar(message)
                    }
                is ReportViewModel.Errors.PrivateReportException ->
                    it.exception.message?.let { message ->
                        showSnackBar(message)
                    }
                is ReportViewModel.Errors.AddReportException ->
                    it.exception.message?.let { message ->
                        showSnackBar(message)
                        finish()
                    }
                is ReportViewModel.Errors.UpDateReportException ->
                    it.exception.message?.let { message ->
                        showSnackBar(message)
                        finish()
                    }
                ReportViewModel.Errors.NameIsEmpty ->
                    showToast("Please insert a name", true)
            }
        }
    }

    private fun binding() {
        binding.addReports.reportDate.setOnClickListener {
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.show(supportFragmentManager, "date picker")
        }
        binding.addReports.reportTime.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.show(supportFragmentManager, "time picker")
        }
    }

    private fun startAddReportFlipper() {
        binding.viewNavigationReports.displayedChild =
            binding.viewNavigationReports.indexOfChild(findViewById(R.id.add_reports))
    }

    private fun startMainActivityFlipper() {
        binding.viewNavigationReports.displayedChild =
            binding.viewNavigationReports.indexOfChild(findViewById(R.id.get_reports))
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        binding.addReports.reportDate.text = currentDateString
        reportViewModel.date.value = currentDateString
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        binding.addReports.reportTime.text = "$hourOfDay:$minute"
        reportViewModel.time.value = "$hourOfDay:$minute"
    }

    companion object {
        private const val ENTER_YOUR_PUB_KEY = "pub-c-27e12d06-a0d8-40bd-b1e6-2343d730d8f4"
        private const val ENTER_YOUR_SUB_KEY = "sub-c-4eaa6cb4-8b27-11eb-99bb-ce4b510ebf19"
    }
}
