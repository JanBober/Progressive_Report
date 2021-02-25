package pl.jan.bober.progressivereport.base.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pl.jan.bober.progressivereport.screens.main.ReportViewModel

object RecyclerViewBindingAdapter {

    /**
     * Bind ItemTouchHelper.SimpleCallback with RecyclerView
     *
     * @param recyclerView      RecyclerView to bind to Swipe
     * @param reportViewModel   ViewModel to bind to the calling methods
     * */

    @JvmStatic
    @BindingAdapter(
        value = ["viewModel"],
        requireAll = true
    )
    fun onSwiped(recyclerView: RecyclerView, reportViewModel: ReportViewModel) {
        ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                reportViewModel.deleteReport(reportViewModel.reports.value?.get(viewHolder.adapterPosition)?.id!!.toLong())
            }
        }).attachToRecyclerView(recyclerView)
    }
}