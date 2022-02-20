package com.begemot.ktestnews

import com.begemot.knewsclient.KNews
import com.begemot.knewscommon.*
import com.begemot.translib.getNewsPapers
import com.begemot.translib.getNewsPapersIfChangedVersion
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
//import com.begemot.knewsclient.setLoggerx
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
          // testmultipleSync()
          //testHeadLines1()
       } catch (e: Throwable) {
           logger.warn { "OSTIMA" }
           logger.error { e }
       }

    }
    logger.debug { "end test srv " }
}


@ExperimentalTime
suspend fun testmultipleSync(){
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
    val K=(0..max).toList()
    val li= listOf(0,0,0,0,2,0,2,0,1,2,3)
    //val li= listOf(0,2,3,0,2,0,2,0,1,2,3)
    //val li= listOf(4,2,3,4,2,0,2,0,1,2,3)

    val t=measureTime {
     //    K.pmap { testTest("${(0..4).random()}") }
       K.pmap { it->testTest(li[it].toString()) }

    }
    logger.debug{ "Total time async $t  $max units: time per unit ${t/max}" }
}




suspend fun testTest(s:String)= withContext(Dispatchers.IO){
   val t= measureTimeMillis {
        val r2 = KNews().Test(s)
        //val r2 = KNews().getArticle("BLK", s, "ca")

       if( r2 is KResult2.Success)  logger.debug { "Test($s): Ok    -> ${r2.timeInfo()}" }
       if(r2 is KResult2.Error) logger.error     { "Test($s): Error -> ${r2.msg}" }
   }
    //logger.warn { "time elapsed ($t)ms" }
   // if( r is KResult2.Error)    logger.error { "result test1: time ${r.clientTime.milisToMinSecMilis()} ->\n ${r.msg}" }
}



suspend fun testgetNewsPapersWithVersion(){
    logger.debug { "test getNewsPapersWithVersion 1" }

    val k= getNewsPapersIfChangedVersion(3)
    val s=kjson.encodeToString(k)
    //var s=k.toStr()
    logger.debug { s }


    val r =  getNewsPapers()                      //KNews().getNewsPapersWithVersion(0)
    r.forEach {
        logger.debug { ("type : ${it.kind} mutable : ${it.mutable} language : ${it.olang}  handler : ${it.handler}  name : ${it.name} desc : ${it.desc}  logoname : ${it.logoName}")}
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