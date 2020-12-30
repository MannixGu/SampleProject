package com.wj.sampleproject.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.wj.android.base.ext.string
import com.orhanobut.logger.Logger
import com.wj.sampleproject.R
import com.wj.sampleproject.base.viewmodel.BaseViewModel
import com.wj.sampleproject.ext.snackbarMsg
import com.wj.sampleproject.helper.UserInfoData
import com.wj.sampleproject.model.ProgressModel
import com.wj.sampleproject.model.SnackbarModel
import com.wj.sampleproject.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * 我的 ViewModel，注入 [repository] 获取数据
 */
class MyViewModel(
        private val repository: UserRepository
) : BaseViewModel() {

    /** 控制进度条弹窗显示  */
    val progressData = MutableLiveData<ProgressModel>()

    /** 显示退出登录弹窗数据 */
    val showLogoutDialogData = MutableLiveData<Int>()

    /** 跳转登录 */
    val jumpLoginData = MutableLiveData<Int>()

    /** 跳转我的收藏 */
    val jumpCollectionData = MutableLiveData<Int>()

    /** 跳转收藏网站 */
    val jumpCollectedWebData = MutableLiveData<Int>()

    /** 跳转学习数据 */
    val jumpToStudyData = MutableLiveData<Int>()

    /** 用户头像地址 */
    val avatarUrl: ObservableField<String> = ObservableField()

    /** 用户名 */
    val userName: ObservableField<String> = ObservableField(R.string.app_un_login.string)

    /** 头部点击 */
    val onTopClick: () -> Unit = {
        if (null == UserInfoData.value) {
            // 未登录，跳转登录
            jumpLoginData.value = 0
        } else {
            // 已登录，提示是否退出登录
            showLogoutDialogData.value = 0
        }
    }

    /** 我的收藏点击 */
    val onMyCollectionClick: () -> Unit = {
        if (null == UserInfoData.value) {
            // 未登录，跳转登录
            jumpLoginData.value = 0
        } else {
            // 已登录，跳转我的收藏列表
            jumpCollectionData.value = 0
        }
    }

    /** 收藏网站点击 */
    val onCollectedWebClick: () -> Unit = {
        if (null == UserInfoData.value) {
            // 未登录，跳转登录
            jumpLoginData.value = 0
        } else {
            // 已登录，跳转收藏网站列表
            jumpCollectedWebData.value = 0
        }
    }

    /** 学习入口点击 */
    val onStudyClick: () -> Unit = {
        jumpToStudyData.value = 0
    }

    /** 用户退出登录 */
    fun logout() {
        viewModelScope.launch {
            try {
                // 显示弹窗
                progressData.value = ProgressModel()
                // 请求接口
                val result = repository.logout()
                if (result.success()) {
                    // 退出成功，清空用户信息
                    UserInfoData.value = null
                } else {
                    // 退出失败，提示
                    snackbarData.value = SnackbarModel(result.errorMsg)
                }
            } catch (throwable: Throwable) {
                // 请求异常
                Logger.t("NET").e(throwable, "logout")
                snackbarData.value = throwable.snackbarMsg
            } finally {
                // 隐藏弹窗
                progressData.value = null
            }
        }
    }
}