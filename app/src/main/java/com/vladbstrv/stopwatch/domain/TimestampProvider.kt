package com.vladbstrv.stopwatch.domain

interface TimestampProvider {
    fun getMilliseconds(): Long
}