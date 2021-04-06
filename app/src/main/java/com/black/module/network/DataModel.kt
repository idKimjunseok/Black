package com.black.module.network

import com.black.module.response.AcademiesResponse
import com.black.module.response.BannersResponse
import com.black.module.response.InitResponse
import io.reactivex.Single
import retrofit2.Response

interface DataModel {

    fun getInit(
    ): Single<Response<InitResponse>>

    fun getBanners(
    ): Single<ArrayList<BannersResponse>>

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
        sort_type: String?
    ): Single<AcademiesResponse>

    fun getAcademiesCount(
        page: Int,
        per_page: Int,
        latitude: Double,
        longitude: Double,
        grade_ids: List<Int>?,
        subject_ids: List<Int>?,
        has_class: Int?,
        has_image: Int?,
        has_payment: Int?,
        sort_type: String?
    ): Single<AcademiesResponse>

}

