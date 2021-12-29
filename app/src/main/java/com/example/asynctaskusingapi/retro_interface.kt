package com.example.asynctaskusingapi

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.GET

interface retro_interface {

    @GET(" ")
    suspend fun getdata():test_data

}