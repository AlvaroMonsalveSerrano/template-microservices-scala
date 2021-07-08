package es.ams.api.adapter

import es.ams.api.views.BasicDTO.{CreateBasic, DeleteBasic, UpdateBasic}
import zio._
import zio.Exit.{Failure, Success}
import es.ams.api.views.BasicViews._
import es.ams.services._
import es.ams.api.Utils

import com.typesafe.scalalogging._

import scala.concurrent.ExecutionContext.Implicits.global

private[api] trait BasicAdapter {
  def getList(): Either[ErrorResponse, List[BasicResponse]]
  def doPost(dtoRequest: CreateBasic): Either[ErrorResponse, CreateResponse]
  def doPut(dtoRequest: UpdateBasic): Either[ErrorResponse, BasicResponse]
  def doDelete(dtoRequest: DeleteBasic): Either[ErrorResponse, Int]
}

private[api] object BasicAdapter extends BasicAdapter {

  import BasicProgramAdapter._
  import es.ams.api.views.BasicDTO._

  private val logger = Logger[BasicAdapter.type]

  def getList(): Either[ErrorResponse, List[BasicResponse]] = {
    logger.info(s"[BasicAdapter] getList()")

    // Hay que cargar la URL desde la configuración.
    Runtime.default.unsafeRunSync(
      programGetList().provideCustomLayer(serviceBasicService(Utils.apply().loadURIPostgresql()))
    ) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        logger.error(s"EXCEPTION->${ex.prettyPrint}")
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

  def doPost(dtoRequest: CreateBasic): Either[ErrorResponse, CreateResponse] = {
    logger.info(s"[BasicAdapter] doPost()")
    Runtime.default.unsafeRunSync(
      programDoPost(dtoRequest).provideLayer(serviceBasicService(Utils.apply().loadURIPostgresql()))
    ) match {
      case Success(value) => Right(CreateResponse(id = value))
      case Failure(ex) => {
        logger.error(s"EXCEPTION->${ex.prettyPrint}")
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

  def doPut(dtoRequest: UpdateBasic): Either[ErrorResponse, BasicResponse] = {
    logger.info(s"[BasicAdapter] doPut()")
    Runtime.default.unsafeRunSync(
      programDoPut(dtoRequest).provideLayer(serviceBasicService(Utils.apply().loadURIPostgresql()))
    ) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        logger.error(s"EXCEPTION->${ex.prettyPrint}")
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

  def doDelete(dtoRequest: DeleteBasic): Either[ErrorResponse, Int] = {
    logger.info(s"[BasicAdapter] doDelete()")
    Runtime.default.unsafeRunSync(
      programDoDelete(dtoRequest).provideLayer(serviceBasicService(Utils.apply().loadURIPostgresql()))
    ) match {
      case Success(value) => Right(value)
      case Failure(ex) => {
        logger.error(s"EXCEPTION->${ex.prettyPrint}")
        Left(ErrorResponse(codError = 0, message = "Internal error"))
      }
    }
  }

}
