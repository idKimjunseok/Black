package com.black.baseview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.black.module.network.DataModel
import com.black.module.response.AcademiesResponse
import com.black.module.response.ErrorData
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainViewModel (private val model: DataModel): BaseKotlinViewModel() {

    val _academies = MutableLiveData<AcademiesResponse>()
    val academies: LiveData<AcademiesResponse>
        get() = _academies

    val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    fun getErrorMessage(it: Any) : String {
        try {
            return when(it) {
                it as? UnknownHostException, it as? SocketTimeoutException, it as? ConnectException -> {
                    "네트워크 상태를 확인해 주세요"
                }
                it as? HttpException -> {
                    val errorbody : ResponseBody = it.response()?.errorBody()!!
                    val errormessage = errorbody.charStream()
                    val text = GsonBuilder().create().fromJson(errormessage, ErrorData::class.java)
                    text.message
                }
                it as? Throwable -> {
                    val data = it as Throwable
                    data.message?.let {
                        it
                    }?: kotlin.run {
                        ""
                    }
                }
                else -> {
                    ""
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return ""
        }
    }


    fun getAcademies(
        page: Int,
        per_page: Int,
        latitude: Double,
        longitude: Double,
        grade_ids: List<Int>?,
        subject_ids: List<Int>?,
        has_class: Int?,
        has_image: Int?,
        has_payment: Int?,
        sort: String?
    ) {
        addDisposable(
            model.getAcademies(page, per_page, latitude, longitude, grade_ids, subject_ids, has_class, has_image, has_payment, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        _academies.postValue(this)
                    }
                }, {
                    _error.postValue(getErrorMessage(it))
                })
        )
    }
}