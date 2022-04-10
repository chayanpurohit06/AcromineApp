package com.app.acromine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.acromine.adapter.VarsAdapter
import com.app.acromine.databinding.ActivityVarsListBinding
import com.app.acromine.model.Vars

class VarsListActivity : AppCompatActivity() {

    lateinit var binding : ActivityVarsListBinding
    private val adapter = VarsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_vars_list)
        binding.lifecycleOwner = this
        binding.recyclerview.adapter = adapter
        val varList = intent.getSerializableExtra("Vars") as ArrayList<Vars>
        adapter.setVarsData(varList!!)
    }
}