package xyz.opendota.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import xyz.opendota.di.applySchedulersSingle
import xyz.opendota.model.Event
import xyz.opendota.repositories.IEventRepository
import xyz.opendota.utils.AppConstants
import xyz.opendota.utils.base.BaseViewModel

class HomeViewModel(
    private val eventRepository: IEventRepository
) : BaseViewModel() {

    val liveData by lazy {
        MutableLiveData<EventData>()
    }

    private var currentPage = AppConstants.DEFAULT_PAGE

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEvents() {
        disposables.add(
            eventRepository.getEvents(offset = currentPage * AppConstants.PAGE_LIMIT)
                .compose(applySchedulersSingle())
                .doOnSubscribe { liveData.value = EventData.ShowLoading }
                .doFinally { liveData.value = EventData.HideLoading }
                .subscribe(
                    { result ->
                        if (currentPage == AppConstants.DEFAULT_PAGE) {
                            liveData.value = EventData.Result(result)
                        } else {
                            liveData.value = EventData.LoadMoreResult(result)
                            liveData.value = EventData.LoadMoreFinished
                        }
                        liveData.value = EventData.Result(result)
                        currentPage++
                        Log.d("HomeViewModel", result.toString())
                    },
                    { error ->
                        liveData.value = EventData.Error(error.message)
                        liveData.value = EventData.LoadMoreFinished
                        Log.e("HomeViewModel", error.toString())
                    }
                )
        )
    }

    fun bookEvent(id: Int) {
        eventRepository.bookEvent(id)
        liveData.value = EventData.ResultBooked
    }

    sealed class EventData {
        object HideLoading : EventData()
        object ShowLoading : EventData()
        object ResultBooked : EventData()
        data class Result(val events: List<Event>) : EventData()
        data class Error(val message: String?) : EventData()
        object LoadMoreFinished : EventData()
        data class LoadMoreResult(val bedRoomInfo: List<Event>) : EventData()
    }
}