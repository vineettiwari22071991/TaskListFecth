package com.example.tasklistfecth.api

import com.example.tasklistfecth.model.Listmodel
import io.reactivex.Single
import retrofit2.http.GET

interface ListDataApi {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getListdata(): Single<Listmodel>
}