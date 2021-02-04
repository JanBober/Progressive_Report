package pl.jan.bober.progressivereport.data

import com.google.gson.annotations.SerializedName
import io.swagger.annotations.ApiModelProperty
import java.util.*


class Table {
    @SerializedName("id")
    @get:ApiModelProperty(value = "Line number of the table")
    var id: String? = null

    @SerializedName("name")
    @get:ApiModelProperty(value = "Name line of the table")
    var name: String? = null

    @SerializedName("date")
    @get:ApiModelProperty(value = "Date line in the table")
    var date: String? = null

    @SerializedName("time")
    @get:ApiModelProperty(value = "Line time in the table")
    var time: String? = null

    @SerializedName("user")
    @get:ApiModelProperty(value = "User of the table line")
    var user: String? = null

    @SerializedName("local")
    @get:ApiModelProperty(value = "The name of the premises in the table")
    var local: String? = null

    fun id(id: String): Table {
        this.id = id
        return this
    }

    fun name(name: String): Table {
        this.name = name
        return this
    }

    fun date(date: String): Table {
        this.date = date
        return this
    }

    fun time(time: String): Table {
        this.time = time
        return this
    }

    fun user(user: String): Table {
        this.user = user
        return this
    }

    fun local(local: String): Table {
        this.local = local
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val table = other as Table?
        return this.id == table!!.id &&
                this.name == table.name &&
                this.date == table.date &&
                this.time == table.time &&
                this.user == table.user &&
                this.local == table.local
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, date, time, user, local)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("class Table {\n")
        sb.append(" id: ").append(toIndentedString(id)).append("\n")
        sb.append(" name: ").append(toIndentedString(name)).append("\n")
        sb.append(" date: ").append(toIndentedString(date)).append("\n")
        sb.append(" time: ").append(toIndentedString(time)).append("\n")
        sb.append(" user: ").append(toIndentedString(user)).append("\n")
        sb.append(" local: ").append(toIndentedString(local)).append("\n")
        sb.append("}")
        return sb.toString()
    }

    private fun toIndentedString(other: Any?): String {
        return other?.toString()?.replace("\n","\n  ") ?: "null"
    }
}