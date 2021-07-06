package com.example.democoroutineunite.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("page") val page : Int,
    @SerializedName("per_page") val per_page : Int,
    @SerializedName("total") val total : Int,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("data") val data : List<Data>,
    @SerializedName("support") val support : Support

) {
    override fun toString(): String {
        return "UserResponse(page=$page, per_page=$per_page, total=$total, total_pages=$total_pages, data=$data, support=$support)"
    }
}

data class Support (
    @SerializedName("url") val url : String,
    @SerializedName("text") val text : String
)
data class Data (

    @SerializedName("id") val id : Int,
    @SerializedName("email") val email : String,
    @SerializedName("first_name") val first_name : String,
    @SerializedName("last_name") val last_name : String,
    @SerializedName("avatar") val avatar : String

)
