package es.ams.api.router.routes

import cats.effect.IO
import cats.data.Kleisli

import io.circe.Encoder.AsObject.importedAsObjectEncoder
import io.circe.generic.auto.exportEncoder

import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s._
import org.http4s.dsl.io._

protected[api] object BasicRoutes {

  import es.ams.api.adapter.BasicAdapter._

  import es.ams.api.Utils._

  val helloWorldRoute = HttpRoutes
    .of[IO] { case GET -> Root / "hello" / name =>
      // curl -X GET  http://localhost:8080/hello/pepe
      Ok(s"Hello, $name.")
    }

  val basicRoute = HttpRoutes
    .of[IO] {

      case req @ POST -> Root / "basic" => {
        // curl -X POST -d "param1=3&param2=3" http://localhost:8080/basic
        req.decode[UrlForm] { data =>
          val param1 = getValueFromChain(data.values.get("param1").head)
          val param2 = getValueFromChain(data.values.get("param2").head)

          Ok(s"result=${doPost(param1, param2)}") // TODO response JSON
        }
      }

      case req @ GET -> Root / "basic" / "list" => {
        // curl -X GET  http://localhost:8080/basic/list
        Ok(s"result=${getList()}") // TODO response JSON
      }

      case req @ DELETE -> Root / "basic" / IntVar(idToDelete) => {
        // curl -X DELETE http://localhost:8080/basic/1
        Ok(s"Result=${doDelete(idToDelete)}")
      }

      case req @ PUT -> Root / "basic" => {
        // curl -X PUT -d "id=4&param1=44&param2=44" http://localhost:8080/basic
        req.decode[UrlForm] { data =>
          val id     = getValueFromChain(data.values.get("id").get)
          val param1 = getValueFromChain(data.values.get("param1").head)
          val param2 = getValueFromChain(data.values.get("param2").head)

          Ok(s"Result=${doPut(id.toInt, param1, param2)}") // TODO response JSON
        }
      }
    }
}

protected[api] object MiddlewareBasicRoutes {

  def myMiddleRedinessRoutes(httpRoutes: HttpRoutes[IO], header: Header): HttpRoutes[IO] = Kleisli {
    (req: Request[IO]) =>
      httpRoutes(req).map {
        case Status.Successful(resp) => resp.putHeaders(header)
        case resp                    => resp
      }
  }

}
