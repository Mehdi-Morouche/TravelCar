package com.mehdi.travelcar.viewmodel

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mehdi.travelcar.adapter.Adapter
import com.mehdi.travelcar.api.CarService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by mehdi on 2020-02-16.
 */

class AccountActivityViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    var TAG = "MainActivityViewModel"

    var adapter = Adapter()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        val retrofit = Retrofit.Builder()
            .baseUrl(CarService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CarService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCars()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        adapter.setData(dataList = response.body()!!)
                    }
                    else {

                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "Exception ${e.message}")
                } catch (e: Throwable) {
                    Log.e(TAG, "Throwable ${e.message}")
                }
            }
        }
    }

    fun filter(text : String) {
        adapter.filterData(text)
    }

    companion object{
        @JvmStatic
        @BindingAdapter("app:load_image")
        fun setImageUrl(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl).apply(RequestOptions().circleCrop().fitCenter())
                .into(view)
        }
    }
}