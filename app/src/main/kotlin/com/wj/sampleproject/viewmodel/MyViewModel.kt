package com.wj.sampleproject.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.wj.android.base.databinding.BindingField
import cn.wj.android.base.ext.tagableScope
import cn.wj.android.base.tools.getString
import cn.wj.android.logger.Logger
import com.wj.sampleproject.R
import com.wj.sampleproject.base.mvvm.BaseViewModel
import com.wj.sampleproject.dialog.GeneralDialog
import com.wj.sampleproject.ext.snackbarMsg
import com.wj.sampleproject.helper.UserHelper
import com.wj.sampleproject.model.ProgressModel
import com.wj.sampleproject.model.SnackbarModel
import com.wj.sampleproject.repository.MyRepository
import kotlinx.coroutines.launch

/**
 * 我的 ViewModel
 */
class MyViewModel
/**
 * 主构造函数
 *
 * @param repository 我的相关数据仓库
 */
constructor(private val repository: MyRepository)
    : BaseViewModel() {

    /** 跳转登录 */
    val jumpLoginData = MutableLiveData<Int>()
    /** 跳转我的收藏 */
    val jumpCollectionData = MutableLiveData<Int>()
    /** 跳转收藏网站 */
    val jumpCollectedWebData = MutableLiveData<Int>()

    /** 用户头像地址 */
    val avatarUrl: BindingField<String> = BindingField()

    /** 用户名 */
    val userName: BindingField<String> = BindingField(R.string.app_un_login.getString())

    /** 头部点击 */
    val onTopClick: () -> Unit = {
        if (null == UserHelper.userInfo) {
            // 未登录，跳转登录
            jumpLoginData.postValue(0)
        } else {
            // 已登录，提示是否退出登录
            showDialogData.postValue(GeneralDialog.newBuilder()
                    .contentStr(R.string.app_are_you_sure_to_logout.getString())
                    .setOnPositiveAction {
                        // 退出登录
                        logout()
                    })
        }
    }

    /** 我的收藏点击 */
    val onMyCollectionClick: () -> Unit = {
        if (null == UserHelper.userInfo) {
            // 未登录，跳转登录
            jumpLoginData.postValue(0)
        } else {
            // 已登录，跳转我的收藏列表
            jumpCollectionData.postValue(0)
        }
    }

    /** 收藏网站点击 */
    val onCollectedWebClick: () -> Unit = {
        if (null == UserHelper.userInfo) {
            // 未登录，跳转登录
            jumpLoginData.postValue(0)
        } else {
            // 已登录，跳转收藏网站列表
            jumpCollectedWebData.postValue(0)
        }
    }

    /**
     * 用户退出登录
     */
    private fun logout() {
        tagableScope.launch {
            try {
                // 显示弹窗
                progressData.postValue(ProgressModel())
                // 请求接口
                val result = repository.logout()
                if (result.success()) {
                    // 退出成功，更新UI显示，清空用户信息
                    userName.set(R.string.app_un_login.getString())
                    avatarUrl.set("")
                    UserHelper.userInfo = null
                } else {
                    // 退出失败，提示
                    snackbarData.postValue(SnackbarModel(result.errorMsg))
                }
            } catch (throwable: Throwable) {
                // 请求异常
                Logger.t("NET").e(throwable, "logout")
                snackbarData.postValue(throwable.snackbarMsg)
            } finally {
                // 隐藏弹窗
                progressData.postValue(null)
            }
        }
    }
}