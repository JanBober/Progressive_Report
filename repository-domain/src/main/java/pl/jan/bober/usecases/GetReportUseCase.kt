package pl.jan.bober.usecases

import io.reactivex.Single
import pl.jan.bober.entities.model.Report
import pl.jan.bober.repositories.ReportRepository

class GetReportUseCase(private val reportRepository: ReportRepository) {

    fun execute(): Single<List<Report>> {
        return reportRepository.getReport()
    }
}