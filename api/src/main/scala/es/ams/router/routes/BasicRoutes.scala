package es.ams.router.routes

import cats.effect.IO
import cats.data.{Kleisli}
import io.circe.literal._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder

import org.http4s._
import org.http4s.dsl.io._

object BasicRoutes {

  import es.ams.adapter.AppAdapter._
  import es.ams.Utils._

  val helloWorldRoute = HttpRoutes
    .of[IO] { case GET -> Root / "hello" / name =>
      // curl -X GET  http://localhost:8080/hello/pepe
      Ok(s"Hello, $name.")
    }

  val basicRoute = HttpRoutes
    .of[IO] {
      case req @ POST -> Root / "resource" => {
        // curl -X POST -d "param1=value1&param2=value2" http://localhost:8080/resource
        req.decode[UrlForm] { data =>
          val param1 = getValueFromChain(data.values.get("param1").head)
          val param2 = getValueFromChain(data.values.get("param2").head)
          Ok(
            doActionPost(param1, param2)
          )
        }
      }
      case req @ GET -> Root / "list" => {
        // curl -X GET  http://localhost:8080/list
        getListEntity().flatMap(Ok(_))
      }
      case req @ DELETE -> Root / "resource" / IntVar(idToDelete) => {
        // curl -X DELETE http://localhost:8080/resource/1
        Ok("Delete resource")
      }

      case req @ PUT -> Root / "resource" => {
        // curl -X PUT -d "param1=value1&param2=value2" http://localhost:8080/resource
        req.decode[UrlForm] { data =>
          val param1 = getValueFromChain(data.values.get("param1").head)
          val param2 = getValueFromChain(data.values.get("param2").head)
          Ok(
            doActionPut(param1, param2)
          )
        }

      }

    }

}

object MiddlewareBasicRoutes {

  def myMiddleRedinessRoutes(httpRoutes: HttpRoutes[IO], header: Header): HttpRoutes[IO] = Kleisli {
    (req: Request[IO]) =>
      httpRoutes(req).map {
        case Status.Successful(resp) => resp.putHeaders(header)
        case resp                    => resp
      }
  }

}
