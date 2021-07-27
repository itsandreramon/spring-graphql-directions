package app.tempory.spring.service

import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface DirectionsService {
    fun optimizeWaypoints(
        origin: String,
        destination: String,
        waypoints: List<String>,
    ): Mono<DirectionsResult>
}

@Service
class DirectionsServiceImpl(
    private val geoApiContext: GeoApiContext,
) : DirectionsService {

    override fun optimizeWaypoints(
        origin: String,
        destination: String,
        waypoints: List<String>
    ): Mono<DirectionsResult> {
        val request = DirectionsApiRequest(geoApiContext)
            .optimizeWaypoints(true)
            .origin(origin)
            .mode(TravelMode.DRIVING)
            .destination(destination)
            .waypoints(*waypoints.toTypedArray())

        return Mono.create { sink ->
            request.setCallback(object : PendingResult.Callback<DirectionsResult> {
                override fun onResult(result: DirectionsResult) { sink.success(result) }
                override fun onFailure(e: Throwable) { sink.error(e) }
            })
        }
    }
}