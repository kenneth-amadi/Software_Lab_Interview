package com.kixfobby.softwarelab.model.registration


import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    var code: Int,
    var `data`: Data?,
    @SerializedName("\$id")
    var id: String?,
    var message: String?
)