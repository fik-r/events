package com.fizus.events.fragment.content.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fizus.events.base.BaseViewModel
import com.fizus.events.model.Event
import com.fizus.events.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsViewModel(private val eventRepository: EventRepository) : BaseViewModel() {

    private val eventsLiveData: MutableLiveData<List<Event>> = MutableLiveData()

    fun onLoadEvents(): LiveData<List<Event>> {
        return eventsLiveData
    }

    suspend fun loadEvents(type: String) {
        try {
            loading.value = true
            withContext(Dispatchers.IO) { return@withContext eventRepository.getListEvent(type) }
                .let { eventsLiveData.value = it }
        } catch (e: Exception) {
            e.printStackTrace()
            error.value = mapOf(0 to e.message.toString())
        } finally {
            loading.value = false
        }
    }
}