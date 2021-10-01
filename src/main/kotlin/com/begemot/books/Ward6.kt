package com.begemot.ktestnews.com.begemot.books

import com.begemot.bookgenerator.IBookGen
import com.begemot.knewscommon.IBook
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Paths
import com.begemot.newspapers.W6

private val logger = KotlinLogging.logger {}

object Ward6Book:IBookGen{
    override val book: IBook
        get() = W6
    override val filepath: String
        get() = "src/main/resources/Chehov/"
    override val outputpath: String
        get() = "$filepath/Chehov/"
    override val filename: String
        get() = "Ward6.all"
    override val lChapterPositions: List<Pair<Int, Int>>
        get() = listOf(Pair(0,60),Pair(59,89),Pair(89,136),Pair(136,170),Pair(170,216),Pair(216,311),Pair(311,349),Pair(349,366),
        Pair(366,470),Pair(470,580),Pair(580,610),Pair(610,669),Pair(669,700),Pair(700,749),Pair(749,794),Pair(794,875),
            Pair(875,923),Pair(923,991),Pair(991,1019)
            )

    override fun getAllBook(): List<String> {
        val p= Paths.get("$path")
        return Files.readAllLines(p)
    }

}

suspend fun createWard6(){

    logger.debug{"Ward 6" }
/*    val nameFile="src/main/resources/Chehov/Ward6.all"
    val doc = Jsoup.connect("https://interlinearbooks.com/literature/russian/reader/palata-6/").get()
    val txt=doc.text()
    logger.debug { txt }
    val l=txt.split(Regex("(?<=\\. )"))
    Files.write(Paths.get(nameFile),l, StandardOpenOption.TRUNCATE_EXISTING)
*/

    //logger.debug { "LAPESTE" }
    //getTransChapter()
    //LapesteBook.createChapters()
    //BulgakovBook.createChapters()
     Ward6Book.createChapters(true)


}