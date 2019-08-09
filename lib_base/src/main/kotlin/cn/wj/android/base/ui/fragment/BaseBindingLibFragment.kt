package cn.wj.android.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.wj.android.base.mvvm.BaseMvvmViewModel

/**
 * Fragment基类
 * - 添加了对 DataBinding 的支持
 * - [onCreateView] 方法中处理了对 [mBinding] 的初始化
 *
 * @param VM MVVM ViewModel 类，继承 [BaseMvvmViewModel]
 * @param DB [ViewDataBinding] 对象
 *
 * @author 王杰
 */
abstract class BaseBindingLibFragment<VM : BaseMvvmViewModel, DB : ViewDataBinding>
    : BaseMvvmLibFragment<VM>() {

    /** DataBinding 对象 */
    protected lateinit var mBinding: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 初始化 DataBinding
        mBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false)

        mRootView = mBinding.root

        // 初始化布局
        initView()

        return mRootView
    }
}