package pl.jan.bober.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import pl.jan.bober.entities.model.Report

@Dao
interface ReportDao {

    @Query("SELECT * FROM report")
    fun getReports(): Single<List<Report>>

    @Insert
    fun insert(report: Report): Completable

    @Update
    fun update(report: Report): Completable

    @Delete
    fun delete(report: Report): Completable

    @Query("DELETE FROM report")
    fun deleteAllReport(): Completable
}