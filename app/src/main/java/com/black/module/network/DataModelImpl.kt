package com.black.module.network

import com.black.module.response.AcademiesResponse
import com.black.module.response.BannersResponse
import com.black.module.response.InitResponse
import io.reactivex.Single
import retrofit2.Response

class DataModelImpl(private val service: LinklassService) : DataModel {

    override fun getInit(): Single<Response<InitResponse>> {
        return service.initApi()
    }

    override fun getBanners(): Single<ArrayList<BannersResponse>> {
        return service.banners()
    }

    override fun getAcademies(
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
    ): Single<AcademiesResponse> {
        return service.academies(
            page = page,
            per_page = per_page,
            latitude = latitude,
            longitude = longitude,
            grade_ids = grade_ids,
            subject_ids = subject_ids,
            has_class = has_class,
            has_image = has_image,
            has_payment = has_payment,
            sort = sort_type
        )
    }

    override fun getAcademiesCount(
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
    ): Single<AcademiesResponse> {
        return service.academiescount(
            page = page,
            per_page = per_page,
            latitude = latitude,
            longitude = longitude,
            grade_ids = grade_ids,
            subject_ids = subject_ids,
            exclude_items = 1,
            has_class = has_class,
            has_image = has_image,
            has_payment = has_payment,
            sort = sort_type
        )
    }
}
