package com.app.acromine.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.acromine.AbbreviationListActivity
import com.app.acromine.Util.Utils

class MainViewModel : ViewModel() {


    val abbreviationTxt = MutableLiveData<String>()

    val fullFormsTxt = MutableLiveData<String>()

    private val getAbbreviationTxt: LiveData<String> get() = abbreviationTxt

    private val getfullFormsTxt: LiveData<String> get() = fullFormsTxt


    fun proceedOnClick(context: Context) {

        if (Utils.isNetworkAvailable(context)) {

            if (!Utils.isSfsOrIfsNullorBlank(getAbbreviationTxt.value) || !Utils.isSfsOrIfsNullorBlank(getfullFormsTxt.value))
            {
                if (Utils.isSfsOrIfsNullorBlank(getAbbreviationTxt.value) && !Utils.isSfsOrIfsNullorBlank(getfullFormsTxt.value))
                {
                    if (!Utils.isSfsOrIfsEmail(getfullFormsTxt.value)) {

                        switchActivity(context, "", getfullFormsTxt.value)
                    } else {
                        Toast.makeText(context, "Email is not Allowed in Full Forms.", Toast.LENGTH_LONG).show()
                    }

                } else if (!Utils.isSfsOrIfsNullorBlank(getAbbreviationTxt.value) && Utils.isSfsOrIfsNullorBlank(getfullFormsTxt.value))
                {
                    if (!Utils.isSfsOrIfsEmail(getAbbreviationTxt.value)) {

                        switchActivity(context, getAbbreviationTxt.value, "")
                    } else {
                        Toast.makeText(context, "Email is not Allowed in Abbriviations.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (!Utils.isSfsOrIfsEmail(getAbbreviationTxt.value) && !Utils.isSfsOrIfsEmail(getfullFormsTxt.value)) {

                        switchActivity(context, getAbbreviationTxt.value, getfullFormsTxt.value)
                    } else {
                        Toast.makeText(context, "Email is not Allowed in Full Forms or Abbreviations", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(context, "Please enter Abbreviation or Full Forms to Proceed", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Network connection is not available.", Toast.LENGTH_LONG).show()
        }
    }


    fun switchActivity(context: Context, abbreviationValue: String?, fullFormsValue: String?) {
        val intent = Intent(context, AbbreviationListActivity::class.java)
        intent.putExtra("AbbreviationTxt", abbreviationValue)
        intent.putExtra("FullFormsTxt", fullFormsValue)
        context.startActivity(intent)
    }

}