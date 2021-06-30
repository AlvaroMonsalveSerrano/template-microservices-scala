package es.ams.api.app

import cats.effect._

import scala.concurrent.ExecutionContext.Implicits.global
import org.http4s.server.blaze._

/** Test:
  *  curl http://localhost:8080/liveness
  *  curl http://localhost:8080/rediness
  *  curl http://localhost:8080/hello/pepe
  *  curl -X POST -d "param1=value1" http://localhost:8080/echo
  *
  *  Ejemplo de cliente:
  *    def run(args: List[String]): IO[ExitCode] = {
  *        val req = POST(UrlForm("q" -> "http4s"), uri"https://duckduckgo.com/")
  *        val responseBody = BlazeClientBuilder[IO](global).resource.use(_.expect[String](req))
  *        responseBody.flatMap(resp => IO.println(resp)).as(ExitCode.Success)
  *    }
  */
object App extends IOApp {

  import es.ams.api.router.AppRouter

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(AppRouter.healthyRouter)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
