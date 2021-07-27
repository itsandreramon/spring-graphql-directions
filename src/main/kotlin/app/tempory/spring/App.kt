package app.tempory.spring

import com.google.maps.GeoApiContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class App {

    @Bean
    fun provideGeoApiContext(): GeoApiContext {
        return GeoApiContext.Builder()
            .apiKey("YOUR_API_KEY")
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
