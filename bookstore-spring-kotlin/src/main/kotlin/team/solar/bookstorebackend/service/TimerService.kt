package team.solar.bookstorebackend.service

import java.time.Duration

interface TimerService {
    fun start()

    fun duration(): Duration
}