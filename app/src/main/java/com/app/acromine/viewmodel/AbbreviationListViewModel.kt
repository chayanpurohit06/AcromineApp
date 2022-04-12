package com.app.acromine.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.acromine.model.AbbreviationModel
import kotlinx.coroutines.*
import com.app.acromine.Util.Utils
import com.app.acromine.model.Lfs
import com.app.acromine.repository.AbbreviationRepository

class AbbreviationListViewModel constructor(private val context: Context,private val abbreviationRepo: AbbreviationRepository) :
    ViewModel() {

    val errorMessage = MutableLiveData<String>()

    val abbreviationList = MutableLiveData<List<AbbreviationModel>>()

    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()

    val isToastVisible = MutableLiveData<Boolean>(false)

    fun onTextChanged(s:CharSequence,start:Int,before:Int,count:Int){

        loading.postValue(false)

        if (Utils.isNetworkAvailable(context)) {

            if (!Utils.isSfsOrIfsNullorBlank(s.toString().trim()))
            {
                if(s.toString().trim().length >=3){

                    getAllAbbreviationData(context,s.toString().trim(),"")
                }

            } else {
                Toast.makeText(context, "Please enter Abbreviation to Proceed", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Network connection is not available.", Toast.LENGTH_LONG).show()
        }
    }



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

                            isToastVisible.value = false

                        } else {
                            abbreviationList.postValue(mutableListOf<AbbreviationModel>())
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
        loading.value = false
        if(isToastVisible.value == false){
            errorMessage.value = message
            isToastVisible.value = true
        }
    }



    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}