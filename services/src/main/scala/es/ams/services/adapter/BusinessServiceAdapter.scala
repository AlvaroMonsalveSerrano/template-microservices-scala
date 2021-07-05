package es.ams.services.adapter

import es.ams.persistence.BasicRepository
import zio._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object BusinessServiceAdapter {

  import es.ams.services.BusinessService
  import es.ams.model.Base

  object BusinessService {

    trait Service {
      def doSomething1(param1: String): UIO[String]

      def findAll(): UIO[List[Base]]
    }

    class ServiceImpl(private val urlTest: String) extends Service {

      val basicRepository = new BasicRepository("", Some(urlTest))

      override def doSomething1(param1: String): UIO[String] = UIO.succeed {
        println(s"[BusinessService] URL database $urlTest")
        println(s"[BusinessService] Hacemos algo con $param1")
        param1
      }

      override def findAll(): UIO[List[Base]] = {
        UIO.succeed(Await.result(basicRepository.findAll(), Duration.Inf))
      }
    }

    def live(pUrl: String): ZLayer[Any, Nothing, BusinessService] = ZLayer.succeed(
      new ServiceImpl(pUrl)
    )

  }

}
