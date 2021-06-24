package com.begemot.bookgenerator
import com.begemot.knewsclient.KNews
import com.begemot.knewscommon.IBook
import mu.KotlinLogging

import com.begemot.knewscommon.print
import com.begemot.knewscommon.printFirstLast
//import com.begemot.ktestnews.logger
import kotlin.io.path.Path
import kotlin.io.path.writeLines

private val logger2 = KotlinLogging.logger {}

interface IBookGen:Iterator<Chapter>{
    val book: IBook
    val filepath:String
    val outputpath:String
    val filename:String
    //val bookname:String
    //val handler:String
    val path:String
        get() = "$filepath$filename"
    val googleName:String
        get() = "Books/${book.directory}/${book.handler}"
    val lChapterPositions:List<Pair<Int,Int>>
    var currChapter:Int
        get() = currChapter
        set(value) {
            currChapter =value}

    fun reset(){ currChapter=1}

    override fun hasNext(): Boolean {
        return (currChapter +1) < lChapterPositions.size+2
    }

    override fun next(): Chapter {
        val cChapter= getOriginalChapter(currChapter)
        currChapter++
        return cChapter
    }


    fun getAllBook():List<String>
    //suspend fun translateChapterAndStore(n: Int, tlang: String):TranslatedChapter
    //fun loadTranslatedChapterFromFile(i: Int, tlang: String):TranslatedChapter
    //fun printForOriginalChapters()

    fun getOriginalChapter(n:Int):Chapter{
        val lSentences= mutableListOf<String>()
        val lTxt= getAllBook() //Files.readAllLines(p, Charset.forName("Windows-1251"))
        val limits= lChapterPositions[n-1]
        lTxt.forEachIndexed { i,it->
            if(it.isNotBlank() && (i>limits.first) && (i<limits.second)) {
                var p="(?<!M)(\\.-?)"
                p="(?<=\\. )"
                p="(?<!M)(?>!»)\\. "

                val j = it.split(Regex(p))
                //logger2.debug { j }
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
        return Chapter(this,"$n",lSentences)

    }

   suspend fun createChapters(){
        for(i in 1..lChapterPositions.size){
            val chapter:Chapter = getOriginalChapter(i)
            logger2.debug { printFirstLast(chapter.lString,"Chapter $i",4) }
            logger2.debug { googleName }
            KNews().storeFile("$googleName$i", chapter.toString() )
        }
    }
}

interface IChapter{
    val book:IBookGen
    val name:String
    //val lString:List<String>
    fun print(name: String,amount:Int):String

}

class Chapter(override val book: IBookGen, override val name: String, val lString: List<String>) :IChapter {
    override fun print(name: String, amount: Int):String {
        return lString.print("${book.book.name}  Chapter ${this.name}", amount)
    }
    //@ExperimentalPathApi
    fun saveAsFile(){
        val nameFile="src/main/resources/Bulgakov/BLK$name"
        val p= Path(nameFile)
        p.writeLines(lString)
    }

    override fun toString(): String {
        return lString.joinToString("\n")
    }

}
