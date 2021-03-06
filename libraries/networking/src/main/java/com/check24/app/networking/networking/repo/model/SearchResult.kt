package com.check24.app.networking.networking.repo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResult(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_results")
    val inComplete: Boolean,

    @SerializedName("items")
    val items: List<Product>

) : Parcelable {
    override fun toString(): String {
        return "SearchResult(totalCount='$totalCount', inComplete=$inComplete, items=$items)"
    }
}
