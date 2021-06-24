package com.begemot.books

import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Paths
import com.begemot.bookgenerator.*
import com.begemot.knewscommon.IBook
import com.begemot.newspapers.LPeste

private val logger = KotlinLogging.logger {}

//private val chapterPositions = listOf(Pair(1,1036),Pair(1037,2658),Pair(2657,2886),Pair(2887,4182),Pair(4182,4827))


object LapesteBook : IBookGen {
    override val lChapterPositions:List<Pair<Int,Int>>
       get() = listOf(Pair(0,1029),Pair(1029,2652),Pair(2651,2880),Pair(2879,4175),Pair(4175,4821))
    override val book: IBook
        get() = LPeste


    override val filepath: String
        get() = "src/main/resources/Camus/"
    override val outputpath: String
        get() = "$filepath/Camus/"
    override val filename: String
        get() = "Lapeste.all"
    //override val bookname: String
    //    get() = "La Peste"
    //override val handler: String
    //    get() = "Lpeste"
    //override val googleName: String
    //    get() = "Books/Camus/Lpeste"
    //override val lChapterPositions: List<Pair<Int, Int>>
    //    get() = lChapterPositions

   // private var currChapter=1

    //override var currChapter: Int


    override fun getAllBook(): List<String> {
        val p= Paths.get("$path")
        return Files.readAllLines(p)//, Charset.forName("Windows-1251"))
    }
}



suspend fun createLapeste(){

/*    logger.debug{"LA peste" }
    val nameFile="src/main/resources/Camus/Lapeste.all"
    val doc = Jsoup.connect("https://www.ebooksgratuits.com/html/camus_la_peste.html").get()
    val txt=doc.text()
    logger.debug { txt }

    val l=txt.split(Regex("(?<=\\. )"))
    val p= Path(nameFile)
    p.writeLines(l)*/

    //Files.write(Path(nameFile),txt.toByteArray(),StandardOpenOption.CREATE)
    logger.debug { "LAPESTE" }
    //getTransChapter()
    LapesteBook.createChapters()
   //BulgakovBook.createChapters()



}