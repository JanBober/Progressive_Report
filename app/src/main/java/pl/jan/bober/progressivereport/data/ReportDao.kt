package pl.jan.bober.progressivereport.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReportDao {
    @get:Query("SELECT * FROM report")
    val allReport: LiveData<List<Report>>

    @Insert
    fun insert(report: Report)

    @Update
    fun update(report: Report)

    @Delete
    fun delete(report: Report)

    @Query("DELETE FROM report")
    fun deleteAllReport()
}