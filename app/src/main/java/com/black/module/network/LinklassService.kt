package com.black.module.network

import com.black.module.response.AcademiesResponse
import com.black.module.response.BannersResponse
import com.black.module.response.InitResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface LinklassService {

    @GET("/app/init")
    fun initApi(
    ): Single<Response<InitResponse>>

    @GET("/app/banners")
    fun banners(
    ): Single<ArrayList<BannersResponse>>

    @GET("/app/academies")
    fun academies(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("grade_ids[]") grade_ids: List<Int>?,
        @Query("subject_ids[]") subject_ids: List<Int>?,
        @Query("has_class") has_class: Int?,
        @Query("has_image") has_image: Int?,
        @Query("has_payment") has_payment: Int?,
        @Query("sort") sort: String?
    ): Single<AcademiesResponse>

    @GET("/app/academies")
    fun academiescount(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("grade_ids[]") grade_ids: List<Int>?,
        @Query("subject_ids[]") subject_ids: List<Int>?,
        @Query("exclude_items") exclude_items: Int,
        @Query("has_class") has_class: Int?,
        @Query("has_image") has_image: Int?,
        @Query("has_payment") has_payment: Int?,
        @Query("sort") sort: String?,

    ): Single<AcademiesResponse>

}
