package com.mehdi.travelcar.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mehdi.travelcar.api.CarService
import com.mehdi.travelcar.db.CarDatabase
import com.mehdi.travelcar.entities.CarEntity
import com.mehdi.travelcar.repository.CarRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivityViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private val repository: CarRepository

    var TAG = "MainActivityViewModel"

    val cars: LiveData<List<CarEntity>>

    init {
        val carDao = CarDatabase.getDatabase(application).carDao()
        repository = CarRepository(carDao)
        cars = repository.cars
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insertAll(car: List<CarEntity>) = viewModelScope.launch {
        repository.insertAll(car)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        if (isOnline(getApplication())) {
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
                            insertAll(response.body()!!)
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

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}