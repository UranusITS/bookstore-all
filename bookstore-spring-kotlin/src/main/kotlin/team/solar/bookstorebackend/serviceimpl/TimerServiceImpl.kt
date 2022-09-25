package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.SessionScope
import team.solar.bookstorebackend.service.TimerService
import java.time.Duration
import java.time.Instant

@Service
@SessionScope
class TimerServiceImpl : TimerService {
    private var startTime = Instant.now()

    override fun start() {
        startTime = Instant.now()
    }

    override fun duration(): Duration {
        return Duration.between(startTime, Instant.now())
    }
}