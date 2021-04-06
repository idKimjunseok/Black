package com.black.module

import com.black.module.network.DataModel
import com.black.module.network.DataModelImpl
import com.black.module.network.LinklassService
import com.black.util.MyLog
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

var retrofitModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    var requestBuilder = original.newBuilder()
//                        .addHeader("User-Agent", "google/AOS/${BuildConfig.VERSION_NAME}")

//                    PreferencesManager.getString(PreferencesManager.PREF_DEVICE_ID)?.let {
//                        requestBuilder.addHeader("UDID", it)
//                    }
//
//                    if (!PreferencesManager.getString(PreferencesManager.PREF_ACCESS_TOKEN).isNullOrEmpty()) {
//                        requestBuilder.addHeader(
//                            "Authorization",
//                            "Bearer " + PreferencesManager.getString(PreferencesManager.PREF_ACCESS_TOKEN)
//                        )
//                    }
                    val request = requestBuilder.build()

                    return chain.proceed(request)
                }
            })
            .addInterceptor(
//                if (BuildConfig.BASE_URL != MyConfig.INIT_SERVER) {
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
//                } else {
//                    HttpLoggingInterceptor()
//                        .setLevel(HttpLoggingInterceptor.Level.NONE)
//                }
            )
            .authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    MyLog.e("response : " + response.code)
                    return null
                }
            })
            .build()
    }

    single<LinklassService> {
        Retrofit.Builder()
            .baseUrl("https://google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(LinklassService::class.java)

    }

}

var adapterModule = module {
    factory {
    }
}

var modelModule = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
}
var viewModelModule = module {
//    viewModel {
//        MainViewModel(get())
//    }
}

//var analyticsModule = module {
//    single { Analytics(get()) }
//}

var listOfModule = listOf(
    retrofitModule,
    adapterModule,
    modelModule,
    viewModelModule,
//    analyticsModule
)
