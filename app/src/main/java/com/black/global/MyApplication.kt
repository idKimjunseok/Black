package com.black.global

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import com.black.module.listOfModule
import com.black.util.Util.webViewSetPath
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    companion object {
        lateinit var instance: MyApplication
        var mAppStatus = AppStatus.FOREGROUND
    }

    fun get(context: Context): MyApplication {
        return context.applicationContext as MyApplication
    }


    fun getAppStatus() : AppStatus {
        return mAppStatus
    }

    enum class AppStatus {
        BACKGROUND, RETURNED_TO_FOREGROUND, FOREGROUND;
    }

    override fun onCreate() {
        super.onCreate()

        webViewSetPath()

        MultiDex.install(this)

        startKoin {
            androidContext(this@MyApplication)
            logger(AndroidLogger(Level.DEBUG))
            modules(listOfModule)
        }

        registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
        MyAppGlideModule()
    }

    private class AdjustLifecycleCallbacks : ActivityLifecycleCallbacks {
        var running : Int = 0

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity?) {
            if (++running == 1) {
                // running activity is 1,
                // app must be returned from background just now (or first launch)
                mAppStatus = AppStatus.RETURNED_TO_FOREGROUND
            } else if (running > 1) {
                // 2 or more running activities,
                // should be foreground already.
                mAppStatus = AppStatus.FOREGROUND
            }
        }

        override fun onActivityStopped(activity: Activity?) {
            if (--running == 0) {
                // no active activity
                // app goes to background
                mAppStatus = AppStatus.BACKGROUND
            }
        }
    }
}