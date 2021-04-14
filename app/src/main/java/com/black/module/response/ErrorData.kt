package com.black.module.response

import java.io.Serializable

data class ErrorData(
    var code: Int,
    var message: String
) : Serializable