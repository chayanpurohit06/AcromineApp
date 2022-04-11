package com.app.acromine.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.acromine.repository.AbbreviationRepository
import com.app.acromine.viewmodel.AbbreviationListViewModel


class ViewModelFactory constructor(private val context: Context, private val abbreviationRepository: AbbreviationRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AbbreviationListViewModel::class.java)) {
            AbbreviationListViewModel(this.context,this.abbreviationRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}