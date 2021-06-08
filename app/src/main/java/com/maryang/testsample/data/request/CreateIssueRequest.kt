package com.maryang.testsample.data.request

import com.google.gson.annotations.SerializedName


data class CreateIssueRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)
