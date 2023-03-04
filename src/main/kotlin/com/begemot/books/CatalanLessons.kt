package com.begemot.ktestnews.com.begemot.books

import com.begemot.bookgenerator.IBookGen
import com.begemot.knewscommon.IBook
import com.begemot.newspapers.CNV
import java.nio.file.Files
import java.nio.file.Paths
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object CatalanLessons : IBookGen {
    override val book: IBook
        get() = CNV
    override val filepath: String
        get() = "src/main/resources/CatalaNV/"
    override val outputpath: String
        get() = "${filepath}/CatalaNV/"
    override val filename: String
        get() ="CatalanLessons1.xml"
    override val lChapterPositions: List<Pair<Int, Int>>
        get() = listOf(Pair(1,8),Pair(9,14),Pair(15,21))

    override fun getAllBook(): List<String> {
        val p = Paths.get("$path")
        //getChapLineNumbers(l)
        return Files.readAllLines(p)

    }

    fun getChapLineNumbers2(){
        val p= Paths.get("$path")
        val l=Files.readAllLines(p)
        getChapLineNumbers(l)
    }

    suspend fun createCatalanLessons(){
        val  regex= """(?<!\.)(?<=\. )"""
        //getChapLineNumbers2()
        createChapters(false,regex)
    }



}