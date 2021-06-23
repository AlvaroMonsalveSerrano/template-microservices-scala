package es.ams.api.router.routes

import cats.data.Kleisli
import org.http4s._

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object HealthyRoutes {

  val redinessRoute = HttpRoutes
    .of[IO] { case GET -> Root / "rediness" =>
      Ok(s"OK")
    }

  val livenessRoute = HttpRoutes
    .of[IO] { case GET -> Root / "liveness" =>
      Ok(s"OK")
    }

}

object MiddlewareHealthyRoutes {

  def myMiddleRedinessRoutes(httpRoutes: HttpRoutes[IO], header: Header): HttpRoutes[IO] = Kleisli {
    (req: Request[IO]) =>
      httpRoutes(req).map {
        case Status.Successful(resp) => resp.putHeaders(header)
        case resp                    => resp
      }
  }

}
