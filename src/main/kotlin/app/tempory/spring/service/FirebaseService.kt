package app.tempory.spring.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface FirebaseService {
    fun verifyIdToken(token: String): Mono<Boolean>
}

@Service
class FirebaseServiceImpl : FirebaseService {

    override fun verifyIdToken(token: String): Mono<Boolean> {
        return try {
            FirebaseAuth.getInstance().verifyIdToken(token)
            Mono.just(true)
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            Mono.just(false)
        }
    }
}