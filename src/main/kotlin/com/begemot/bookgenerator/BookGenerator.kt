package com.begemot.bookgenerator

import com.begemot.knewsclient.KNews
import com.begemot.knewscommon.*
import com.begemot.ktestnews.com.begemot.books.CatalanLessons
import mu.KotlinLogging

import com.begemot.ktestnews.com.begemot.books.ChekhovStories
import com.begemot.newspapers.CNV
import com.begemot.newspapers.TXS
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import java.io.File
import java.io.FileInputStream
import java.util.ArrayDeque
import kotlin.Exception
//import java.nio.file.Path
//import com.begemot.ktestnews.logger
import kotlin.io.path.Path
import kotlin.io.path.writeLines
import kotlin.math.abs

private val logger = KotlinLogging.logger {}

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
        logger.error { "getChapLineNumbers" }
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
        logger.error { "\n${sb.toString()}" }
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
        logger.error { "\nORIGINAL \n$sAux" }
        logger.error { lTotal.print("RESULT SPLITING") }
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
                    val t = ("$it ").split(rexp.toRegex())
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
        logger.debug { "createChapters test=$test" }
         for(i in 1..lChapterPositions.size){
       // val u = 2
       // for (i in u..u) {
            val chapter: Chapter = getOriginalChapter(i, regex)
            //logger2.debug { printFirstLast(chapter.lString,"Chapter $i",4) }
            logger.debug { chapter.lString.print("chapter  $i", chapter.lString.size, false) }
            logger.debug { googleName }
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


suspend fun createBook(handler: String){
    logger.debug { "creating book of handler = $handler" }
    runCatching {
        createListChaptersFromXML(handler)
        createChaptersFromXML(handler)
    }.onFailure {
        logger.error { "OPS!\n Stack = ${getStackExceptionMsg(it)} end OPS!" }
    }
}



fun getIBookFromHandler(handler: String):IBook{
    return when(handler){
         "TXS"-> TXS
         "CNV"-> CNV
         else -> throw Exception("No IBook named $handler")
    }
}

fun getIBookGenFromIBookName(handler: String):IBookGen{
    return when(handler){
        "CNV"-> CatalanLessons
        "TXS"-> ChekhovStories
        else -> throw Exception("No IBookGen for IBook with handler = $handler")
    }
}


fun getLocalXMLDoc(handler:String):Document{
    val bookgen= getIBookGenFromIBookName(handler)
    val xmlfile= File(bookgen.path)
    val xmlifile=FileInputStream(xmlfile)
    return  Jsoup.parse(xmlifile, null, "", Parser.xmlParser())
}



fun getGetHeadLinesFromDoc(doc:Document):String{
    val lHeadLines=doc.select("CHAPTER")
        .mapIndexed { index, element ->  KArticle(element.attr("title"),"${index+1}")}
    if(lHeadLines.isEmpty()) throw Exception("No chapters found!")
    return toJStr(lHeadLines)
}

suspend fun createListChaptersFromXML(handler:String ){
    val ibookgen = getIBookGenFromIBookName(handler)
    val sgooglebookname= getIBookFromHandler(handler).getGoogleHeadLinesDir()
    logger.debug { "create list chapters from ibook = $handler LOCAL FILE ${ibookgen.path} GOOGLE FILE $sgooglebookname" }
    val jLocalHeadlines= getGetHeadLinesFromDoc(getLocalXMLDoc(handler))
    storeIfDifferentSize(jLocalHeadlines,sgooglebookname)
}
suspend fun createChaptersFromXML(ibookhandler: String){
    val doc= getLocalXMLDoc(ibookhandler)
    val ibookgen = getIBookGenFromIBookName(ibookhandler)
    logger.debug { "create chapters from ibook = $ibookhandler LOCAL FILE ${ibookgen.path} GOOGLE FILE: ${ibookgen.googleName}12..3" }
    doc.select("CHAPTER").forEachIndexed { index, element ->
        val slicedText=sliceString(element.text())
        storeIfDifferentSize(slicedText,"${ibookgen.googleName}${index.inc()}")
        displayChapter(slicedText)
    }
}


suspend fun storeIfDifferentSize(stostore:String, sgoglefile:String){
    logger.debug { "storeIfDifferentSize I'm going to store if sizes are different" }
    if (stostore.toByteArray().size.toLong() == KNews().getGoogleFileSize(sgoglefile).res.getOrDefault(0L)) {
            logger.debug { "NO FILE SIZE CHANGES AT $sgoglefile" }
            return
        }
    logger.debug { "FILE SIZE CHANGED STORING CHANGES AT $sgoglefile" }
    KNews().sToreFileAndDeleteSiblings(sgoglefile, stostore)
}

fun displayChapter(sB:String){
    logger.debug { "BEGIN DISPLAY CHAPTER   ----------------------------" }
    val ls=sB.toString().split("\n")
    for(s in ls){
        logger.debug { "${s.length} $s" }
    }
    logger.debug { "END DISPLAY CHAPTER   ----------------------------" }
}


fun sliceLongSentence(s:String):String{
    //val rexp =  """, """
    val lSlicedSentences = mutableListOf<String>()
    val inputStack=ArrayDeque<String>()
    inputStack.addAll(s.split(""", """.toRegex()))

    var ls = ""
    while(inputStack.isNotEmpty()){
        val subs=inputStack.pop()
        ls+="$subs, "
        if(ls.length>150 || inputStack.isEmpty()) {
            if(inputStack.isEmpty()) {
                ls=ls.substring(0,ls.length-2)+"."
            }
            lSlicedSentences.add(ls)
            ls=""
        }
    }
 //   logger.debug { "ori ${s.length} ->$s." }
    for(sliced in lSlicedSentences) {
 //       logger.debug { "sli ${sliced.length} $sliced" }
    }
    val sq=lSlicedSentences.joinToString("\n")
 //   logger.debug { "js=$sq" }
    return sq
}



fun sliceString(istring:String,regex: String=""):String{
    val z="""(?<!M)(?>!»)\. """
    //т. д
    //"""(?<!т. д)(?! д)\. """    (?<!T)patata no precedit per T   patata(?!@) No seguit de@
    var ee="""\. (?! д)"""
    ee="""(?<!т)\. """
    ee="""(?<!т)\. (?! д)"""
    if(istring.isEmpty()) return ""
//    logger.debug { "istring->$istring<-" }
    istring.last()
    val rexp = regex.ifEmpty { ee }
    val lSentences=istring.split(rexp.toRegex())
    val lS= mutableListOf<String>()
    for ((i,s) in lSentences.withIndex()){
         if(s.length<250) {
             //logger.debug { "${s.length}->$s." }
             lS.add("$s.")
         }
         else lS.add(sliceLongSentence(s))
    }
    val v=lS.joinToString("\n")

  //  logger.debug { "->$v<-" }
    return v.dropLast(1) //lS.joinToString("\n")
    //return "patata"
}

//371 416 340 361