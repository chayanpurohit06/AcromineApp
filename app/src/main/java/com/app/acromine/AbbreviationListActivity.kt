package com.app.acromine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.app.acromine.model.Lfs


class AbbreviationListActivity : AppCompatActivity() {

    lateinit var binding: ActivityAbbreviationListBinding
    lateinit var abbreviationListViewModel: AbbreviationListViewModel
    private val adapter = AbbreviationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abbreviation_list)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_abbreviation_list)
        val retrofitService = RetrofitService.getInstance()
        val abbreviationRepository = AbbreviationRepository(retrofitService)
        binding.recyclerview.adapter = adapter
        abbreviationListViewModel =
            ViewModelProvider(this, ViewModelFactory(this, abbreviationRepository)).get(
                AbbreviationListViewModel::class.java
            )
        binding.abbreviationListViewModel = abbreviationListViewModel
        binding.lifecycleOwner = this

        abbreviationListViewModel.abbreviationList.observe(this) {
            if (it.isNotEmpty()) {
                adapter.setAbbreviationsData(it[0].lfs)
                Utils.hideSoftKeyBoard(this, binding.root)
            } else {
                adapter.setAbbreviationsData(mutableListOf<Lfs>())
            }
        }

        abbreviationListViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

        }

        abbreviationListViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        adapter.onItemClick = { lfs ->
            val intent = Intent(this, VarsListActivity::class.java)
            intent.putExtra("Vars", lfs.vars)
            startActivity(intent)

        }
    }
}