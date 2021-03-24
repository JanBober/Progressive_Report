package pl.jan.bober.repositories

import io.reactivex.Completable
import io.reactivex.Single
import io.swagger.client.api.ReportApi
import pl.jan.bober.entities.model.Report

class ReportRepositoryImpl(private val reportApi: ReportApi) : ReportRepository {

    override fun getReport(): Single<List<Report>> {
        return reportApi.apiReportGet()
    }

    override fun postReport(report: Report): Single<Report> {
        return reportApi.apiReportPost(report)
    }

    override fun putReport(report: Report): Single<Report> {
        return reportApi.apiReportPut(report)
    }

    override fun deleteReport(reportId: Long): Completable {
        return reportApi.apiReportDelete(reportId)
    }
}