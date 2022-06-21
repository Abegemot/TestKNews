package com.begemot.ktestnews

import com.begemot.knewsclient.KNews
import com.begemot.knewscommon.*
import com.begemot.translib.getNewsPaperByKey
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File


private val logger = KotlinLogging.logger {}


fun deleteArticles(sHandler:String){
    logger.debug { "deleteArticles of $sHandler" }
    val nP= getNewsPaperByKey(sHandler)
    logger.debug { nP.getGoogleArticlesDir()}

}


suspend fun generateSongs(sNameFile:String, sHandler:String){
    logger.debug { "Hola I'll generate Songs: $sNameFile" }

    val nP= getNewsPaperByKey(sHandler)

    val TAG = when(nP.kind){
        KindOfNews.NEWS  ->  ""
        KindOfNews.SONGS ->  "SONG"
        KindOfNews.BOOK  ->  "CHAPTER"
    }
    if(TAG.isEmpty()) return

    val doc: Document = Jsoup.parse(File(sNameFile), "utf-8")
    val songs=doc.select(TAG)
    logger.debug { "TAG=$TAG" }

    val sListArticles=toJStr(songs.mapIndexed{ i, song -> KArticle(song.attr("title"),"${i+1}",song.attr("l"))})
    logger.debug { "NAMEFILE=${nP.getGoogleHeadLinesDir()}" }

    val sOriginalHeadLines=KNews().getOriginalHeadLines(GetHeadLines(sHandler,"",0))
    if(sOriginalHeadLines is KResult3.Success){
       if(sOriginalHeadLines.t==sListArticles){
           logger.debug { "NO CHANGED HEADLINES" }
       }
        else
           logger.debug { "CHANGED HEADLINES !!" }
           KNews().storeFile(nP.getGoogleHeadLinesDir(),sListArticles)
           KNews().deleteHeadLines(nP.handler)
    }

    logger.debug { "result: $sListArticles" }

    songs.forEachIndexed { index, song ->
        //  val nameFile="${nP.getGoogleArticlesDir()}${nP.handler}${index+1}"
        val nameFile="${nP.getGoogleArticlesDir()}${index+1}"
        logger.debug { "name=$nameFile" }
        val txt= trimSentence(song.wholeText())
        //logger.debug { txt }
         val originalArticle=KNews().getOriginalArticle(sHandler,(index+1).toString(),"")
         if(originalArticle is KResult3.Success){
              val oArticle=originalArticle.t
             if(oArticle==txt){
                 logger.debug { "original=new " }
             } else {
                 logger.debug { "ORIGINAL != NEW" }
                 logger.debug { "original = $oArticle" }
                 logger.debug { "new   = $txt" }
                 KNews().deleteArticle(listOf(sHandler,(index+1).toString()))
                 KNews().storeFile(nameFile,txt)
             }
         }
    }
    logger.debug { songs.size }
}


fun trimSentence(text:String):String{
    val rexp="""(?<!т.-е)(?<!г)(?<!\.\.)(?<!\.)\. """
    val txt2=text.replace("\r\n"," ").trimStart()
    val ls=txt2.split(rexp.toRegex())
    //logger.debug { ls }
    val sB=StringBuilder()

    ls.forEach{it->
        if(it.isNotEmpty()) {
            sB.append(it)
            sB.append(".\n")
        }
    }
    return sB.toString()
}