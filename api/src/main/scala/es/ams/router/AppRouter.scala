package es.ams.router

import cats.syntax.all._
import org.http4s.Header
import org.http4s.implicits._
import org.http4s.server.Router

object AppRouter {

  import es.ams.router.routes.HealthyRoutes._
  import es.ams.router.routes.BasicRoutes._

  import es.ams.router.routes.MiddlewareBasicRoutes._

  private val myMiddlewareBasic = myMiddleRedinessRoutes(helloWorldRoute, Header("nameHeader", "valueHeader"))

  private val healthRoutes =
    redinessRoute <+>
      livenessRoute <+>
      myMiddlewareBasic <+>
      helloWorldRoute <+>
      basicRoute

  val healthyRouter = Router("/" -> healthRoutes).orNotFound
}
