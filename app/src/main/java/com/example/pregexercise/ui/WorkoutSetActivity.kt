package com.example.pregexercise.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pregexercise.BaseActivity
import com.example.pregexercise.R
import com.example.pregexercise.databinding.ActivityWorkoutSetBinding
import com.example.pregexercise.models.WorkoutSetModel
import com.example.pregexercise.ui.adepters.WorkoutSetAdapter
import com.example.pregexercise.utils.Constants

class WorkoutSetActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWorkoutSetBinding
    private lateinit var mWorkoutSetItems : ArrayList<WorkoutSetModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        mWorkoutSetItems = Constants.getWorkoutItems(this)
        getRowsData()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_workout_set -> {
                Log.i("OnClick Adapter >>", "ll_workout_set")
            }
        }
    }


    private fun getRowsData() {
        if (mWorkoutSetItems.size > 0) {
            binding.rvDataItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                binding.rvDataItems.layoutManager = GridLayoutManager(this, 2)
            } else {
                // In portrait
                binding!!.rvDataItems.layoutManager = LinearLayoutManager(this)
            }

            binding!!.rvDataItems.setHasFixedSize(true)
            val itemAdapter = WorkoutSetAdapter(this@WorkoutSetActivity, mWorkoutSetItems)
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvDataItems.adapter = itemAdapter
        } else {
            binding.rvDataItems.visibility = View.GONE
            binding.tvNoItemsFound.visibility = View.VISIBLE
        }

        // Hide Progress dialog.
        hideProgressDialog()
    }


    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = resources.getString(R.string.toolbar_workout_set)
/*
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            onBackPressed()
        }*/
    }

}