package com.app.acromine.model

import com.google.gson.annotations.SerializedName

data class AbbreviationModel(

    @SerializedName("sf") var sf: String? = null,
    @SerializedName("lfs") var lfs: ArrayList<Lfs> = arrayListOf()

)
