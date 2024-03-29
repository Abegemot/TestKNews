package com.begemot.ktestnews

import com.begemot.bookgenerator.createBook
import com.begemot.bookgenerator.createListChaptersFromXML
import com.begemot.bookgenerator.getIBookFromHandler

import com.begemot.books.LapesteBook
import com.begemot.knewsclient.*
import com.begemot.knewscommon.*
import com.begemot.translib.*
import kotlinx.coroutines.runBlocking


import org.jsoup.Jsoup

import java.util.*
import kotlin.system.measureTimeMillis
import mu.KotlinLogging
import com.begemot.books.BulgakovBook
import com.begemot.books.createLapeste
import com.begemot.ktestnews.com.begemot.books.*
import com.begemot.ktestnews.com.begemot.books.CatalanLessons.createCatalanLessons
import kotlinx.serialization.Serializable
import java.io.File

private val logger = KotlinLogging.logger {}

//@ExperimentalTime
fun main() {
    System.setProperty(kotlinx.coroutines.DEBUG_PROPERTY_NAME,"on")
    HolaTransLib()
    logger.debug { "init main bb" }
    logger.debug { "init main 2" }
try {
    runBlocking {
        //testlink()
        //WebTest()
        //LocalTest()
        //testSrv()
        //testgetNewsPapersWithVersion()
        //testRemoteNewsPapers()
        //testSerializedos()
        //createLapeste()
        //createWard6()
        //testLocalArticle()
        //->deleteServerFiles()
        //->listServerFiles()
        //  testArticle()
        //createDeadSouls1()
        //createBook("")
           //createCatalanLessons()
           //createTxehovStories()
           //createListChaptersFromXML("CNV")

        //createBook("TXS")
        //testSrv33()
        testGoogleDrive()
        //testKRES()
         // createListChaptersFromXML("TXS")
        //createLapeste()
        //createWard6()
        //createRougeEtNoir()
        //->testXgetTranslatedString()
        //testHeadLines("VW")
        //testKArticle()
       // testMP3("Lemonde.png")
       // generateSongs("src/main/resources/CatalaNV/CatalanLessons1.xml","CNV")
       //generateSongs("src/main/resources/RussianSongs/AllSongs.xml","RussSongs1")
       // testArticle("VW",0)
       // testArticle("W6",0)
        //->createBulgakovChapters()
        //->translateBulgakovChapter()
        //LocalTest2()
        //testtoJList()

    }
    logger.debug { "end main" }
    //exitProcess(0)
}catch (e:Exception){
    logger.error {e.message}
    logger.error { e }
    logger.error { getStackExceptionMsg2(e) }
}
}


suspend fun testMP3(sName:String){
      val sName2="nochcbetla"   //"Images/Lemonde.png"
      //val r=KNews().getImage2(sName2)
    val file:File= File("src/lemonde.png")
    val r=KNews().getIMG(sName,file)

   /* if(r is KResult3.Success){
        val t=r.t.found
        val u=r.t.bresult.size
        logger.debug { "$sName2 found $t  size $u" }
    } else
    {
        logger.error { "$sName2 error ${r.msg()}" }
    }*/
}


fun testKArticle(){
    val lA=listOf(KArticle2("Una"), KArticle2("dos"), KArticle2("tres","mylink"))
    val s= toJStr(lA)
    logger.debug { s }

}


suspend fun testa(){
     logger.debug{BulgakovBook.book.googleDir}
     logger.debug{LapesteBook.book.googleDir}

}


suspend fun testXgetTranslatedString(){
    val t="Hola como estas"
    val s= XgetTranslatedString(t,"es","zh")
    logger.debug { "original ${s.original}  translated ${s.translated}" }
}


suspend fun testKRES(){
    logger.debug { "testKRES" }
    val t=KNews().TestKNews()
    logger.debug { "testKNews=$t" }
}


suspend fun testArticle(handler:String,nArticle:Int=0){

    val t=measureTimeMillis {
        val hln=getOriginalHeadLines(handler)

        logger.debug { hln.print("HeadLines") }
        val hl=hln[nArticle]
        logger.debug {"title: ${hl.title}" }
        val a=getOriginalArticle(handler,hl.link)
        //val lp= splitLongText2(a)
        val lSentences = buildListSentences(a,"ca")

        logger.debug { "original article n $nArticle" }
        logger.debug { "${hl.title}" }
        logger.debug { a }

        logger.debug { lSentences.print("Article",100)}

    }

    logger.debug { "time  ($t) ms" }



}



suspend fun createBulgakovChapters(){
    logger.debug{"I'll test bulgakov"}

    try {
        for(i in 1..33) {
            val ls = BulgakovBook.getOriginalChapter(i)
            logger.debug { ls.lString.print("Chapter $i") }
            KNews().storeFile("Books/Bulgakov/BLK$i", ls.toString())
        }


    } catch (e: Exception) {
        logger.debug { e }
    }

    logger.debug { "end I test Bulgakov" }
}


suspend fun translateBulgakovChapter(){
    val ch=BulgakovBook.getOriginalChapter(2)
    logger.debug { ch.lString.print("PonTi Pilatus",20) }

    val ltch=transPayListOfParagraphs(ch.lString,"ru","zh")
    logger.debug { ltch.print("trans ponti pilatus",20) }


}





fun testlink() {
    val lnk =
        "https://www.theguardian.com/society/2020/jul/18/like-putting-out-a-fire-with-a-colander-of-water-my-life-as-an-antisocial-behaviour-officer"
    val str = lnk.split("/").lastOrNull()?.takeLast(10) + "it"
    println(str)
    val str2 = str.substringBeforeLast("it")
    println(str2)

}


