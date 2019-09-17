package com.wj.sampleproject.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.wj.android.base.ext.orEmpty
import com.wj.sampleproject.base.mvvm.BaseViewModel
import com.wj.sampleproject.entity.SystemCategoryEntity
import com.wj.sampleproject.ext.snackbarMsg
import com.wj.sampleproject.ext.toSnackbarMsg
import com.wj.sampleproject.repository.SystemRepository
import kotlinx.coroutines.launch

/**
 * 体系目录列表 ViewModel
 */
class SystemCategoryViewModel
/**
 * @param repository 体系相关数据仓库
 */
constructor(private val repository: SystemRepository)
    : BaseViewModel() {

    override fun onCreate(source: LifecycleOwner) {
        super.onCreate(source)

        // 获取分类数据
        getSystemCategoryList()
    }

    /** TODO 目录点击 */
    val onCategoryItemClick: (SystemCategoryEntity) -> Unit = { item ->
        snackbarData.postValue(item.name.toSnackbarMsg())
    }

    /** 列表数据 */
    val listData = MutableLiveData<ArrayList<SystemCategoryEntity>>()

    /**
     * 获取分类数据
     */
    private fun getSystemCategoryList() {
        viewModelScope.launch {
            try {
                val result = repository.getSystemCategoryList()
                if (result.success()) {
                    // 获取成功
                    listData.postValue(result.data.orEmpty())
                } else {
                    snackbarData.postValue(result.toError())
                }
            } catch (throwable: Throwable) {
                snackbarData.postValue(throwable.snackbarMsg)
            }
        }
    }
}