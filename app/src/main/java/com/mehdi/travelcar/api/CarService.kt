package com.mehdi.travelcar.api

import com.mehdi.travelcar.carset.data.CarSet
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by mehdi on 2020-02-15.
 */

interface CarService {

    companion object {
        const val ENDPOINT = "https://gist.githubusercontent.com/ncltg/6a74a0143a8202a5597ef3451bde0d5a/raw/"
    }

    @GET("8fa93591ad4c3415c9e666f888e549fb8f945eb7/tc-test-ios.json")
    suspend fun getCars(): Response<List<CarSet>>

}