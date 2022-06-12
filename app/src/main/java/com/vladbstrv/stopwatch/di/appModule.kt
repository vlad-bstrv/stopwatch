package com.vladbstrv.stopwatch.di

import com.vladbstrv.stopwatch.data.ElapsedTimeCalculator
import com.vladbstrv.stopwatch.data.StopwatchStateCalculator
import com.vladbstrv.stopwatch.data.StopwatchStateHolder
import com.vladbstrv.stopwatch.data.TimestampMillisecondsFormatter
import com.vladbstrv.stopwatch.domain.StopwatchListOrchestrator
import com.vladbstrv.stopwatch.domain.TimestampProvider
import com.vladbstrv.stopwatch.ui.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<TimestampProvider> {
        object : TimestampProvider {
            override fun getMilliseconds(): Long {
                return System.currentTimeMillis()
            }
        }
    }

    factory { ElapsedTimeCalculator(timestampProvider = get()) }
    factory {
        StopwatchStateCalculator(
            timestampProvider = get(),
            elapsedTimeCalculator = get()
        )
    }
    factory { TimestampMillisecondsFormatter() }

    factory {
        StopwatchStateHolder(
            elapsedTimeCalculator = get(),
            stopwatchStateCalculator = get(),
            timestampMillisecondsFormatter = get()
        )
    }
    factory { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    factory {
        StopwatchListOrchestrator(
            stopwatchStateHolder = get(),
            scope = get()
        )
    }

    viewModel { MainViewModel(stopwatchListOrchestrator = get()) }
}



