package io.swagger.client.api

import io.reactivex.Completable
import io.reactivex.Single
import pl.jan.bober.entities.model.Report
import retrofit2.http.*

interface ReportApi {

    @GET("/reports")
    fun apiReportGet(): Single<List<Report>>

    @DELETE("/reports/{id}")
    fun apiReportDelete(
        @Path("id") id: Long
    ): Completable
}