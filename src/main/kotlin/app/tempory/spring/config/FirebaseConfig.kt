package app.tempory.spring.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct

@Configuration
class FirebaseConfig {

    @PostConstruct
    fun init() {
        try {
            val stream = ClassPathResource(
                "/serviceAccountKey.json"
            ).inputStream

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build()

            FirebaseApp.initializeApp(options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}