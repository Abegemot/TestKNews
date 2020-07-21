import com.begemot.knewscommon.ListOriginalTransList
import com.begemot.knewscommon.NewsPaper
import com.begemot.translib.HolaTransLib
import com.begemot.translib.getTranslatedArticle
import com.begemot.knewsclient.*
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.system.exitProcess


data class pp(val uni:ListOriginalTransList)


fun main(){
    HolaTransLib()
    println("Hello World .... zopa 22w")
    runBlocking {
        WebTest()
        //LocalTest()
    }
    println("FIN of everything")
    exitProcess(0)
}


suspend fun WebTest(){
    println("...WebTest")
    val z=KNews().getStoredFiles().forEach{
        //  println(it.toString())

        val d1 = LocalDateTime.now()
        val d2 = Instant.ofEpochMilli(it.tcreation).atZone(ZoneId.systemDefault()).toLocalDateTime()
        val d3=System.currentTimeMillis()
        val d4=it.tcreation
        val elapsedminutes=(d3-d4)/(1000*60)
        //val d3=LocalDate.f
        //val d2=Date(it.tcreation).toInstant().epochSecond

        println("name ${it.name} size ${it.size} tcreation:${Date(it.tcreation)}  now $d1  elapsed time : ${elapsedminutes} in minutes ")
    }


    val x=KNews().getHeadLines("GU","it")
    println("Message from server getHeadLines: ${x}")
    val lnk="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    val i=KNews().getArticle("GU","it",lnk)
    println("Message from server getArticle: ${i}")

    println("End Web Test")



}

suspend fun LocalTest(){
    println("...LocalTest")
     val l="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
     val ltA= getTranslatedArticle("GU", "it", l)
     //val ltA2= getTranslatedHeadLines("GU","es")
     //println(ltA2.size)
     //println(ltA2)
     println("End local Test")
}



suspend fun getHeadLinesKotilxS(name: String,tlang: String){
    println("get Head Lines Kotinx")
    println("before...post")
    val x=KNews().getHeadLines("jsjs","ss")


}


suspend fun getFileList(){
    println("get file list ....2")
    //val fl=KClient().post<List<StoredElement>>("getFileList")
    val fl=KNews().getNewsPapers()
    println("fl :$fl")
}

suspend fun getNewsPapers(){
     println("getnewspapers")
     val fl=KClient().post<List<NewsPaper>>("getNewsPapers")
    println("fl ->   $fl")
}

