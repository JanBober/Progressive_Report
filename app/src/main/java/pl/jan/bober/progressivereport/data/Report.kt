package pl.jan.bober.progressivereport.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report")
class Report(
    @ColumnInfo(name = "Name")
    var name: String,
    @ColumnInfo(name = "Date")
    var date: String,
    @ColumnInfo(name = "Time")
    var time: String,
    @ColumnInfo(name = "User")
    var user: String,
    @ColumnInfo(name = "Local")
    var local: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id = 0
}