suspend fun deleteServerFiles(){
    KNews().deleteFiles()
}


suspend fun WebTest() {
    println("...WebTest")
    // println(KNews().deleteFiles())
    //listFiles()

    val r = KNews().getNewsPapersWithVersion3(1)
    r.res.onSuccess {
        it.newspaper.forEach { it ->
            println("handler : ${it.handler}  name : ${it.name} desc : ${it.desc} language : ${it.olang}   logoname : ${it.logoName}")
        }
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

suspend fun listServerFiles() {
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
   // println(hl. .lhl[0].kArticle.link)
    println("end local article")
}

suspend fun getNewsPapersf() {
    lateinit var r:KResult<NewsPaperVersion>
    val t = measureTimeMillis {
        // r = getNewsPapersIfChangedVersion(0)
        r=KNews().getNewsPapersWithVersion3(0)
    }
    if(r.res.isFailure) {
        println("news papers version : ${r.res.getOrThrow().version}  $t ms")
        println(r)
    }
    //r.newspaper.forEach {
    //    println(it)
    //}
    //println("KNEWS->${KNews().getNewsPapersWithVersion(1).newspaper}")

}


suspend fun testLocalArticle() {
    logger.debug { "testLocalArticle" }
    val name="KP"
    val lA=getOriginalHeadLines(name)
    //logger.debug { lA.print("LM Head Lines") }
    val link = lA[0].link
    logger.debug { link }
    logger.debug { "title : ${lA[0].title }"}
    val oA=getOriginalArticle(name,link)
    val oAS=splitLongText2(oA)
    logger.debug { oAS.print("Original Article",3) }

}


suspend fun testHeadLines(npaper: String) {
    //val npaper="LV"
    //val npaper="KP"
    //val npaper="SZ"
    //val npaper="PCh"
    //val npaper="VW"
    val timer= measureTimeMillis {
       // val ol=getOlang(npaper)
        val lA= getOriginalHeadLines(npaper)
        logger.debug { "$npaper size list Articles: ${lA.size}" }
        logger.debug{ lA.print(npaper) }
        //printNArticle(npaper, "en", 1)
        //val tA=translateHeadLines(lA, getOlang(npaper),"en")
        //logger.debug { tA.print(npaper) }


    }
    logger.debug { "elapsed milis $timer" }
}

suspend fun printNArticle(npaper:String, tlang:String, n:Int=0){
  /*  val l=KNews().getHeadLines(GetHeadLines(npaper,tlang,0))
    if(l is KResult2.Success){
         logger.debug { l.timeInfo()}
         logger.debug { l.t.lhl.print("From Server") }
    }*/

    try {
        val t= measureTimeMillis {
            val lHL = getTranslatedHeadLines(npaper, tlang)
            logger.debug { lHL.print("translated HeadLines of $npaper", 2) }

            //logger.debug("Time Elapsed getTranlsatedHeadLines $t")


            val lnk = lHL[n].kArticle.link
            val nameArticleToStore = "Articles/$npaper${lnk.takeLast(15).replace("/","")}$tlang"
            logger.debug { "namearticle: $nameArticleToStore" }
            val lArt= getTranslatedArticle(npaper,tlang,lnk)
            logger.debug { lArt.print("translated Article of $npaper",amount = 2) }

        }
        //val lArt= getTranslatedArticle(npaper,tlang,lnk)
        //logger.debug { lArt.print("translated Article of $npaper",amount = 2) }
    } catch (e: Exception) {
        logger.warn { e }
    }

}


suspend fun getStaticFile(){
    println("getStaticFile")
    val t1= measureTimeMillis {
       // https://storage.googleapis.com/knews1939.appspot.com/HeadLines/LVzh
        val url = "https://storage.googleapis.com/knews1939.appspot.com/HeadLines/LVzh"
        val d = Jsoup.connect(url).ignoreContentType(true).get().text()
        println(d)
        val thl=THeadLines(0, fromJStr(d))
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
    //println(translatePayString("mariposa","es","zh"))

}


suspend fun LocalTest() {
    logger.debug{"...LocalTest"}
    //testGoogle()
    //localArticle()
    //getNewsPapersf()
    testHeadLines("LV")
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
        //al p = KNews().Test("marimon2")
        //println(p)
    }
    //val j=KNews().getHeadLines("GU","it")
    //println(j)
    //println("result :$p")
}


suspend fun testSrv33() {
    logger.debug { "Begin testSrv33" }
    //KNews().testKNews(getIBookFromHandler("TXS").getGoogleHeadLinesDir())

    val r1=KNews().getNewsPapers()
     logger.debug { "$r1" }



    val r=KNews().getNewsPapersWithVersion3(0)
    //r.res
   //.onSuccess { logger.debug { r.logInfo()  } }
   //.onFailure { logger.error { r.logInfo() } }
   logw(r)

    val r3=KNews().getGoogleFileSize(getIBookFromHandler("TXS").getGoogleHeadLinesDir())
    logw(r3,"Holis")
    r3.res
        .onSuccess { logger.debug { r3.logInfo()  } }
        .onFailure { logger.error { r3.logInfo() } }


    logger.debug { "End testSrv33" }
    //KNews().getGoogleFileSize(getIBookFromHandler("TXS").getGoogleHeadLinesDir())
    //    .onSuccess { logger.debug { "result getGoogle file size $it" } }
    //    .onFailure { logger.error { "failure: $it" } }
}


suspend fun testGoogleDrive()
{
    logger.debug { "I'll test Google Drive" }
    logger.debug { "One of those days...." }
}

//Max 448 522