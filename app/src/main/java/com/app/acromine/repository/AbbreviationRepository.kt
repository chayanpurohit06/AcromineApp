package com.app.acromine.repository

import com.app.acromine.service.RetrofitService

class AbbreviationRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllAbbreviations(sfs:String,lfs:String) = retrofitService.getAllAbbreviations(sfs,lfs)


}