package com.app.acromine.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vars (

    @SerializedName("lf") var lf: String? = null,
    @SerializedName("freq") var freq: Int? = null,
    @SerializedName("since") var since: Int? = null

):Serializable