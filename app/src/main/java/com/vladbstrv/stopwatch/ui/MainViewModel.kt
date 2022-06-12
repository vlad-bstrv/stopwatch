package com.vladbstrv.stopwatch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladbstrv.stopwatch.data.ElapsedTimeCalculator
import com.vladbstrv.stopwatch.data.StopwatchStateCalculator
import com.vladbstrv.stopwatch.data.StopwatchStateHolder
import com.vladbstrv.stopwatch.data.TimestampMillisecondsFormatter
import com.vladbstrv.stopwatch.domain.StopwatchListOrchestrator
import com.vladbstrv.stopwatch.domain.TimestampProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val stopwatchListOrchestrator: StopwatchListOrchestrator): ViewModel() {

    private val _data: MutableLiveData<String> = MutableLiveData<String>()
    val data: LiveData<String> get() = _data
    init {
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                _data.value = it
            }
        }
    }

    fun start() {
        stopwatchListOrchestrator.start()
    }

    fun pause() {
        stopwatchListOrchestrator.pause()
    }

    fun stop() {
        stopwatchListOrchestrator.stop()
    }

}