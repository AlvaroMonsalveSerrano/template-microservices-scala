package es.ams.api.router.routes

import cats.effect.IO
import cats.data.Kleisli
import es.ams.api.views.BasicDTO.DeleteBasic
import io.circe.syntax._
import org.http4s._
import org.http4s.dsl.io._

private[api] object BasicRoutes {

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
          val dtoCreateBasic =
            loadCreateBasic(elem1 = data.values.get("param1").head, elem2 = data.values.get("param2").head)
          doPost(dtoCreateBasic) match {
            case Left(error) => Ok("System error")
            case Right(value) => {
              Ok(value.asJson.noSpaces)
            }
          }
        }
      }

      case req @ GET -> Root / "basic" / "list" => {
        // curl -X GET  http://localhost:8080/basic/list
        getList() match {
          case Left(error)  => Ok("System error")
          case Right(value) => Ok(value.asJson.noSpaces)
        }
      }

      case req @ DELETE -> Root / "basic" / IntVar(idToDelete) => {
        // curl -X DELETE http://localhost:8080/basic/1
        doDelete(DeleteBasic(id = idToDelete)) match {
          case Left(error)  => Ok("System error")
          case Right(value) => Ok(value.asJson.noSpaces)
        }
      }

      case req @ PUT -> Root / "basic" => {
        // curl -X PUT -d "id=4&param1=44&param2=44" http://localhost:8080/basic
        req.decode[UrlForm] { data =>
          val dtoUpdateBasic = loadUpdateBasic(
            elem1 = data.values.get("id").get,
            elem2 = data.values.get("param1").head,
            elem3 = data.values.get("param2").head
          )
          doPut(dtoUpdateBasic) match {
            case Left(error)  => Ok("System error")
            case Right(value) => Ok(value.asJson.noSpaces)
          }
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
