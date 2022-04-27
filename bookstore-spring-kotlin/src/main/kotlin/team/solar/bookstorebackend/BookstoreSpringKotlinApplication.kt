package team.solar.bookstorebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookstoreSpringKotlinApplication

fun main(args: Array<String>) {
    runApplication<BookstoreSpringKotlinApplication>(*args)
}
