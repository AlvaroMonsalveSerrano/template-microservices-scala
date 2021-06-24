package es.ams.services.adapter

import es.ams.persistence.repository.BasicRepository
import zio.{Has, Task, ZIO, ZLayer}
import zio.console.Console

object BasicServiceAdapter {

  import es.ams.services.views.BasicViews._
  import es.ams.services.exception.BasicException._

  type TypeParam    = String
  type ServiceError = ServiceBasicException
  type BasicService = Has[BasicService.Service]

  object BasicService {

    trait Service {
      def getListEntity(): Task[List[BasicResponse]]
      def doActionPost(request: BasicRequest): Task[Int]
      def doActionPut(request: BasicRequest): Task[BasicResponse]
      def doDelete(id: Int): Task[Int]
    }

    // Service implementation
    val live: ZLayer[Any, Nothing, BasicService] = ZLayer.succeed(
      new Service {

        import es.ams.model.DomainBasic._

//        def getResult(param1: String, param2: String): List[BasicResponse] = {
//          List(
//            BasicResponse(name = "1", value = param1),
//            BasicResponse(name = "2", value = param2)
//          )
//        }

        override def getListEntity(): Task[List[BasicResponse]] = {
          def toListBasicResponse(list: List[Base]): List[BasicResponse] =
            list.map(elem => BasicResponse(name = elem.length_rec.toString, value = elem.width_rec.toString))

          ZIO.fromFuture(implicit ec => BasicRepository.apply().findAll().map(elem => toListBasicResponse(elem)))
        }

        override def doActionPost(request: BasicRequest): Task[Int] = {
          ZIO.fromFuture(implicit ec =>
            BasicRepository
              .apply()
              .insert(Base(id_rec = 0, length_rec = request.name.toInt, width_rec = request.value.toInt))
          )
        }

        override def doActionPut(request: BasicRequest): Task[BasicResponse] = {
          ZIO.fromFuture(implicit ec =>
            BasicRepository
              .apply()
              .update(Base(id_rec = request.id.get, length_rec = request.name.toInt, width_rec = request.value.toInt))
              .map(elem => BasicResponse(name = elem.length_rec.toString, value = elem.width_rec.toString))
          )
        }

        override def doDelete(id: Int): Task[Int] = {
          ZIO.fromFuture(implicit ec => BasicRepository.apply().delete(id))
        }

      }
    )

  }
}

import BasicServiceAdapter._
package object basicservice {

  import es.ams.services.views.BasicViews._

  def getListEntity(): ZIO[BasicService, Throwable, List[BasicResponse]] = ZIO.accessM(_.get.getListEntity())

  def doActionPost(request: BasicRequest): ZIO[BasicService, Throwable, Int] =
    ZIO.accessM(_.get.doActionPost(request))

  def doActionPut(request: BasicRequest): ZIO[BasicService, Throwable, BasicResponse] =
    ZIO.accessM(_.get.doActionPut(request))

  def doActionDelete(id: Int): ZIO[BasicService, Throwable, Int] = ZIO.accessM(_.get.doDelete(id))

  val serviceBasicService: ZLayer[Any, Nothing, Console with BasicService] = Console.live ++ BasicService.live
}
