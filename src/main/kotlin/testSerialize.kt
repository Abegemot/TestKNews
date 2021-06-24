package com.begemot.ktestnews

import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.kjson
import kotlinx.serialization.Serializable
import mu.KotlinLogging
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val logger = KotlinLogging.logger {}

enum class KindOfSubject{
    NEWS,BOOK
}

@Serializable
sealed  class Container(){
   // abstract val kind:KindOfSubject
    abstract val name:String
    abstract val olang:String
    abstract val desc: String
    abstract val logoName: String
    abstract val handler: String
    abstract val url:String
   // fun getOriginalHeadLines(): List<KArticle> { return emptyList<KArticle>()  }
   // fun getOriginalArticle(link: String): String { return "" }
    abstract  val fuck: ()->List<KArticle> //= { emptyList<KArticle>()}
}

@Serializable
open class NewsA(
    //override val kind:KindOfSubject = KindOfSubject.NEWS,
    override val name: String,
    override val olang: String,
    override val desc: String,
    override val logoName: String,
    override val handler: String,
    override val url:String,
    override val fuck: () -> List<KArticle>
):Container()



@Serializable
open class BooksA(
    //override val kind:KindOfSubject = KindOfSubject.BOOK,
    val directory:String,
    override val name: String,
    override val olang: String,
    override val desc: String,
    override val logoName: String,
    override val handler: String,
    override val url:String,
    override val fuck: () -> List<KArticle>
):Container()


/*@Serializable
object P:BooksA(directory = "ZZZBulgakov",name = "bulss",olang = "ru",
    desc = "desc",logoName = "dddd",handler = "handler",
    url = "http"

)*/

@Serializable
class NPV(val version:Int=0, val newspapers:List<Container>)

@Serializable
data class NP(val i:Int)

fun NPV.toJSTR():String = kjson.encodeToString<NPV>(this)
fun fromStrToNPV(str:String): NPV = kjson.decodeFromString(str)

fun testSerialize(){

    val a=BooksA(directory = "Bulgakov",name = "bulss",olang = "ru",
                desc = "desc",logoName = "dddd",handler = "handler",
            url = "http",fuck = ::getYY

        )
    val b=NewsA(
        name = "bulss",olang = "ru",
        desc = "desc",logoName = "dddd",handler = "handler",
        url = "http",fuck = ::getYY

    )

    val npv=NPV(5, listOf(a,b,a))

    logger.debug { "I test serialize" }
    logger.debug { npv.toJSTR() }

    val s = npv.toJSTR()
    val q = fromStrToNPV(s)

    logger.debug { q }


}


fun getYY():List<KArticle>{return emptyList()}