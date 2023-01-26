package com.example.m16_architecture.data

import com.example.m16_architecture.entity.UsefulActivity
import com.google.gson.annotations.SerializedName

data class UsefulActivityDTO(

    @SerializedName("activity") override val activity : String,
    @SerializedName("type") override val type : String,
    @SerializedName("participants") override val participants : Int,
    @SerializedName("price") override val price : Double,
    @SerializedName("link") override val link : String,
    @SerializedName("key") override val key : Int,
    @SerializedName("accessibility") override val accessibility : Double

) : UsefulActivity {
}