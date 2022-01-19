package com.check24.app.networking.networking.error

open class HttpErrorException(
    val conde: Int? = null,
    val errorBody: HttpErrorResponse? = null
) : Throwable() {
    override val message: String?
        get() = super.message
}