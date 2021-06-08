package com.maryang.testsample.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Issue(
    @SerializedName("id")
    val id: Long,
    @SerializedName("number")
    val number: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("assignees")
    val assignees: List<User>,
    @SerializedName("comments_url")
    val commentsUrl: String
) : Parcelable
