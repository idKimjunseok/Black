package com.black.module.response

data class AcademiesResponse(

    var total: Int,
    var per_page: Int,
    var total_pager: Int,
    var current_page: Int,

    var ads: ArrayList<MainList>,
    var non_ads: ArrayList<MainList>,
    var items: ArrayList<MainList>,
    var mainlist: ArrayList<MainList>

) {
    data class Banners(
        var id: Int,
        var image_path: String,
        var link: String
    )

    data class MainList(
        var id: Int,
        var name: String,
        var distance: Int,
        var images: ArrayList<String>?,
        var image_url: String?,
        var grade_ids: ArrayList<Int>?,
        var subject_ids: ArrayList<Int>?,
        var badge_ids: ArrayList<Int>?,
        var attribute_ids: ArrayList<Int>?,
        var location: String?,
        var reviews_count: Int,
        var videos_count: Int,
        var recommendation_style: Recommend?,
        var banners : ArrayList<BannersResponse>?,
        var viewtype: Int = 0,
        var adstype: Int,
        var help_count: Int,
        var is_ad: Int
    )

    data class Recommend(
        var id: Int,
        var expectation: Int

    )
}