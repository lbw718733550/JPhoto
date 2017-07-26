package com.lbw.jphoto.service

/**
 * Created by lin on 16/12/21.
 */
class HttpResult<T> {
    var success: Boolean = true
    var message: String? = null
    var contentData: T? = null
}
