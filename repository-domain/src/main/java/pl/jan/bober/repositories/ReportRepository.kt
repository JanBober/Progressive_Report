package pl.jan.bober.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.jan.bober.entities.model.Report

interface ReportRepository {
    fun getReport(): Single<List<Report>>

    fun deleteReport(reportId: Long): Completable
}