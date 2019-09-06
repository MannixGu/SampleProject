package com.wj.sampleproject.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.wj.android.base.adapter.FragVpAdapter
import cn.wj.android.base.tools.toast
import com.wj.sampleproject.R
import com.wj.sampleproject.base.ui.BaseActivity
import com.wj.sampleproject.constants.MAIN_BACK_PRESS_INTERVAL_MS
import com.wj.sampleproject.databinding.AppActivityMainBinding
import com.wj.sampleproject.fragment.HomepageFragment
import com.wj.sampleproject.mvvm.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.absoluteValue

class MainActivity
    : BaseActivity<MainViewModel, AppActivityMainBinding>() {

    companion object {
        /**
         * 界面入口
         *
         * @param context Context 对象
         */
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                if (context !is Activity) {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            })
        }
    }

    override val mViewModel: MainViewModel by viewModel()

    /** 上次返回点击时间 */
    private var lastBackPressMs = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_main)

        // 配置适配器
        mBinding.cvpMain.adapter = FragVpAdapter.newBuilder()
                .manager(supportFragmentManager)
                .frags(arrayListOf(
                        HomepageFragment.actionCreate()
                ))
                .build()
    }

    override fun onBackPressed() {
        // 当前时间
        val currentBackPressMs = System.currentTimeMillis()
        if ((currentBackPressMs - lastBackPressMs).absoluteValue > MAIN_BACK_PRESS_INTERVAL_MS) {
            // 间隔时间外，提示
            toast(R.string.app_press_again_to_exit)
            // 保存时间
            lastBackPressMs = currentBackPressMs
        } else {
            // 间隔时间内，退到后台
            moveTaskToBack(true)
        }
    }
}