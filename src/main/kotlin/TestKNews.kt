import com.begemot.translib.HolaTransLib
import com.begemot.translib.getTranslatedArticle
import com.begemot.knewsclient.*
import com.begemot.knewscommon.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ImplicitReflectionSerializer
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess



@ImplicitReflectionSerializer
fun main(){
    HolaTransLib()
    println("Hello World .... zopa 22w")
    runBlocking {
        //testlink()
        WebTest()
       //LocalTest()
    }
    println("FIN of everything")
    exitProcess(0)
}

fun Long.milisToMinSecMilis():String{
    val stb=StringBuilder()
    val days    = TimeUnit.MILLISECONDS.toDays(this)
    val hours   = TimeUnit.MILLISECONDS.toHours(this)-(24*days)
    val minuts  = TimeUnit.MILLISECONDS.toMinutes(this)-(60*hours)-(24*days*60)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this)-(60*minuts)-(hours*60*60)-(days*24*60*60)
    val milis   = this-(seconds*1000)-(minuts*60*1000)-(hours*60*60*1000)-(days*24*60*60*1000)
    val ln=5
    stb.append('(')
    if(days>0) stb.append("$days d".padEnd(ln,' '))
    if(hours>0) stb.append("$hours h".padEnd(ln,' '))
    if(minuts>0) stb.append("$minuts m".padEnd(ln,' '))
    if(seconds>0) stb.append("$seconds s".padEnd(ln,' '))
    stb.append("$milis ms)".padEnd(ln,' '))
    return stb.toString()
}

fun testlink(){
    val lnk="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    val str=lnk.split("/").lastOrNull()?.takeLast(10)+"it"
    println(str)
    val str2=str.substringBeforeLast("it")
    println(str2)

}

 //(String)->JListString
val l={a:String->JListString(a)}


@ImplicitReflectionSerializer
suspend fun WebTest(){
    println("...WebTest")
   // println(KNews().deleteFiles())
    listFiles()
    var nameFile="GUit"
    println("getFile de $nameFile ${KNews().getFileContent(nameFile).sresult}")
    println("getFile de GU ${KNews().getFileContent("GU").sresult}")
    nameFile="GUur-officer"
    //println("getFile de $nameFile ${KNews().getFileContent(nameFile).sresult}")
   // println(KNews().deleteFiles())
    val lka= mutableListOf<KArticle>()
    val p=KNews().getFileContent(nameFile)
    if(p.found){
        println(p)
        val sq=JListString(p.sresult).toListString()
        println("sq   ${sq.size} $sq ")
      /*  val x=ListToString2<String,JListKArticle>(sq) { b:String->JListKArticle(b)}  //{JListString ("hola")}

        val ss= ListToString3<String>(sq)

        val jj=ListToString1(lka)

        println("xss->$ss")
        println("x<-")*/
        //val sq= Json(JsonConfiguration.Stable).parse(ListSerializer(String.serializer()),p.sresult)
        println(sq.size)
        sq.forEach {
            println(it)
        }
    }else{
        println("$nameFile not found")
    }
    //val x=KNews().getHeadLines("GU","it")
    // println("getHeadLines GU it: ${x}")
     val lnk="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
     val i=KNews().getArticle("GU","it",lnk)
     println("getArticle: GUlinkit ${i}")



    println("End Web Test")

}

suspend fun listFiles(){
    println("listFiles")
    val ct=System.currentTimeMillis()
    KNews().getStoredFiles().forEach{
        val emilis=ct-it.tcreation
        val str="  ${it.name}".padEnd(20,' ')+
                it.size.toString().padStart(6,' ')+" B "+
                "${Date(it.tcreation)}".padEnd(10,' ')+" "+
                "${emilis.milisToMinSecMilis()}".padStart(23,' ')
        println(str)
    }
}


suspend fun LocalTest(){
    println("...LocalTest")
     val l="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
     val ltA= getTranslatedArticle("GU", "it", l)
    println(ltA)
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

