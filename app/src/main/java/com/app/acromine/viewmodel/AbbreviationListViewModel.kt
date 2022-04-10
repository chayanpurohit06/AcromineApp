package com.app.acromine.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.acromine.model.AbbreviationModel
import kotlinx.coroutines.*
import com.app.acromine.Util.Utils
import com.app.acromine.repository.AbbreviationRepository

class AbbreviationListViewModel constructor(private val abbreviationRepo: AbbreviationRepository) :
    ViewModel() {

    val errorMessage = MutableLiveData<String>()

    val abbreviationList = MutableLiveData<List<AbbreviationModel>>()

    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()

    fun getAllAbbreviationData(context: Context, abbreviationText: String, fullForms: String) {


        if (Utils.isNetworkAvailable(context)) {

            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

                loading.postValue(true)

                val response = abbreviationRepo.getAllAbbreviations(abbreviationText,fullForms)

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {

                        if (response.body()!!.isNotEmpty()) {

                            abbreviationList.postValue(response.body())

                            loading.value = false

                        } else {
                            onError("Data not Available,Please try again with other input.")
                        }
                    } else {
                        onError("${response.message()} ")
                    }
                }
            }
        } else {
            onError("Network connection is not available.Please try again after some time.")
        }

    }

    fun getAbbreviationData(): LiveData<List<AbbreviationModel>> {
        return abbreviationList
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}