package com.nishant.compose.ui.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _timeDelhi = MutableLiveData(intArrayOf(17, 15, 30))
    val timeDelhi: LiveData<IntArray> = _timeDelhi

    fun tickDelhiTime() {
        _timeDelhi.value?.let {
            if (it[2] < 59) {
                _timeDelhi.value = intArrayOf(it[0], it[1], it[2])
                return@let
            }

            if (it[1] < 59) {
                _timeDelhi.value = intArrayOf(it[0], it[1] + 1, 0)
                return@let
            }

            if (it[0] < 23) {
                _timeDelhi.value = intArrayOf(0, it[1], it[2])
                return@let
            }
        }
    }
}