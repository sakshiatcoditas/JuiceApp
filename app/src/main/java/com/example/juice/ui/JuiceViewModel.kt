package com.example.juice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.juice.data.Juice
import com.example.juice.data.RoomJuiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JuiceViewModel(private val juiceRepository: RoomJuiceRepository) : ViewModel() {

    // --- From TrackerViewModel ---
    val juicesStream: Flow<List<Juice>> = juiceRepository.juicesStream

    fun deleteJuice(juice: Juice) = viewModelScope.launch {
        juiceRepository.deleteJuice(juice)
    }

    // --- From EntryViewModel ---
    fun getJuiceStream(id: Long): Flow<Juice?> = juiceRepository.getJuiceStream(id)

    fun saveJuice(
        id: Long,
        name: String,
        description: String,
        color: String,
        rating: Int
    ) {
        if (name.isBlank()) return

        val juice = Juice(id, name.trim(), description.trim(), color, rating)
        viewModelScope.launch {
            if (id > 0) {
                juiceRepository.updateJuice(juice)
            } else {
                juiceRepository.addJuice(juice)
            }
        }
    }

    // --- Factory for creating the ViewModel with a repository ---
    class Factory(private val repository: RoomJuiceRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(JuiceViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return JuiceViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
