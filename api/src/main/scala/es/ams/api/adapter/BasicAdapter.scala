package es.ams.api.adapter

import es.ams.services.adapter.BasicServiceAdapter.BasicService
import es.ams.services.adapter.basicservice.{doActionPost, doActionPut, getListEntity, serviceBasicService}
import zio._
import es.ams.services.views.BasicViews._
import zio.console.{Console, putStrLn}

trait BasicAdapter {
  def getList(): List[BasicResponse]
  def doPost(param1: String, param2: String): Int
  def doPut(param1: String, param2: String): List[BasicResponse]

}

object BasicAdapter extends BasicAdapter {

  def getList(): List[BasicResponse] = {

    val program: ZIO[Console with BasicService, Throwable, List[BasicResponse]] = for {
      _    <- putStrLn("[START] GetList...")
      list <- getListEntity()
      _    <- putStrLn("[END] GetList...")
    } yield { list }

    Runtime.default
      .unsafeRun(
        program
          .provideLayer(serviceBasicService)
      )

  }

  def doPost(param1: String, param2: String): Int = {
    Runtime.default
      .unsafeRun(
        doActionPost(BasicRequest(name = param1, value = param2))
          .provideLayer(serviceBasicService)
      )
  }

  def doPut(param1: String, param2: String): List[BasicResponse] = {
    Runtime.default
      .unsafeRun(
        doActionPut(BasicRequest(name = param1, value = param2))
          .provideLayer(serviceBasicService)
      )
  }

}
