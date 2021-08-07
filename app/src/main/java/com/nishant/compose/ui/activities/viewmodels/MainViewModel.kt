package com.nishant.compose.ui.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishant.compose.repositories.LocalRepository
import com.nishant.compose.repositories.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = LocalRepository()

    private val _timeDelhi = MutableLiveData(intArrayOf(0, 0, 0))
    val timeDelhi: LiveData<IntArray> = _timeDelhi

    private val _timeNewYork = MutableLiveData(intArrayOf(0, 0, 0))
    val timeNewYork: LiveData<IntArray> = _timeNewYork

    private val _timeLondon = MutableLiveData(intArrayOf(0, 0, 0))
    val timeLondon: LiveData<IntArray> = _timeLondon

    private val _timeTokyo = MutableLiveData(intArrayOf(0, 0, 0))
    val timeTokyo: LiveData<IntArray> = _timeTokyo

    init {
        viewModelScope.launch {
            _timeDelhi.value = repository.getDelhiTime()
            _timeNewYork.value = repository.getNewYorkTime()
            _timeLondon.value = repository.getLondonTime()
            _timeTokyo.value = repository.getTokyoTime()
        }
    }

    fun tickTime() {
        tickIndividualTimeZone(_timeDelhi)
        tickIndividualTimeZone(_timeNewYork)
        tickIndividualTimeZone(_timeLondon)
        tickIndividualTimeZone(_timeTokyo)
    }

    private fun tickIndividualTimeZone(location: MutableLiveData<IntArray>) {
        location.value?.let {
            if (it[2] < 59) {
                location.value = intArrayOf(it[0], it[1], it[2] + 1)
                return@let
            }

            if (it[1] < 59) {
                location.value = intArrayOf(it[0], it[1] + 1, 0)
                return@let
            }

            if (it[0] < 23) {
                location.value = intArrayOf(0, it[1], it[2])
                return@let
            }
        }
    }
}