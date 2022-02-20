package com.begemot.bookgenerator

import com.begemot.knewsclient.KNews
import com.begemot.knewscommon.IBook
import mu.KotlinLogging

import com.begemot.knewscommon.print
import com.begemot.knewscommon.printFirstLast
//import java.nio.file.Path
//import com.begemot.ktestnews.logger
import kotlin.io.path.Path
import kotlin.io.path.writeLines
import kotlin.math.abs

private val logger2 = KotlinLogging.logger {}

interface IBookGen : Iterator<Chapter> {
    val book: IBook
    val filepath: String
    val outputpath: String
    val filename: String

    val path: String
        get() = "$filepath$filename"
    val googleName: String
        get() = "Books/${book.googleDir}/${book.handler}"
    val lChapterPositions: List<Pair<Int, Int>>
    var currChapter: Int
        get() = currChapter
        set(value) {
            currChapter = value
        }


    fun getChapLineNumbers(ls: List<String>) {
        val lbeg = mutableListOf<String>()
        val lend = mutableListOf<String>()
        logger2.error { "getChapLineNumbers" }
        ls.forEachIndexed { index, s ->
            if (s.trim().equals("ZXS")) lbeg.add((index).toString())
            if (s.trim().equals("ZXE")) lend.add((index + 1).toString())
        }
        val sb = StringBuilder()
        lbeg.forEachIndexed { index, s ->
            val ss = "Pair(${lbeg[index]},${lend[index]}),"
            sb.append(ss)
            if (index % 6 == 5) sb.append("\n")
        }
        logger2.error { "\n${sb.toString()}" }
    }


    fun reset() {
        currChapter = 1
    }


    override fun hasNext(): Boolean {
        return (currChapter + 1) < lChapterPositions.size + 2
    }

    override fun next(): Chapter {
        val cChapter = getOriginalChapter(currChapter)
        currChapter++
        return cChapter
    }


    fun getAllBook(): List<String>
    //suspend fun translateChapterAndStore(n: Int, tlang: String):TranslatedChapter
    //fun loadTranslatedChapterFromFile(i: Int, tlang: String):TranslatedChapter
    //fun printForOriginalChapters()

    fun splitbycommas3(sAux: String): List<String> {
        val imidle = sAux.length / 2
        val rexp = """(?<=, )"""
        val lComas = sAux.split(rexp.toRegex())
        val lSize = mutableListOf<Pair<Int, Int>>()
        lComas.forEachIndexed { index, s ->
            if (index == 0) lSize.add(Pair(s.length, index))
            else lSize.add(Pair(s.length + lSize[index - 1].first, index))
        }
        val limit = 1 + (lSize.minByOrNull { abs(imidle - it.first) }?.second ?: 0)
        val lTotal =
            listOf(lComas.subList(0, limit).joinToString(""), lComas.subList(limit, lComas.size).joinToString(""))
        logger2.error { "\nORIGINAL \n$sAux" }
        logger2.error { lTotal.print("RESULT SPLITING") }
        return lTotal
    }

    fun splitbycommas(sAux: String): List<String> {
        val ilimit = 170
        val rexp = """(?<=, )"""
        val lComas = sAux.split(rexp.toRegex())


        val lNew = mutableListOf<String>()
        var sAux2 = ""
        lComas.forEachIndexed { index, s ->
            sAux2 += s
            if (sAux2.length >= ilimit) {
                lNew.add(sAux2); sAux2 = ""
            }
            if (index == lComas.size - 1) {
                if (sAux2.length > 0) lNew.add(sAux2)
            }
        }

        //val limit=1+(lSize.minByOrNull { abs(imidle-it.first)  }?.second ?: 0)
        //val lTotal=listOf(lComas.subList(0,limit).joinToString(""),lComas.subList(limit,lComas.size).joinToString(""))
        //logger2.error {"\nORIGINAL  ${sAux.length}\n$sAux"}
        //logger2.error { lNew.print("RESULT SPLITING ${lComas.size}",0) }
        return lNew
    }


    fun getOriginalChapter(n: Int, regex: String = ""): Chapter {
        val lSentences = mutableListOf<String>()
        val lTxt = getAllBook() //Files.readAllLines(p, Charset.forName("Windows-1251"))
        val limits = lChapterPositions[n - 1]

        lTxt.forEachIndexed { i, it ->
            if (it.isNotBlank() && (i >= limits.first) && (i < limits.second)) {
                val rexp = if (regex.length != 0) regex else """(?<!г)(?<=\. )"""
                if (it.length < 250) {
                    lSentences.add(it)
                } else {
                    val t = (it + " ").split(rexp.toRegex())
                    val j = t.filter { it.isNotEmpty() }.map { it -> it.trim() + ". " }
                    j.forEach {
                        if (it.length < 300) lSentences.add(it)
                        else {
                            val q = it.replace("; ", "; #").split("#")
                            //val q=it.split(Regex("\\. "))
                            q.forEach {
                                if (it.length < 250) lSentences.add(it)
                                else lSentences.addAll(splitbycommas(it))
                            }
                            //lSentences.addAll(q)
                        }
                    }


                }

            }
        }
        lSentences.removeFirst()
        lSentences.removeLast()
        return Chapter(this, "$n", lSentences)

    }

    suspend fun createChapters(test: Boolean = true, regex: String = "") {
        logger2.debug { "createChapters test=$test" }
         for(i in 1..lChapterPositions.size){
       // val u = 2
       // for (i in u..u) {
            val chapter: Chapter = getOriginalChapter(i, regex)
            //logger2.debug { printFirstLast(chapter.lString,"Chapter $i",4) }
            logger2.debug { chapter.lString.print("chapter  $i", chapter.lString.size, false) }
            logger2.debug { googleName }
            if (!test) KNews().storeFile("$googleName$i", chapter.toString())
        }
    }
}

interface IChapter {
    val book: IBookGen
    val name: String

    //val lString:List<String>
    fun print(name: String, amount: Int): String

}

class Chapter(override val book: IBookGen, override val name: String, val lString: List<String>) : IChapter {
    override fun print(name: String, amount: Int): String {
        return lString.print("${book.book.name}  Chapter ${this.name}", amount)
    }

    //@ExperimentalPathApi
    fun saveAsFile() {
        val nameFile = "src/main/resources/Bulgakov/BLK$name"
        val p = Path(nameFile)
        p.writeLines(lString)
    }

    override fun toString(): String {
        return lString.joinToString("\n")
    }

}
