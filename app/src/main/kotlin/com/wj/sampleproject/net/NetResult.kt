package com.wj.sampleproject.net

import com.wj.sampleproject.base.SnackbarEntity
import com.wj.sampleproject.constants.NET_RESPONSE_CODE_LOGIN_FAILED
import com.wj.sampleproject.constants.NET_RESPONSE_CODE_SUCCESS

/**
 * 网络请求返回数据基本框架
 */
data class NetResult<T>
/**
 * 构造方法
 *
 * @param errorCode 错误码
 * @param errorMsg 错误信息
 * @param data 请求返回数据
 */
constructor(
        var errorCode: Int = -1,
        var errorMsg: String? = "",
        var data: T? = null
) {

    /**
     * 检查返回结果
     *
     * @return 请求是否成功
     */
    fun success(): Boolean {
        if (errorCode == NET_RESPONSE_CODE_LOGIN_FAILED) {
            // TODO 登录失败，需要重新登录
        }
        return errorCode == NET_RESPONSE_CODE_SUCCESS
    }

    fun toError(): SnackbarEntity {
        return SnackbarEntity(errorMsg.orEmpty())
    }
}