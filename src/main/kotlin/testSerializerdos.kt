package com.begemot.ktestnews

import com.begemot.knewscommon.*
import com.begemot.newspapers.GU
import com.begemot.newspapers.SZ
import com.begemot.translib.getNewsPapers
import com.begemot.translib.getNewsPapersIfChangedVersion
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

interface XIBaseNewsPaper {
    val kind: KindOfNews
    val olang: String
    val name: String
    val desc: String
    val logoName: String
    val handler: String
    val url:String
    fun getOriginalHeadLines(): List<KArticle> { return emptyList<KArticle>()  }
    fun getOriginalArticle(link: String): String { return "" }
}

interface XINewsPaper : XIBaseNewsPaper {
    override val kind: KindOfNews
        get()=KindOfNews.NEWS
}

interface XIBook: XIBaseNewsPaper {
    val directory:String
    override val kind: KindOfNews
        get()=KindOfNews.BOOK
}

@Serializable
object A:XINewsPaper{
    override val olang: String
        get() = "de"
    override val name: String
        get() = "Süddeutsche Zeitung"
    override val desc: String
        get() ="German Newspaper"
    override val logoName: String
        get() = "sz-plus-logo.png"
    //get() = "La_Vanguardia.png"
    override val handler: String
        get() = "SZ"
    override val url: String
        get() = "https://www.sueddeutsche.de"
}

@Serializable
object B:XIBook{
    override val directory: String
        get() = "Bulgakov"
    override val olang: String
        get() = "ru"
    override val name: String
        get() = "Master i Margherita"
    override val desc: String
        get() = "Russian Book"
    override val logoName: String
        get() = "master2.png"
    override val handler: String
        get() = "BLK"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/The_Master_and_Margarita"

}

@Serializable
class NPX(val version:Int=0, val newspapers:List<XIBaseNewsPaper>)

fun NPX.toJSTR():String = kjson.encodeToString<NPX>(this)
fun fromStrToNPX(str:String): NPX = kjson.decodeFromString(str)


val modulex= SerializersModule {
    polymorphic(IBaseNewsPaper::class) {
        subclass(SZ::class, SZ.serializer())
        subclass(GU::class,GU.serializer())
    }

}


/*val modulex= SerializersModule {
    polymorphic(XIBaseNewsPaper::class){
        subclass(A::class,A.serializer())
        subclass(B::class,B.serializer())
    }
}*/
val kjson2 = Json {  serializersModule = modulex }


fun testSerializedos(){
    /*val npv=NPX(5, listOf(B,A))

    logger.debug { npv.newspapers[0].desc }
    logger.debug { "I test serialize  $npv" }
    logger.debug { kjson2.encodeToString(npv) }//npv.toJSTR() }

    val x=kjson2.encodeToString(npv)
    val y= kjson2.decodeFromString<NPX>(x)
    logger.debug { "encoded npv:= $npv" }
    logger.debug { "Y          := ${ y.newspapers[0].kind }" }*/
    val u=getNewsPapers()

    logger.debug { "getNewsPapers  $u" }

    val v= getNewsPapersIfChangedVersion(0)
    val s= kjson2.encodeToString(v) //    v.toStr()

    logger.debug { "getNewsPapersIfChanged  $v" }



}