package com.maryang.testsample.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class GithubRepo(
    @PrimaryKey
    @SerializedName("id")
    var id: Long,
    @SerializedName("name")
    var name: String,
    @SerializedName("private")
    var private: Boolean,
    @Ignore
    @SerializedName("owner")
    var owner: User,
    @SerializedName("html_url")
    var url: String,
    @SerializedName("description")
    var description: String?,
    @SerializedName("stargazers_count")
    var stargazersCount: Int,
    @SerializedName("watchers_count")
    var watchersCount: Int,
    @SerializedName("forks_count")
    var forksCount: Int,
    @SerializedName("open_issues_count")
    var openIssuesCount: Int,
    @SerializedName("created_at")
    var createdAt: Date,
    @SerializedName("updated_at")
    var updatedAt: Date,
    @SerializedName("pushed_at")
    var pushedAt: Date,
    @Expose
    var star: Boolean = false
) : Parcelable {
    constructor() : this(
        0, "", false, User(
            0, "", "", "", ""
        ), "", "", 0, 0, 0, 0,
        Date(), Date(), Date()
    )
}
