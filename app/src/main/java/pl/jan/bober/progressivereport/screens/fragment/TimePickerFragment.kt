package pl.jan.bober.progressivereport.screens.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import pl.jan.bober.progressivereport.base.BaseDialogFragment
import java.util.*

class TimePickerFragment : BaseDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        return TimePickerDialog(
            requireActivity(),
            activity as TimePickerDialog.OnTimeSetListener?,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }
}