package com.kixfobby.softwarelab.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    var code: Int,
    var `data`: Data?,
    @SerializedName("\$id")
    var id: String?,
    var message: String?
)