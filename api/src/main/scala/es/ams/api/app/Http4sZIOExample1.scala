package es.ams.api.app

import es.ams.services.{getListEntity, serviceBasicService}
import zio._
import zio.console._
import zio.interop.catz._
import zio.interop.catz.implicits._
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

object Http4sZIOExample1 extends App {

  private val dsl = Http4sDsl[Task]
  import dsl._

  private val helloWorlService = HttpRoutes
    .of[Task] { case GET -> Root / "hello" =>
      val result = Runtime.default.unsafeRun(getListEntity().provideLayer(serviceBasicService()))
      println(s"result=${result}")
      Ok(s"Hello, Al result=${result}")
    }
    .orNotFound

  def run(args: List[String]): zio.URIO[zio.ZEnv, ExitCode] =
    ZIO
      .runtime[ZEnv]
      .flatMap { implicit runtime =>
        BlazeServerBuilder[Task](runtime.platform.executor.asEC)
          .bindHttp(8080, "localhost")
          .withHttpApp(helloWorlService)
          .resource
          .toManagedZIO
          .useForever
          .foldCauseM(
            err => putStrLn(err.prettyPrint).as(ExitCode.failure),
            _ => ZIO.succeed(ExitCode.success)
          )

      }

}
