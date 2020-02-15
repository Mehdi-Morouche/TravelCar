package com.mehdi.travelcar.viewModel

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mehdi.travelcar.adapter.Adapter
import com.mehdi.travelcar.api.CarService
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivityViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

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