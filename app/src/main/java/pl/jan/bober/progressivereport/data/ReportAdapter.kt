package pl.jan.bober.progressivereport.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.jan.bober.progressivereport.databinding.ReportItemBinding

class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    private val report = mutableListOf<Report>()
    private val accepts = mutableListOf<Boolean>()

    var onClick: (Report) -> Unit = {}

    fun setReportList(reportList: List<Report>) {
        report.clear()
        report.addAll(reportList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReportViewHolder(
        ReportItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = report.size

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) =
        holder.bind(report[position], onClick)

    class ReportViewHolder(
        private val binding: ReportItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            report: Report,
            onClick: (Report) -> Unit
        ) {
            onClick(report)
        }
    }
}