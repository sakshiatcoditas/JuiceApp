package com.example.juice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.juice.data.Juice
import com.example.juice.data.RoomJuiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import kotlinx.coroutines.launch

@HiltViewModel
class JuiceViewModel @Inject constructor(
    private val juiceRepository: RoomJuiceRepository
) : ViewModel() {

    val juicesStream = juiceRepository.juicesStream

    fun deleteJuice(juice: Juice) = viewModelScope.launch {
        juiceRepository.deleteJuice(juice)
    }

    fun getJuiceStream(id: Long) = juiceRepository.getJuiceStream(id)

    fun saveJuice(id: Long, name: String, description: String, color: String, rating: Int) {
        if (name.isBlank()) return

        val juice = Juice(id, name.trim(), description.trim(), color, rating)
        viewModelScope.launch {
            if (id > 0) juiceRepository.updateJuice(juice)
            else juiceRepository.addJuice(juice)
        }
    }
}
