package com.kixfobby.softwarelab.model.registration


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Email")
    var email: String?,
    @SerializedName("\$id")
    var id: String?,
    @SerializedName("Id")
    var Id: Int,
    @SerializedName("Name")
    var name: String?,
    @SerializedName("Token")
    var token: String?
)