package com.begemot.ktestnews

import com.begemot.knewsclient.*
import com.begemot.knewscommon.*
import com.begemot.translib.*
import io.ktor.util.*


import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import java.lang.StringBuilder

import java.util.*
import kotlin.system.measureTimeMillis
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() {
    HolaTransLib()
    logger.debug { "init main\ntamparampan" }
    runBlocking {
        //testlink()
        //WebTest()
        LocalTest()
        //LocalTest2()
        //testtoJList()
    }
    logger.debug { "fin" }
    //exitProcess(0)
}


fun testlink() {
    val lnk =
        "https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    val str = lnk.split("/").lastOrNull()?.takeLast(10) + "it"
    println(str)
    val str2 = str.substringBeforeLast("it")
    println(str2)

}


suspend fun WebTest() {
    println("...WebTest")
    // println(KNews().deleteFiles())
    //listFiles()

    var r = KNews().getNewsPapersWithVersion(1)
    r.newspaper.forEach {
        println("handler : ${it.handler}  name : ${it.name} desc : ${it.desc} language : ${it.olang}   logoname : ${it.logoName}")
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

suspend fun listFiles() {
    println("listFiles")
    val ct = System.currentTimeMillis()
    KNews().getStoredFiles().forEach {
        val emilis = ct - it.tcreation
        val str = "  ${it.name}".padEnd(20, ' ') +
                it.size.toString().padStart(6, ' ') + " B " +
                "${Date(it.tcreation)}".padEnd(10, ' ') + " " +
                "${emilis.milisToMinSecMilis()}".padStart(23, ' ')
        println(str)
    }
}


suspend fun localArticle() {
    val g = GetHeadLines("RT", "zh", 0)
    val hl = KNews().getHeadLines(g)
    println(hl.lhl[0].kArticle.link)
    println("end local article")
}

suspend fun getNewsPapersf() {
    lateinit var r:NewsPaperVersion
    val t = measureTimeMillis {
        // r = getNewsPapersIfChangedVersion(0)
        r=KNews().getNewsPapersWithVersion(0)
    }
    println("news papers version : ${r.version}  $t ms")
    println(r)
    //r.newspaper.forEach {
    //    println(it)
    //}
    //println("KNEWS->${KNews().getNewsPapersWithVersion(1).newspaper}")

}


suspend fun getTranslatedArt() {
    println("getheadlines")

    val link = getOriginalHeadLines("RT")[0].link
    println(link)
    println("getTranslatedArticle")

    lateinit var art1: List<OriginalTrans>
    val t1 = measureTimeMillis {
        art1 = getTranslatedArticle("RT", "ddzh", link)
        //val art = ltA[0]
    }
    println("after try catch   $t1 ms  list original trans ${art1.size}")
   // println("art as it is :\n$art1\nend art as it is")

    lateinit var art2: List<OriginalTrans>
    val t2 = measureTimeMillis {
        try {
            art2 = KNews().getArticle("RT", link, "ddzh")  //why does not fail if tlang is crapy?

        } catch (e: Throwable) {
            println("GOT IT!!!!  ${e.message}")
        }
    }
    //println("art->$art2")
    println("after try catch   $t2 ms list original trans ${art2.size}")




}


@KtorExperimentalAPI
suspend fun testHeadLines() {


    val npaper="LV"
    //val npaper="PCh"
    val lHL= getOriginalHeadLines(npaper)
    logger.debug { lHL.print("getOriginalHeadLines",2) }

    //val lnk="http://politics.people.com.cn/n1/2021/0102/c1001-31986697.html"
    val lnk=lHL[0].link
    //val lart=getOriginalArticle(npaper,lnk)
    val lart= getTranslatedArticle(npaper,"zh",lnk)
    logger.debug{lart.print("OriginalArticle",2)}

}

suspend fun getStaticFile(){
    println("getStaticFile")
    val t1= measureTimeMillis {
       // https://storage.googleapis.com/knews1939.appspot.com/HeadLines/LVzh
        val url = "https://storage.googleapis.com/knews1939.appspot.com/HeadLines/LVzh"
        val d = Jsoup.connect(url).ignoreContentType(true).get().text()
        println(d)
        val thl=THeadLines(0, JListOriginalTransLink(d).toList())
        println(thl)
    }
    println("$t1 ms" )
}


fun testGoogle(){
   /* val url="https://translate.google.com/?sl=de&tl=es&text=Angesichts&op=translate"

             val ant = Jsoup.connect(url)
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
                 .followRedirects(true)
            .get()
    println(ant.html())*/
    println(translatePayString("mariposa","es","zh"))

}


suspend fun LocalTest() {
    logger.debug("...LocalTest")
    //testGoogle()
    //localArticle()
    //getNewsPapersf()
    testHeadLines()
    //getStaticFile()
    //getTranslatedArt()
/*


   var r= getNewsPapersIfChangedVersion(0)
    println("news papers version : ${r.version}")
    r.newspaper.forEach {
        println("handler ${it.handler}  name : ${it.name}  desc : ${it.desc} language : ${it.olang}  logoname : ${it.logoName}")
    }
*/
    // val g=GetHeadLines("LV","zh",0)
    // val r=KNews().getHeadLines(g)
    // val thl=addPinying(r.lhl)
    //  println(thl )

    /* try {
         val ltA2= getOriginalHeadLines("LV")
         val ltq= translategcHeadLines(ltA2.subList(0,3),"es","zh")
         println(ltq)
     } catch (e: Exception) {
         println("Exception ${getStackExceptionMsg(e)}")
     }*/
    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

    // val l="https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    // val ltA= getTranslatedArticle("GU", "it", l)
    //println(ltA)


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

    /* val ltA2= getOriginalHeadLines("LV")
     println(ltA2)
     println(ltA2.size)
     val ltq= translateHeadLines(ltA2.subList(0,3),"es","zh")
     println("translated $ltq")
     */


    /* val ls=listOf("Buenos dias profesora.","esta lloviendo")
     val q= translateListOfParagraphs(ls,"es","zh")

     println(q)

     val www=q.map{it->String(it.translated.commonAsUtf8ToByteArray(), Charset.defaultCharset())}
     println(www)

     val w=q.map{it->it.translated }
     val ZZ= listOf<KArticle>(KArticle(w[0]),KArticle(w[1]))
     val ZX= translateHeadLines(ZZ,"zh","ur")
     println(ZX)



     val zq= translateListOfParagraphs(w,"zh","ca")

     println(zq)
 */


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



suspend fun getFileList() {
    println("get file list ....2")
    //val fl=KClient().post<List<StoredElement>>("getFileList")
    val fl = KNews().getNewsPapers()
    println("fl :$fl")
}



suspend fun LocalTest2() {
    println("localtest2")
    //val p=KNews().storeFile("pepep","jose")
    for (I in 0..20) {
        val p = KNews().Test()
        println(p)
    }
    //val j=KNews().getHeadLines("GU","it")
    //println(j)
    //println("result :$p")
}
