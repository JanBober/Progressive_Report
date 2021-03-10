package pl.jan.bober.progressivereport.screens.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import pl.jan.bober.progressivereport.base.BaseDialogFragment
import java.util.*

class DatePickerFragment : BaseDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(requireActivity(), activity as OnDateSetListener?, year, month, day)
    }
}