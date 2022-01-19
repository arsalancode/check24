package com.check24.app.networking.networking.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("message") val message: String
)