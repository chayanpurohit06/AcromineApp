package com.app.acromine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.acromine.Util.Utils

import com.app.acromine.adapter.AbbreviationAdapter

import com.app.acromine.repository.AbbreviationRepository
import com.app.acromine.databinding.ActivityAbbreviationListBinding
import com.app.acromine.service.RetrofitService
import com.app.acromine.viewmodel.AbbreviationListViewModel
import com.app.acromine.factory.ViewModelFactory


class AbbreviationListActivity : AppCompatActivity() {

    lateinit var binding :ActivityAbbreviationListBinding
    lateinit var abbreviationListViewModel: AbbreviationListViewModel
    private val adapter = AbbreviationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abbreviation_list)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_abbreviation_list)
        val retrofitService = RetrofitService.getInstance()
        val abbreviationRepository = AbbreviationRepository(retrofitService)
        binding.recyclerview.adapter = adapter
        abbreviationListViewModel = ViewModelProvider(this, ViewModelFactory(abbreviationRepository)).get(AbbreviationListViewModel::class.java)
        binding.abbreviationListViewModel = abbreviationListViewModel
        binding.lifecycleOwner = this

        abbreviationListViewModel.abbreviationList.observe(this) {
            adapter.setAbbreviationsData(it[0].lfs)
        }

        abbreviationListViewModel.errorMessage.observe(this) {
            Utils.showDialog(this,it)

        }

        abbreviationListViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })


        abbreviationListViewModel.getAllAbbreviationData(this,intent.getStringExtra("AbbreviationTxt")!!,
            intent.getStringExtra("FullFormsTxt")!!)


        adapter.onItemClick = { lfs ->
            val intent = Intent(this, VarsListActivity::class.java)
            intent.putExtra("Vars", lfs.vars)
            startActivity(intent)

        }
    }
}