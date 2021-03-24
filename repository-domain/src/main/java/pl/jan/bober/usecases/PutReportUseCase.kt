package pl.jan.bober.usecases

import io.reactivex.Single
import pl.jan.bober.entities.model.Report
import pl.jan.bober.repositories.ReportRepository

class PutReportUseCase(private val reportRepository: ReportRepository) {

    fun execute(report: Report): Single<Report> {
        return reportRepository.putReport(report)
    }
}