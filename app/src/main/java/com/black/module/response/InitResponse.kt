package com.black.module.response

import androidx.annotation.Keep
import java.io.Serializable


@Keep
data class InitResponse(
    var app: App,
    var attributes: ArrayList<Attributes>,
    var subject_group: SubjectGroup,
    var grades: ArrayList<Grades>,
    var badges: ArrayList<Badges>,
    var subjects: ArrayList<Subjects>,
    var styles: ArrayList<Styles>,
    var current_timestamp: Long,
    var terms: ArrayList<Terms>,
    var user_roles: ArrayList<Userroles>,
    var grade_numbers: ArrayList<Gradenumbers>,
    var school_types: ArrayList<SchoolTypes>,
    var popups: ArrayList<PopUp>,
    var assessments: Assessments,

): Serializable {
    data class App (
        var force_update: Boolean,
        var recommend_update: Boolean,
        var store_version: String,
        var store_url: String,
        var cs_phone_number: String
    ): Serializable

    data class Attributes(
        var id: Int,
        var name: String,
        var attribute_id: Int
    ): Serializable

    data class Grades(
        var id: Int,
        var name: String,
        var fullname: String,
        var grade_id: Int
    ): Serializable

    data class Badges (
        var id: Int,
        var name: String
    ): Serializable

    data class Subjects(
        var id: Int,
        var name: String,
        var subject_id: Int,
        var is_main: Int
    ): Serializable

    data class Terms(
        var code: String,
        var name: String
    ): Serializable

    data class Period(
        var code: String,
        var name: String
    ): Serializable

    data class Userroles(
        var type: String,
        var name: String
    ): Serializable

    data class Styles(
        var id: Int,
        var description: String
    ): Serializable

    data class Gradenumbers(
        var no: Int,
        var name: String,
        var fullname: String
    ): Serializable

    data class SchoolTypes(
        var type: String,
        var grade_numbers: ArrayList<Int>
    ): Serializable

    data class PopUp(
        var id: Int,
        var title: String,
        var image_path: String,
        var link: String
    ): Serializable

    data class SubjectGroup (
        val subjects: ArrayList<Int>,
        val non_subjects: ArrayList<Int>
    ) : Serializable

    data class Assessments (
        val helpful: ArrayList<Assessment>,
        val unhelpful: ArrayList<Assessment>,
    ) : Serializable

    data class Assessment (
        val id: Int,
        val name: String,
    ) : Serializable
}