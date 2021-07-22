package com.oip.carapp

import com.google.gson.annotations.SerializedName

class CampaignData(
    @SerializedName("campaign_id") val campaignId: Int = 0,
    @SerializedName("category_id") val categoryId: Int = 0,
    @SerializedName("category_name") val categoryName: String = "",
    @SerializedName("image_path") val imagePath: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("is_featured") val isFeatured: Int = 0,
    @SerializedName("campaign_created_at") val campaignCreatedAt: String = "",
    @SerializedName("amount") val amount: Int = 0,
    @SerializedName("status") val status: Int = 0,
    @SerializedName("donatedAmount") val donatedAmount: String = "",
    @SerializedName("totalDonors") val totalDonors: Int = 0,
    @SerializedName("last_donated") val last_donated: String = ""
)