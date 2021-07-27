package app.tempory.spring.fetcher

import app.tempory.graphql.types.Directions
import app.tempory.graphql.types.DirectionsInput
import app.tempory.spring.service.DirectionsService
import app.tempory.spring.service.FirebaseService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.web.bind.annotation.RequestHeader
import reactor.core.publisher.Mono

@DgsComponent
class DirectionsFetcher(
    private val directionsService: DirectionsService,
    private val firebaseService: FirebaseService,
) {

    @DgsQuery
    fun optimizeWaypoints(
        @InputArgument directions: DirectionsInput,
        @RequestHeader("authorization") token: String,
    ): Mono<Directions> {
        return Mono.just(token)
            .flatMap { firebaseService.verifyIdToken(it) }
            .flatMap { verified ->
                if (verified) {
                    createOptimizedDirections(directions)
                } else {
                    Mono.empty()
                }
            }
    }

    private fun createOptimizedDirections(
        directions: DirectionsInput
    ): Mono<Directions> {
        val origin = directions.origin
        val destination = directions.destination
        val waypoints = directions.waypoints.take(10) // avoid higher bills

        val waypointMap = mutableMapOf<Int, String>()
        waypoints.forEachIndexed { i, s -> waypointMap[i] = s }

        return directionsService
            .optimizeWaypoints(origin, destination, waypoints)
            .map {
                val optimizedWaypoints = it.routes[0].waypointOrder
                    .map { i -> waypointMap[i] }
                    .filterNotNull()
                    .toList()

                Directions(origin, destination, optimizedWaypoints)
            }
    }
}