package es.ams.api.router.routes

import cats.data.Kleisli

import cats.effect.IO

import org.http4s._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

private[api] object HealthyRoutes {

  val redinessRoute = HttpRoutes
    .of[IO] { case GET -> Root / "rediness" =>
      Ok(s"OK")
    }

  val livenessRoute = HttpRoutes
    .of[IO] { case GET -> Root / "liveness" =>
      Ok(s"OK")
    }

}

private[api] object MiddlewareHealthyRoutes {

  def myMiddleRedinessRoutes(httpRoutes: HttpRoutes[IO], header: Header): HttpRoutes[IO] = Kleisli {
    (req: Request[IO]) =>
      httpRoutes(req).map {
        case Status.Successful(resp) => resp.putHeaders(header)
        case resp                    => resp
      }
  }

}
