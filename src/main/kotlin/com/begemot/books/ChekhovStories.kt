package com.begemot.ktestnews.com.begemot.books

import com.begemot.bookgenerator.IBookGen
import com.begemot.bookgenerator.createChaptersFromXML
import com.begemot.bookgenerator.createListChaptersFromXML
//import com.begemot.knewsclient.KClient
//import com.begemot.knewsclient.KNews
import com.begemot.knewscommon.IBook
import com.begemot.newspapers.TXS
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import java.io.File
import java.io.FileInputStream




private val logger = KotlinLogging.logger {}
object ChekhovStories : IBookGen {
    override val book: IBook
        get() = TXS
    override val filepath: String
        get() = "src/main/resources/Chehov/"
    override val outputpath: String
        get() = "$filepath/ChehovTales/"
    override val filename: String
        get() = "ChehovTales.xml"
    override val lChapterPositions: List<Pair<Int, Int>>
        get() = emptyList()

    override fun getAllBook(): List<String> {
        return emptyList()
    }

}

suspend fun createTxehovStories(){
    logger.debug { "I'll create Txehov Stories" }
    //val size=KNews().getGoogleFileSize("Books/TxehovStories/TXS2")
    //logger.debug { "And the size is: $size" }
    createListChaptersFromXML("TXS")
    //createChaptersFromXML("TXS")
    return

    val file = File("${ChekhovStories.path}")
    val fis = FileInputStream(file)
    val doc: Document = Jsoup.parse(fis, null, "", Parser.xmlParser())
    logger.debug { doc.toString() }
    for (e in doc.select("PART")) {
        logger.debug { "Q->"+e.wholeText().trimIndent() }
    }
    logger.debug { "end read" }

    val s=StringBuilder()
    s.append("\nlistOf(")
    var n=0
    for (e in doc.select("CHAPTER")) {
       // logger.debug { "is there any chapter?" }

       // logger.debug { "XQ->"+e.attr("title").toString() }
        s.append("\n    KArticle(\"${e.attr("title").toString()}\",\"${n++}\"),")
    }
    s.append("\n)")
    logger.debug { "end read XQ" }
    logger.debug { "s->$s" }


}