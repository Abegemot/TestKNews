import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

import io.ktor.client.features.json.JsonFeature

import com.begemot.knewscommon.*

import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import jdk.nashorn.internal.runtime.JSONFunctions
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parse
import kotlin.system.exitProcess

data class pp(val uni:ListOriginalTransList)


fun main(){

    println("Hello World .... zopa 22w")
    runBlocking {
        getHeadLinesKotilxS("GU","it")
        getFileList()

    }
    println("FIN of everything")
    exitProcess(0)
}

suspend fun getHeadLinesKotilxS(name: String,tlang: String){
    println("get Head Lines Kotinx")
    val s=GetHeadLines("sss","uu")
    println("$s")
    val client= HttpClient(OkHttp){// <----launch in Dispatchers.IO
        engine {
        }
        install(JsonFeature){
            serializer=KotlinxSerializer()
        }
    }
    val x = client.post<String> {
        url("http://knews1939.ey.r.appspot.com/getTransHeadLines")
        contentType(ContentType.Application.Json)
        body=GetHeadLines("GU","it")
    }
    val w=Json(JsonConfiguration.Default  ).parse(ListOriginalTransList.serializer(),x) //   <---- launch in Dispachers.Default
    println("lets see ....")
    println("Message from server: ${x}")
    println("Message from server: ${w.lOT}")
    client.close()
    println("End")

}


suspend fun getFileList(){
    println("get file list ....")
    //List<StoredElement>
    val fileList=KClient().client.post<List<StoredElement>>{
        url("http://knews1939.ey.r.appspot.com/getFileList")
        contentType(ContentType.Application.Json)
    }
    //val r=Json(JsonConfiguration.Stable).parse(ListSerializer(StoredElement.serializer()),x)
    fileList.forEach{
        println(it)
    }


}