package es.ams.adapter

import es.ams.persistence.repository.BasicRepository
import zio.{Has, IO, ZIO, ZLayer}
import zio.console.Console

import scala.concurrent.duration.Duration
import scala.concurrent.{Await}
import scala.concurrent.ExecutionContext.Implicits.global

object BasicServiceAdapter {

  import es.ams.views.BasicViews._
  import es.ams.exception.BasicException._

  type TypeParam    = String
  type ServiceError = ServiceBasicException
  type BasicService = Has[BasicService.Service]

  object BasicService {

    trait Service {
      def getListEntity(): IO[ServiceError, List[BasicResponse]]
      def doActionPost(param1: TypeParam, param2: TypeParam): IO[ServiceError, List[BasicResponse]]
      def doActionPut(param1: TypeParam, param2: TypeParam): IO[ServiceError, List[BasicResponse]]
    }

    // Service implementation
    val live: ZLayer[Any, Nothing, BasicService] = ZLayer.succeed(
      new Service {

        import es.ams.model.DomainBasic._

        def getResult(param1: String, param2: String): List[BasicResponse] = {
          List(
            BasicResponse(name = "1", value = param1),
            BasicResponse(name = "2", value = param2)
          )

        }

        override def getListEntity(): IO[ServiceError, List[BasicResponse]] = IO.succeed {

          // TODO Task[List[Base]]
          val resultRepository: List[Base] = Await.result(BasicRepository.apply().findAll(), Duration.Inf)
          resultRepository.map(elem => BasicResponse(name = elem.width_rec.toString, value = elem.length_rec.toString))
        }

        override def doActionPost(param1: TypeParam, param2: TypeParam): IO[ServiceError, List[BasicResponse]] =
          IO.succeed(
            getResult(param1, param2)
          )

        override def doActionPut(param1: TypeParam, param2: TypeParam): IO[ServiceError, List[BasicResponse]] =
          IO.succeed(
            getResult(param1, param2)
          )
      }
    )

  }
}

import BasicServiceAdapter._
package object basicservice {

  import es.ams.views.BasicViews._

  def getListEntity(): ZIO[BasicService, ServiceError, List[BasicResponse]] = ZIO.accessM(_.get.getListEntity())

  def doActionPost(param1: TypeParam, param2: TypeParam): ZIO[BasicService, ServiceError, List[BasicResponse]] =
    ZIO.accessM(_.get.doActionPost(param1, param2))

  def doActionPut(param1: TypeParam, param2: TypeParam): ZIO[BasicService, ServiceError, List[BasicResponse]] =
    ZIO.accessM(_.get.doActionPut(param1, param2))

  val serviceBasicService: ZLayer[Any, Nothing, Console with BasicService] = Console.live ++ BasicService.live
}
