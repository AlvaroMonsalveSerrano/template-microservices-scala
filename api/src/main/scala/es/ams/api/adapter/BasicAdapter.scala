package es.ams.api.adapter

import es.ams.services.adapter.BasicServiceAdapter.BasicService
import es.ams.services._
import zio._
import es.ams.api.views.BasicViews._
import es.ams.services.views.BasicViews.{BasicServiceRequest, BasicServiceResponse}
import zio.Exit.{Failure, Success}
import zio.console.{Console, putStrLn}

trait BasicAdapter {
  def getList(): Either[ErrorResponse, List[BasicResponse]]
  def doPost(param1: String, param2: String): Either[ErrorResponse, Int]
  def doPut(id: Int, param1: String, param2: String): Either[ErrorResponse, BasicServiceResponse]
  def doDelete(id: Int): Either[ErrorResponse, Int]
}

object BasicAdapter extends BasicAdapter {

  def getList(): Either[ErrorResponse, List[BasicResponse]] = {

    val program: ZIO[Console with BasicService, Throwable, List[BasicResponse]] = for {
      _    <- putStrLn("[START] GetList...")
      list <- getListEntity()
      _    <- putStrLn("[END] GetList...")
    } yield { list.map(elem => BasicResponse(name = elem.name, value = elem.value)) }

    Runtime.default.unsafeRunSync(program.provideLayer(serviceBasicService)) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

  def doPost(param1: String, param2: String): Either[ErrorResponse, Int] = {

    val program: ZIO[Console with BasicService, Throwable, Int] = for {
      _               <- putStrLn("[START] Insert entity ...")
      idEntityCreated <- doActionPost(BasicServiceRequest(name = param1, value = param2))
      _               <- putStrLn("[END] Insert entity ...")
    } yield { idEntityCreated }

    Runtime.default.unsafeRunSync(program.provideLayer(serviceBasicService)) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }

  }

  def doPut(id: Int, param1: String, param2: String): Either[ErrorResponse, BasicServiceResponse] = {

    val program: ZIO[Console with BasicService, Throwable, BasicServiceResponse] = for {
      _             <- putStrLn("[START] Update entity ...")
      entityUpdated <- doActionPut(BasicServiceRequest(id = Some(id), name = param1, value = param2))
      _             <- putStrLn("[END] Update entity ...")
    } yield { entityUpdated }

    Runtime.default.unsafeRunSync(program.provideLayer(serviceBasicService)) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }

  }

  def doDelete(id: Int): Either[ErrorResponse, Int] = {
    val program: ZIO[Console with BasicService, Throwable, Int] = for {
      _               <- putStrLn("[START] Delete entity ...")
      idEntityDeleted <- doActionDelete(id)
      _               <- putStrLn("[END] Delete entity ...")
    } yield { idEntityDeleted }

    Runtime.default.unsafeRunSync(program.provideLayer(serviceBasicService)) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

}
