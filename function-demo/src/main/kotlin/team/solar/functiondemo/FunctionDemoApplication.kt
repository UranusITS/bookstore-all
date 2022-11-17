package team.solar.functiondemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FunctionDemoApplication {
    @Bean
    fun hello(): (String) -> String {
        return { "Hello $it" }
    }
    @Bean
    fun sum(): (List<Pair<Int, Double>>) -> Double {
        return { it -> it.sumOf { it.first * it.second } }
    }
}

fun main(args: Array<String>) {
    runApplication<FunctionDemoApplication>(*args)
}
