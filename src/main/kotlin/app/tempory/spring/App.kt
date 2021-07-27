package app.tempory.spring

import com.google.maps.GeoApiContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class App {

    @Value("\${GOOGLE_MAPS_SERVICES_API_KEY}")
    lateinit var apiKey: String

    @Bean
    fun provideGeoApiContext(): GeoApiContext {
        return GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
