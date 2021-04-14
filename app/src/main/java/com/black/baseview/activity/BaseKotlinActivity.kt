package com.black.baseview.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.black.baseview.viewmodel.BaseKotlinViewModel

abstract class BaseKotlinActivity <T : ViewDataBinding, L : BaseKotlinViewModel> :
    AppCompatActivity(), View.OnClickListener {

    lateinit var viewDataBinding: T

    abstract val layoutResourceId: Int

    abstract val viewModel: L

    abstract fun initStartView()

    abstract fun initDataBinding()

    abstract fun initAfterBinding()

    protected abstract fun setDefaultTransAnimation()

//    protected var options: ActivityTransOptions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)

        initStartView()
        initDataBinding()
        initAfterBinding()
        setDefaultTransAnimation()
    }
}