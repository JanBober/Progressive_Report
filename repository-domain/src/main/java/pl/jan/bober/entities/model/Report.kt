package pl.jan.bober.entities.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.util.*

@Entity(tableName = "report")
@Keep
class Report(
    @get:ApiModelProperty(value = "Name Report")
    @field:SerializedName("name")
    var name: String? = null,

    @get:ApiModelProperty(value = "Date Report")
    @field:SerializedName("date")
    var date: String? = null,

    @get:ApiModelProperty(value = "Time Report")
    @field:SerializedName("time")
    var time: String? = null
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @get:ApiModelProperty(value = "The unique Report Identifier")
    @SerializedName("id")
    var id = 0



    fun id(id: Int): Report {
        this.id = id
        return this
    }
    fun name(name: String): Report {
        this.name = name
        return this
    }
    fun date(date: String): Report {
        this.date = date
        return this
    }
    fun time(time: String): Report {
        this.time = time
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val report = other as Report
        return id == report.id &&
                name == report.name &&
                date == report.date &&
                time == report.time
    }

    override fun hashCode(): Int {
        return Objects.hash(
            id,
            name,
            date,
            time
        )
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class Report {\n")
        sb.append("    id: ").append(toIndentedString(id)).append("\n")
        sb.append("    name: ").append(toIndentedString(name)).append("\n")
        sb.append("    date: ").append(toIndentedString(date)).append("\n")
        sb.append("    time: ").append(toIndentedString(time)).append("\n")
        sb.append("}")
        return sb.toString()
    }

    private fun toIndentedString(o: Any?): String {
        return o?.toString()?.replace("\n","\n    ") ?: "null"
    }
}