package com.begemot.ktestnews

import com.begemot.knewscommon.*
import mu.KotlinLogging
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.writeLines

private val logger = KotlinLogging.logger {}

internal val chapterPositions=listOf(Pair(31,320),Pair(321,850),Pair(851,957),Pair(958,1093),Pair(1094,1308),
    Pair(1309,1525),Pair(1526,1742),Pair(1743,1940),Pair(1941,2141), Pair(2142,2433), Pair(2434,2493),Pair(2494,2816),
    Pair(2817,3230),Pair(3231,3387),Pair(3388,3679), Pair(3680,3853), Pair(3854,4111), Pair(4112,4563), Pair(4564,4854),
    Pair(4855,4948),Pair(4949,5146), Pair(5147,5398), Pair(5399,5630), Pair(5631,6237), Pair(6238,6503), Pair(6504,6999),
    Pair(7000,7238),Pair(7239,7462), Pair(7463,7596), Pair(7597,7866), Pair(7867,7917), Pair(7918,7989), Pair(7988,8500)
)

interface IBook{
    val filepath:String
    val outputpath:String
    val filename:String
    val bookname:String
    val handler:String
    val path:String
    get() = "$filepath$filename"
    val lChapterPositions:List<Pair<Int,Int>>
    fun getOriginalChapter(n:Int):Chapter
    fun getAllBook():List<String>
    fun translateChapterAndStore(n: Int, tlang: String):TranslatedChapter
    fun loadTranslatedChapterFromFile(i: Int, tlang: String):TranslatedChapter
    fun printForOriginalChapters()
}

interface IChapter{
    val book:IBook
    val name:String
    //val lString:List<String>
    fun print(name: String,amount:Int):String

}

class Chapter(override val book: IBook, override val name: String, val lString: List<String>) :IChapter {
    override fun print(sname: String, amount: Int):String {
        return lString.print("${book.bookname}  Chapter $name", amount)
    }
    @ExperimentalPathApi
    fun saveAsFile(){
         val nameFile="src/main/resources/Bulgakov/BLK$name"
         val p= Path(nameFile)
         p.writeLines(lString)
    }

    override fun toString(): String {
        return lString.joinToString("\n")
    }

}

object BulgakovBook:IBook,Iterator<Chapter>{
    override val filepath: String
        get() = "src/main/resources/"
    override val outputpath: String
        get() = "$filepath/Bulgakov/"
    override val filename: String
        get() = "Bul.txt"
    override val bookname: String
        get() = "Master i Margerita"
    override val handler: String
        get() = "BLK"
    override val lChapterPositions: List<Pair<Int, Int>>
        get() = chapterPositions

    private var currChapter=1

    fun reset(){currChapter=1}

    override fun getAllBook(): List<String> {
        val p= Paths.get("$path")
        return Files.readAllLines(p, Charset.forName("Windows-1251"))
    }

    @ExperimentalPathApi
    override fun translateChapterAndStore(n: Int, tlang: String): TranslatedChapter {
        val lOT = mutableListOf<OriginalTrans>()
        val namefile="$handler$n$tlang"
        val pathfilename="${outputpath}$namefile"
        if(Files.exists(Path(namefile))) throw Exception("File Already exists : $pathfilename")

        val ls= getOriginalChapter(n)
        ls.lString.forEachIndexed{ i,it->
            logger.debug { "($i)-> $it" }
            val lA=getFreeTranslatedArticle(it,"ru",tlang)
            lOT.add(lA)
        }
        logger.debug { "end trans chapter" }
        //logger.debug { lArt.print("JJJJJJRONYA") }

        Files.write(Path(pathfilename),lOT.toJSON().str.toByteArray())
        return TranslatedChapter("$namefile",tlang,lOT)
    }

    @ExperimentalPathApi
    override fun loadTranslatedChapterFromFile(i: Int, tlang: String): TranslatedChapter {
        val namefile="$handler$i$tlang"
        val lOT = JListOriginalTrans(String(Files.readAllBytes(Path("${BulgakovBook.outputpath}/$namefile")))).toList()
        return TranslatedChapter(namefile,tlang,lOT)
    }

    override fun printForOriginalChapters() {
        val lS= getAllBook()
        lS.forEachIndexed { i, it ->
            val s= i.toString().padStart(3,' ')
            if(it.contains("Глава"))
                logger.debug { "($s) ${it.length.toString().padStart(4,' ')} '${it}'\n" }
        }
        logger.debug { "end print for chapters" }
    }

    override fun getOriginalChapter(n: Int): Chapter {
        val lSentences= mutableListOf<String>()
        val lTxt= getAllBook() //Files.readAllLines(p, Charset.forName("Windows-1251"))
        val limits= chapterPositions[n-1]
        println("original->($n)")
        lTxt.forEachIndexed { i,it->
            if(it.isNotBlank() && (i>limits.first) && (i<limits.second)) {
                val j = it.split(Regex("(?<=\\. )"))
                val q= mutableListOf<String>()
                var sAux = ""
                j.forEach {
                    sAux += it
                    if(sAux.length>80){ q.add(sAux); sAux="" }
                }
                if(sAux.isNotEmpty()) q.add(sAux)
                lSentences.addAll(q)
            }
        }
        return Chapter(BulgakovBook,"$n",lSentences)
    }

    override fun hasNext(): Boolean {
        return (currChapter+1) < chapterPositions.size+2
    }

    override fun next(): Chapter {
        val cChapter= getOriginalChapter(currChapter)
        currChapter++
        return cChapter
    }

    fun getChapterHeadlines():String{
        val sB=StringBuilder()
        var I=0
        sB.append("\nval lChapters=mutableListOf<KArticle>()\n")
        BulgakovBook.forEach {
            I++
            sB.append("lChapters.add(KArticle(\"($I) ${it.lString[0]}.\",\"$I\"))\n")
            logger.debug { "$I ${it.lString[0]}"}
        }
        sB.append("return lChapters")
        return sB.toString()
    }

}

  class TranslatedChapter(val namefile:String, val tlan:String, private var lOT:List<OriginalTrans>){
    fun print(msg:String,amount:Int=0,start:Int=0):String{
          return "\n$msg\nTranslated Chapter\nName :  $namefile ${lOT.print("",amount,start)}"
    }
}







