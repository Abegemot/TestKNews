package com.begemot.ktestnews

import com.begemot.knewsclient.KNews
import com.begemot.knewscommon.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
//import com.begemot.knewsclient.setLoggerx
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}



@KtorExperimentalAPI
@ExperimentalTime
fun testSrv(){
    logger.debug { "start test srv2" }
    runBlocking(Dispatchers.IO) {
       try {
           //testTest("I Test")

          // testgetNewsPapersWithVersion()
          testmultipleAsync()
          // testmultiplesync()
          //testHeadLines1()
       } catch (e: Throwable) {
           logger.warn { "OSTIMA" }
           logger.error { e }
       }

    }
    logger.debug { "end test srv " }
}


@ExperimentalTime
suspend fun testmultiplesync(){
    val max=10
    val t=measureTime {
        for (I in 0..max) {
            testTest("$I")
        }
    }
    logger.debug{ "Total time sync: $t  per unit ${t/40}" }
}


@ExperimentalTime
suspend fun testmultipleAsync(){
    val max=10
    val K=(1..max).toList()
    val t=measureTime {
        K.pmap { testTest("${(0..4).random()}") }
       // K.pmap { testTest("1") }

    }
    logger.debug{ "Total time async $t  $max units: time per unit ${t/max}" }
}




suspend fun testTest(s:String)= withContext(Dispatchers.IO){
   val t= measureTimeMillis {
        //val r2 = KNews().Test(s)
        val r2 = KNews().getArticle("BLK", s, "ca")

       if( r2 is KResult2.Success)  logger.debug { "test1($s): -> ${r2.timeInfo()}" }
       if(r2 is KResult2.Error) logger.error { r2.msg }
   }
    //logger.warn { "time elapsed ($t)ms" }
   // if( r is KResult2.Error)    logger.error { "result test1: time ${r.clientTime.milisToMinSecMilis()} ->\n ${r.msg}" }
}


@KtorExperimentalAPI
suspend fun testgetNewsPapersWithVersion(){
    logger.debug { "test getNewsPapersWithVersion 1" }
    val r = KNews().getNewsPapersWithVersion(1)
    r.newspaper.forEach {
        logger.debug { ("handler : ${it.handler}  name : ${it.name} desc : ${it.desc} language : ${it.olang}   logoname : ${it.logoName}")}
    }
    logger.debug { "end test getNewsPapersWithVersion 1" }
}


@KtorExperimentalAPI
suspend fun testHeadLines1(){
    logger.debug { "testHeadlines" }
    val kk=KNews().getHeadLines(GetHeadLines("PCh","en",0))
    //if( kk is KResult2.Success)  logger.debug { "testHeadlines1(): -> '${kk.t}' client time ${kk.clientTime.milisToMinSecMilis()} server time ${kk.serverTime.milisToMinSecMilis()}" }
    //if( kk is KResult2.Error)    logger.error { "result testHeadlines1:  client time ${kk.clientTime.milisToMinSecMilis()}\n${kk.msg}" }

}