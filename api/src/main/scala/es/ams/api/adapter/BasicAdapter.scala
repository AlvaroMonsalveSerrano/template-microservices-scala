package es.ams.api.adapter

import es.ams.services.adapter.BasicServiceAdapter.BasicService
import es.ams.services.adapter.basicservice._
import zio._
import es.ams.services.views.BasicViews._
import zio.console.{Console, putStrLn}

trait BasicAdapter {
  def getList(): List[BasicResponse]
  def doPost(param1: String, param2: String): Int
  def doPut(id: Int, param1: String, param2: String): BasicResponse

}

object BasicAdapter extends BasicAdapter {

  def getList(): List[BasicResponse] = {

    val program: ZIO[Console with BasicService, Throwable, List[BasicResponse]] = for {
      _    <- putStrLn("[START] GetList...")
      list <- getListEntity()
      _    <- putStrLn("[END] GetList...")
    } yield { list }

    Runtime.default.unsafeRun(program.provideLayer(serviceBasicService))
  }

  def doPost(param1: String, param2: String): Int = {

    val program: ZIO[Console with BasicService, Throwable, Int] = for {
      _               <- putStrLn("[START] Insert entity ...")
      idEntityCreated <- doActionPost(BasicRequest(name = param1, value = param2))
      _               <- putStrLn("[END] Insert entity ...")
    } yield { idEntityCreated }

    Runtime.default.unsafeRun(program.provideLayer(serviceBasicService))
  }

  def doPut(id: Int, param1: String, param2: String): BasicResponse = {

    val program: ZIO[Console with BasicService, Throwable, BasicResponse] = for {
      _             <- putStrLn("[START] Update entity ...")
      entityUpdated <- doActionPut(BasicRequest(id = Some(id), name = param1, value = param2))
      _             <- putStrLn("[END] Update entity ...")
    } yield { entityUpdated }

    Runtime.default.unsafeRun(program.provideLayer(serviceBasicService))
  }

  def doDelete(id: Int): Int = {
    val program: ZIO[Console with BasicService, Throwable, Int] = for {
      _               <- putStrLn("[START] Delete entity ...")
      idEntityDeleted <- doActionDelete(id)
      _               <- putStrLn("[END] Delete entity ...")
    } yield { idEntityDeleted }

    Runtime.default.unsafeRun(program.provideLayer(serviceBasicService))
  }

}
