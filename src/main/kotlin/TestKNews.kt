import com.begemot.knewsclient.*
import com.begemot.knewscommon.*
import com.begemot.translib.*
import kotlinx.coroutines.runBlocking

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess




fun main(){
    HolaTransLib()
    println("Hello World .... zopa 22w")
    runBlocking {
        //testlink()
        //WebTest()
         LocalTest()
        //LocalTest2()
        //testtoJList()
    }
    println("FIN of everything")
    exitProcess(0)
}


fun testtoJList(){
    println("test jlist")



    val X= mutableListOf<KArticle>(KArticle("article1","link 1"))



   // z.toListKArticle()
    println("end testjlist")

}



fun testlink(){
    val lnk="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    val str=lnk.split("/").lastOrNull()?.takeLast(10)+"it"
    println(str)
    val str2=str.substringBeforeLast("it")
    println(str2)

}


suspend fun WebTest(){
    println("...WebTest")
   // println(KNews().deleteFiles())
   //listFiles()

    var r=KNews().getNewsPapersWithVersion(1 )
    r.newspaper.forEach {
        println("handler : ${it.handler}  name : ${it.name} desc : ${it.desc} language : ${it.olang}  title : ${it.title}  logoname : ${it.logoname}")
    }

    //var nameFile="GU"

   // println("getFile de $nameFile ${KNews().getFileContent(nameFile).sresult}")
    //println("getFile de GU ${KNews().getFileContent("GU").sresult}")

   /* val image=KNews().getImage("Images/rt-logo.png")
    println("get file img ${image.bresult.size}")
    nameFile="GUur-officer"
    val filename="patata.png"
    val myfile= File(filename)
    Files.write(myfile.toPath(),image.bresult)//,StandardOpenOption.TRUNCATE_EXISTING)
*/

    //println("getFile de $nameFile ${KNews().getFileContent(nameFile).sresult}")
   // println(KNews().deleteFiles())
   /* val lka= mutableListOf<KArticle>()
    val p=KNews().getFileContent(nameFile)
    if(p.found){
        println(p)
        //val sq=JListString(p.sresult).toListString()
        val sq=p.sresult.fromJsonToList<String>()

        println("sq   ${sq.size} $sq ")
        println(sq.size)
        var c=1
        sq.forEach {
            println("  $c->$it")
            c++
        }
    }else{
        println("$nameFile not found")
    }*/
    //val x=KNews().getHeadLines("RT","ca")
    // println("getHeadLines RT ca: ${x}")
    // val lnk="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    // val i=KNews().getArticle("GU","it",lnk)
    // println("getArticle: GUlinkit ${i}")



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
    // val l="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    // val ltA= getTranslatedArticle("GU", "it", l)
    //println(ltA)

    //var r= getNewsPapersIfChangedVersion(2)
    //r.newspaper.forEach {
    //    //println("${it.olang}${it.nameFile}${it.logoName}")
    //    println("handler ${it.handler}  name : ${it.name}  desc : ${it.desc} language : ${it.olang}  title : ${it.title}  logoname : ${it.logoname}")
    //}


   /* var ltA2= getOriginalHeadLines("GU")
     //println(ltA2.size)
     println(ltA2)
     println(ltA2.size)
    val ltq= translateHeadLines(ltA2.subList(0,3),"en","es")
    println("translated $ltq")

     ltA2= getOriginalHeadLines("RT")
     println(ltA2)
     println(ltA2.size)

    */

    val ltA2= getOriginalHeadLines("LV")
    println(ltA2)
    println(ltA2.size)
    //val ltq= translateHeadLines(ltA2.subList(0,3),"ru","en")
    //println("translated $ltq")




    /*  val lnk = "https://www.theguardian.com/world/2020/aug/10/lebanese-pm-to-resign-after-more-than-a-third-of-cabinet-quits"
      //val lnk="https://russian.rt.com/inotv/2020-07-25/Hill-specsluzhbi-SSHA-soobshhili-o"
     val originalArticle= getOriginalArticle("GU",lnk)
     //val jsloriginalArticle= toJListString(originalArticle)
    val l= "“Este desastre es el resultado de la corrupción crónica”, dijo Diab, repitiendo: “La red de corrupción es más grande que el estado”. "
    val lm= mutableListOf<String>()
     lm.add(l)
     val ltranslatedArticle= translateListOfParagraphs(originalArticle.subList(0,1),"en","es")
     ltranslatedArticle.forEach {
         println(it.original)
         println(it.translated)
     }*/


     //val re= getOriginalArticle("RT",lnk)
     //println(re)
     //println(re.size)
     //re.forEach {
     //    println("${it.length} $it")
     //}
    //println("End local Test")
}



suspend fun getHeadLinesKotilxS(name: String,tlang: String){
    println("get Head Lines Kotinx")
    println("before...post")
   // val x=KNews().getHeadLines("jsjs","ss")


}


suspend fun getFileList(){
    println("get file list ....2")
    //val fl=KClient().post<List<StoredElement>>("getFileList")
    val fl=KNews().getNewsPapers()
    println("fl :$fl")
}

suspend fun getNewsPapers2(){
     println("getnewspapers")
     val fl=KClient().post<List<NewsPaper>>("getNewsPapers")
    println("fl ->   $fl")
}


suspend fun LocalTest2(){
    println("localtest2")
    //val p=KNews().storeFile("pepep","jose")
    for(I in 0..20) {
        val p = KNews().Test()
        println(p)
    }
    //val j=KNews().getHeadLines("GU","it")
    //println(j)
    //println("result :$p")
}
