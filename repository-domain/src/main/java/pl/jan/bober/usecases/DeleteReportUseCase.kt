package pl.jan.bober.usecases

import io.reactivex.Completable
import pl.jan.bober.repositories.ReportRepository

class DeleteReportUseCase(private val reportRepository: ReportRepository) {

    fun execute(reportId: Long): Completable {
        return reportRepository.deleteReport(reportId)
    }
}