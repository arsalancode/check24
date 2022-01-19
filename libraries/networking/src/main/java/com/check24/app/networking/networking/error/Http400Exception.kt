package com.check24.app.networking.networking.error

class Http400Exception(
    code: Int? = null,
    errorBody: HttpErrorResponse? = null
) : HttpErrorException(code, errorBody)