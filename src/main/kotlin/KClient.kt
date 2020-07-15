import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

class KClient{
    val client= HttpClient(OkHttp){// <----launch in Dispatchers.IO
        engine {
        }
        install(JsonFeature){
            serializer= KotlinxSerializer()
        }
    }



}