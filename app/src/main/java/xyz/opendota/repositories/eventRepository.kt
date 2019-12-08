package xyz.opendota.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.HttpException
import xyz.opendota.api.EventsApi
import xyz.opendota.model.Event

interface IEventRepository {
    fun getEvents(offset: Int): Single<List<Event>>
    fun getBookedEvents(): Single<List<Event>>
    fun bookEvent(id: Int): String
}

class EventRepositoryImpl(
    private val localStorage: ILocalRepository,
    private val gson: Gson,
    private val api: EventsApi
) : IEventRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    var listOfEvents: List<Event> = generateEventList()
    var listOfBookedEvents: List<Event> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateEventList(): List<Event> {
        val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
        return arrayListOf(
            Event(
                1,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                2,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                3,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                4,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                5,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                6,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                7,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                8,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                9,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                10,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            ),
            Event(
                11,
                "Title",
                description,
                "https://miro.medium.com/max/1024/0*4ty0Adbdg4dsVBo3.png"
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getEvents(offset: Int): Single<List<Event>> {
        return api.getEvents()
    }

    override fun getBookedEvents(): Single<List<Event>> {
        return Single.fromCallable { listOfBookedEvents }
    }

    override fun bookEvent(id: Int): String {
        for ((cnt, event) in listOfEvents.withIndex()) {
            if (event.id == id) {
                (listOfBookedEvents as MutableList<Event>).add(event)
                (listOfEvents as MutableList<Event>).removeAt(cnt)
                return "${event.id}"
            }
        }
        return "Error"
    }

}