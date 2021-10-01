package com.begemot.ktestnews.com.begemot.books

import com.begemot.bookgenerator.IBookGen
import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.KArticle
import com.begemot.newspapers.RougeEtNoire
import mu.KotlinLogging
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.writeLines
private val logger2 = KotlinLogging.logger {}

object RougeEtNoireIg :IBookGen{
    override val book: IBook
        get() = RougeEtNoire
    override val filepath: String
        get() = "src/main/resources/Stendhal/"
    override val outputpath: String
        get() = "$filepath/Stendhal/"
    override val filename: String
       // get() = "Le_Rouge_et_le_Noir_Texte_entier2.txt"
       //get() = "Le_Rouge_et_le_NoirX.txt"
    get()=""

    override val lChapterPositions: List<Pair<Int, Int>>
        get() = listOf(
            Pair(123,139),Pair(147,165),Pair(174,206),Pair(214,232),Pair(238,299),Pair(309,369),
            Pair(376,435),Pair(443,491),Pair(497,559),Pair(567,597),Pair(610,632),Pair(640,672),
            Pair(679,709),Pair(715,743),Pair(752,775),Pair(784,814),Pair(823,855),Pair(864,945),
            Pair(953,1014),Pair(1022,1049),Pair(1056,1154),Pair(1161,1221),Pair(1231,1343),Pair(1353,1412),
            Pair(1421,1503),Pair(1515,1590),Pair(1597,1618),Pair(1622,1663),Pair(1670,1779),Pair(1787,1930),
            Pair(1936,2018),Pair(2025,2073),Pair(2080,2110),Pair(2117,2200),Pair(2207,2220),Pair(2226,2271),
            Pair(2279,2346),Pair(2354,2433),Pair(2442,2510),Pair(2519,2571),Pair(2578,2598),Pair(2606,2642),
            Pair(2649,2714),Pair(2722,2762),Pair(2768,2798),Pair(2804,2860),Pair(2869,2912),Pair(2918,2955),
            Pair(2964,3029),Pair(3035,3080),Pair(3086,3131),Pair(3137,3198),Pair(3205,3281),Pair(3287,3341),
            Pair(3347,3398),Pair(3410,3429),Pair(3435,3455),Pair(3461,3485),Pair(3491,3519),Pair(3527,3567),
            Pair(3574,3621),Pair(3628,3673),Pair(3680,3729),Pair(3735,3776),Pair(3783,3816),Pair(3822,3880),
            Pair(3886,3917),Pair(3923,3968),Pair(3975,4003),Pair(4009,4034),Pair(4040,4084),Pair(4086,4132),
            Pair(4135,4191),Pair(4193,4260),Pair(4262,4313),

            )

    fun transform(){
        //val inPath= Paths.get(path+"origUTF16LE.txt")
        //val l= Files.readAllLines(p,Charsets.UTF_16LE)
        //val z=Files.readAllBytes(p)
        //val e=z.toUByteArray()
        val inPath = Paths.get("${path}SAVED8.txt")
        val srr="Sopa"
        val l= Files.readAllLines(inPath)
        val bA=Files.readAllBytes(inPath)
        val ss=bA.toString(Charsets.UTF_8)//decodeToString()
       // val s= String(Files.readAllBytes(inPath),Charsets.UTF_16LE)
        val sba=ss.toByteArray(Charsets.UTF_8)
        val sfba=sba.toString(charset("UTF-8"))
        val sss=String(sba, Charset.defaultCharset())
        //Files.write(outPath,sba)

    }


    fun getChapTitles(ls:List<String>){
        val lname= mutableListOf<String>()
        val lnum= mutableListOf<String>()
        for(i in 20..98){
            val l="""(.*?)Chap.(.*?)""".toRegex().matchEntire(ls[i])
            lname.add(""+l?.groups?.get(1)?.value)
            lnum.add(""+l?.groups?.get(2)?.value)
            logger2.error { "${l?.groups?.get(1)?.value}${l?.groups?.get(2)?.value}" }
        }
       val lname2=lname.drop(1)
        logger2.error { lname2.toString() }
        logger2.error { lnum.toString() }
        val sb=StringBuilder()
       // KArticle("3","3"),
        for (i in 0..75) {
            val s = "KArticle(\"${lnum[i].trim()} ${lname2[i].trim()}\",\"${i+1}\"),\n"
            sb.append(s)
        }
        logger2.error { "\n${sb.toString()}" }

    }

   /* fun getChapLineNumbers(ls:List<String>){
        val lbeg = mutableListOf<String>()
        val lend = mutableListOf<String>()
        logger2.error {"getChapLineNumbers"}
        ls.forEachIndexed { index, s ->
            if(s.trim().equals("ZXS")) lbeg.add((index).toString())
            if(s.trim().equals("ZXE")) lend.add((index+1).toString())
        }
        val sb=StringBuilder()
        lbeg.forEachIndexed{ index,s->
            val ss="Pair(${lbeg[index]},${lend[index]}),"
            sb.append(ss)
            if(index%6==5) sb.append("\n")
        }
        logger2.error { "\n${sb.toString()}"}
    }*/


    override fun getAllBook(): List<String> {
     //   transform()
        val p= Paths.get("${path}SAVED8.txt")
        val l= Files.readAllLines(p)
       // getChapTitles(l)
        //getChapLineNumbers(l)
        //val z=Files.readAllBytes(p)
        //val e=z.toUByteArray()
        //val outp = Paths.get("$path+resultA.txt")
        //val s= String(Files.readAllBytes(p),Charsets.UTF_16LE)
        //Files.write(outp,s.toByteArray(Charsets.UTF_8))

        //val p = Paths.get("$path+resultA.txt")
        //val l= Files.readAllLines(p,Charsets.UTF_8)
       // return l
        /*val l =
            try {
                String(Files.readAllBytes(Paths.get(path))).split("\n")
            } catch (e: Exception) {
             //   Timber.d("exception $e")
                ""//"error loadFromCache ${e.message}"
                emptyList<String>()
            }*/
        //val p2 = Paths.get("$path+resultA.txt")
        //p2.writeLines(l,Charsets.UTF_8)
        return l
        /*try {
            Files.write(Paths.get("$path+A"),str.toByteArray())
            //Files.write(Paths.get(sf+"/ERROR.BGM"),"ok namefile:$sNameFile  size ${scontent.length}\n".toByteArray(),StandardOpenOption.APPEND,StandardOpenOption.CREATE,StandardOpenOption.WRITE)
        } catch (e: Exception) {
            //Files.write(Paths.get(sf+"/ERROR.BGM"),"err ${e.message}\n".toByteArray(),StandardOpenOption.APPEND,StandardOpenOption.CREATE,StandardOpenOption.WRITE)
            //writeError("storeInCache $sNameFile ${e.message}")
        }*/

        //return emptyList()
        return l
    }


}


suspend fun createRougeEtNoir(){
    //https://fr.wikisource.org/wiki/Le_Rouge_et_le_Noir/Chapitre_VII
    var p = """(?<!M)(?>!»)\. """
    p = """(?<!M)\. """
    //p="""n"""
   //p="""(?<=\. )"""   //ok pero no salva el M.
    p="""(?<!M)(\. )"""  // salva el M. pero es carrega els .
   // p="""(?<!M)(?<=\. )"""


    RougeEtNoireIg.createChapters(true,p)
}