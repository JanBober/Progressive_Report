package pl.jan.bober.progressivereport.screens.addreport

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import pl.jan.bober.progressivereport.R
import pl.jan.bober.progressivereport.base.BaseActivity
import pl.jan.bober.progressivereport.base.ktx.showSnackBar
import pl.jan.bober.progressivereport.base.ktx.showToast
import pl.jan.bober.progressivereport.databinding.ActivityAddReportBinding
import pl.jan.bober.progressivereport.screens.fragment.DatePickerFragment
import pl.jan.bober.progressivereport.screens.fragment.TimePickerFragment
import pl.jan.bober.progressivereport.screens.main.MainActivity
import java.text.DateFormat
import java.util.*

class AddReport : BaseActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private lateinit var addReportViewModel: AddReportViewModel
    private lateinit var binding: ActivityAddReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addReportViewModel = viewModelOf(AddReportViewModel::class)

        binding = DataBindingUtil.setContentView<ActivityAddReportBinding>(
            this,
            R.layout.activity_add_report
        )
            .also {
                it.lifecycleOwner = this
                it.viewModel = addReportViewModel
            }
        activeOutput()
        binding()
    }

    private fun activeOutput() {
        addReportViewModel.actionReport.observe(this) {
            when (it) {
                is AddReportViewModel.Action.AddReport -> {
                    showToast("add Report", true)
                    // TODO: 28.02.2021 Change feature, don't work
                    startMainActivity()
                }
                AddReportViewModel.Action.DoNotSaveReport ->
                    startMainActivity()
            }
        }
        addReportViewModel.errorReport.observe(this) {
            when (it) {
                is AddReportViewModel.Errors.AddReportException ->
                    it.exception.message?.let { message ->
                        showSnackBar(message)
                        finish()
                    }
                AddReportViewModel.Errors.NameIsEmpty ->
                    showToast("Please insert a name",true)
            }
        }
    }

    private fun binding() {
        binding.reportDate.setOnClickListener {
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.show(supportFragmentManager,"date picker")
        }
        binding.reportTime.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.show(supportFragmentManager, "time picker")
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, AddReport::class.java))
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        binding.reportDate.text = currentDateString
        addReportViewModel.date.value = currentDateString
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        binding.reportTime.text = "$hourOfDay:$minute"
    }
}