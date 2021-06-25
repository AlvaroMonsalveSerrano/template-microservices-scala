package es.ams.services.adapter

import es.ams.persistence.BasicRepository
import zio.{Task, ZIO, ZLayer}

import es.ams.services.BasicService

object BasicServiceAdapter {

  import es.ams.services.views.BasicViews._
  import es.ams.services.exception.BasicException._

  type TypeParam    = String
  type ServiceError = ServiceBasicException

  object BasicService {

    trait Service {
      def getListEntity(): Task[List[BasicServiceResponse]]
      def doActionPost(request: BasicServiceRequest): Task[Int]
      def doActionPut(request: BasicServiceRequest): Task[BasicServiceResponse]
      def doDelete(id: Int): Task[Int]
    }

    // Service implementation
    val live: ZLayer[Any, Nothing, BasicService] = ZLayer.succeed(
      new Service {

        import es.ams.model._

        override def getListEntity(): Task[List[BasicServiceResponse]] = {
          def toListBasicResponse(list: List[Base]): List[BasicServiceResponse] =
            list.map(elem => BasicServiceResponse(name = elem.length_rec.toString, value = elem.width_rec.toString))

          ZIO.fromFuture(implicit ec => BasicRepository.apply().findAll().map(elem => toListBasicResponse(elem)))
        }

        override def doActionPost(request: BasicServiceRequest): Task[Int] = {
          ZIO
            .fromFuture(implicit ec =>
              BasicRepository
                .apply()
                .insert(Base(id_rec = 0, length_rec = request.name.toInt, width_rec = request.value.toInt))
            )
            .catchAll(exception =>
              ZIO.fail(
                new ServiceBasicException(
                  codError = 11,
                  message = s"Error interno: ${exception.getMessage}",
                  cause = exception
                )
              )
            )
        }

        override def doActionPut(request: BasicServiceRequest): Task[BasicServiceResponse] = {
          Task
            .fromFuture(implicit ec =>
              BasicRepository
                .apply()
                .update(Base(id_rec = request.id.get, length_rec = request.name.toInt, width_rec = request.value.toInt))
                .map(elem => BasicServiceResponse(name = elem.length_rec.toString, value = elem.width_rec.toString))
            )
            .catchAll(exception =>
              ZIO.fail(
                new ServiceBasicException(
                  codError = 11,
                  message = s"Error interno: ${exception.getMessage}",
                  cause = exception
                )
              )
            )
        }

        override def doDelete(id: Int): Task[Int] = {
          ZIO
            .fromFuture(implicit ec => BasicRepository.apply().delete(id))
            .catchAll(exception =>
              ZIO.fail(
                new ServiceBasicException(
                  codError = 11,
                  message = s"Error interno: ${exception.getMessage}",
                  cause = exception
                )
              )
            )
        }
      }
    )
  }
}
