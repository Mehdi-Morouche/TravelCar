package com.mehdi.travelcar

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR.model
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.mehdi.travelcar.databinding.ActivityMainBinding
import com.mehdi.travelcar.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mModel: MainActivityViewModel

    private val LOCATION_REQUEST_CODE = 101

    lateinit var dataBinding: ActivityMainBinding

    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null
    private var fabCloseFast: Animation? = null
    private var fabRotateOpen: Animation? = null
    private var fabRotateClose: Animation? = null
    private var fabRotateCloseFast: Animation? = null

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        dataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            model = mModel
            setLifecycleOwner(this@MainActivity)
            lifecycle.addObserver(mModel)
        }



        setSupportActionBar(toolbar)
        toolbar.title = title

        fabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)

        fabCloseFast = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close_fast)

        fabRotateOpen = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fab_rotate_open
        )

        fabRotateClose = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fab_rotate_close
        )

        fabRotateCloseFast = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fab_rotate_close_fast
        )
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
        else {
            //getPosition()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    makeRequest()
                }
                else {
                    //getPosition()
                }
            }
        }
    }
}