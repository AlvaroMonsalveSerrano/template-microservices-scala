package es.ams.adapter

import es.ams.adapter.BasicServiceAdapter.{BasicService, ServiceError}
import zio._
import es.ams.views.BasicViews._
import es.ams.adapter.basicservice._
import zio.console.{Console, putStrLn}

trait BasicAdapter {
  def getList(): List[BasicResponse]
  def doPost(param1: String, param2: String): List[BasicResponse]
  def doPut(param1: String, param2: String): List[BasicResponse]

}

object BasicAdapter extends BasicAdapter {

  def getList(): List[BasicResponse] = {

    val program: ZIO[Console with BasicService, ServiceError, List[BasicResponse]] = for {
      _    <- putStrLn("[START] GetList...")
      list <- getListEntity()
      _    <- putStrLn("[END] GetList...")
    } yield { list }

    Runtime.default
      .unsafeRun(
        program
          .provideLayer(serviceBasicService)
      )

//    Runtime.default
//      .unsafeRun(
//        getListEntity()
//          .provideLayer(serviceBasicService)
//      )
  }

  def doPost(param1: String, param2: String): List[BasicResponse] = {
    Runtime.default
      .unsafeRun(
        doActionPost(param1, param2)
          .provideLayer(serviceBasicService)
      )
  }

  def doPut(param1: String, param2: String): List[BasicResponse] = {
    Runtime.default
      .unsafeRun(
        doActionPut(param1, param2)
          .provideLayer(serviceBasicService)
      )
  }

}
