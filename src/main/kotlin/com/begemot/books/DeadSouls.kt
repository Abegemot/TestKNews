package com.begemot.ktestnews.com.begemot.books

import com.begemot.bookgenerator.IBookGen
import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.KArticle
import com.begemot.newspapers.DeadSoulsI
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Paths
private val logger = KotlinLogging.logger {}

object DeadSoulsIg: IBookGen {
    override val book: IBook
        get() = DeadSoulsI
    override val filepath: String
        get() = "src/main/resources/Gogol/"
    override val outputpath: String
        get() = "$filepath/Gogol/"
    override val filename: String
        get() = "gogoldeadsouls1.txt"
    override val lChapterPositions: List<Pair<Int, Int>>
        get() = listOf(
            Pair(61,82),Pair(85,384),Pair(387,724),Pair(727,1276),Pair(1279,1590),Pair(1593,1808),
            Pair(1811,2020),Pair(2023,2094),Pair(2097,2292),Pair(2295,2372),Pair(2375,2454),
        )

    override fun getAllBook(): List<String> {
        logger.debug { "path = $path" }
        val p= Paths.get("$path")
        val l=Files.readAllLines(p)
        //getChapLineNumbers(l)
        return l
    }

}

suspend fun  createDeadSouls1(){
    var regex= """(?<!г)(?<!\.)(?<=\. )"""   //el group (?<=\. ) fa petar la cosa
    regex= """(?<!\.)(?<=\. )"""
    regex="""(?<!г)(?<!\.)\. """
    regex="""(?<!т.-е)(?<!г)\. """
    regex="""(?<!т.-е)(?<!г)(?<!\.\.)(?<!\.)\. """
    DeadSoulsIg.createChapters(true,regex)
}