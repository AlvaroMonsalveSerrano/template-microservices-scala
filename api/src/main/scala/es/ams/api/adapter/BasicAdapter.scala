package es.ams.api.adapter

import es.ams.api.views.BasicDTO.{CreateBasic, DeleteBasic, UpdateBasic}
import zio._
import zio.Exit.{Failure, Success}
import es.ams.api.views.BasicViews._
import es.ams.services._

protected[api] trait BasicAdapter {
  def getList(): Either[ErrorResponse, List[BasicResponse]]
  def doPost(dtoRequest: CreateBasic): Either[ErrorResponse, CreateResponse]
  def doPut(dtoRequest: UpdateBasic): Either[ErrorResponse, BasicResponse]
  def doDelete(dtoRequest: DeleteBasic): Either[ErrorResponse, Int]
}

protected[api] object BasicAdapter extends BasicAdapter {

  import BasicProgramAdapter._
  import es.ams.api.views.BasicDTO._

  def getList(): Either[ErrorResponse, List[BasicResponse]] = {
    Runtime.default.unsafeRunSync(programGetList().provideLayer(serviceBasicService)) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

  def doPost(dtoRequest: CreateBasic): Either[ErrorResponse, CreateResponse] = {
    Runtime.default.unsafeRunSync(programDoPost(dtoRequest).provideLayer(serviceBasicService)) match {
      case Success(value) => Right(CreateResponse(id = value))
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

  def doPut(dtoRequest: UpdateBasic): Either[ErrorResponse, BasicResponse] = {
    Runtime.default.unsafeRunSync(programDoPut(dtoRequest).provideLayer(serviceBasicService)) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

  def doDelete(dtoRequest: DeleteBasic): Either[ErrorResponse, Int] = {
    Runtime.default.unsafeRunSync(programDoDelete(dtoRequest).provideLayer(serviceBasicService)) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        println(s"EXCEPTION->${ex.prettyPrint}") // TODO to file log
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

}
