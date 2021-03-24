package pl.jan.bober.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.jan.bober.entities.model.Report

interface ReportRepository {
    fun getReport(): Single<List<Report>>

    fun postReport(report: Report): Single<Report>

    fun putReport(report: Report): Single<Report>

    fun deleteReport(reportId: Long): Completable
}