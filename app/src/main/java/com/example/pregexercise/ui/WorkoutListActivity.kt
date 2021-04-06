package com.example.pregexercise.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pregexercise.BaseActivity
import com.example.pregexercise.R
import com.example.pregexercise.databinding.ActivityWorkoutListBinding
import com.example.pregexercise.models.WorkoutModel
import com.example.pregexercise.models.WorkoutSetModel
import com.example.pregexercise.ui.adepters.WorkoutAdapter
import com.example.pregexercise.ui.adepters.WorkoutSetAdapter
import com.example.pregexercise.utils.Constants

class WorkoutListActivity : BaseActivity() {

    private lateinit var binding: ActivityWorkoutListBinding

    private lateinit var mWorkoutSetItems : ArrayList<WorkoutSetModel>
    private lateinit var mWorkoutListItems : ArrayList<WorkoutModel>

    private var mSetId: String = ""
    private var mSetName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.INTENT_WORKOUT_SET_ID)) {
            mSetId = intent.getStringExtra(Constants.INTENT_WORKOUT_SET_ID)!!
        }
        if (intent.hasExtra(Constants.INTENT_WORKOUT_SET_NAME)) {
            mSetName = intent.getStringExtra(Constants.INTENT_WORKOUT_SET_NAME)!!
            //binding.tvWorkoutListTitle.text = mSetName
        }

        setupActionBar()
        getRowsData()

        binding.btnStartExercise.setOnClickListener {
            val intent = Intent(this@WorkoutListActivity, WorkoutActivity::class.java)
            intent.putExtra(Constants.INTENT_WORKOUT_SET_ID, mSetId)
            intent.putExtra(Constants.INTENT_WORKOUT_SET_NAME, mSetName)
            startActivity(intent)
        }
    }

    private fun getRowsData() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        mWorkoutSetItems = Constants.getWorkoutItems(this)
        if (mWorkoutSetItems.size > 0) {
            /*** WORKOUT LIST* */
            mWorkoutSetItems.filter { it.id == mSetId }.forEach { selectedSet ->

                mWorkoutListItems = selectedSet.workouts!!
            }
            binding.rvDataItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                binding.rvDataItems.layoutManager = GridLayoutManager(this, 2)
            } else {
                // In portrait
                binding.rvDataItems.layoutManager = LinearLayoutManager(this)
            }

            binding.rvDataItems.setHasFixedSize(true)
            val itemAdapter = WorkoutAdapter(this@WorkoutListActivity, mWorkoutListItems)
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
        binding.tvTitle.text = mSetName //resources.getString(R.string.toolbar_workout_list)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /**
     * Toolbar Activity
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.go_to_history -> {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}