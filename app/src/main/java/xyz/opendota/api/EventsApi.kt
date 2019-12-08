package xyz.opendota.api

import io.reactivex.Single
import retrofit2.http.GET
import xyz.opendota.model.Event

interface EventsApi {
    @GET("events")
    fun getEvents(): Single<List<Event>>
}
