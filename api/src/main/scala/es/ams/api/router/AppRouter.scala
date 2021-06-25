package es.ams.api.router

import cats.syntax.all._
import org.http4s.Header
import org.http4s.implicits._
import org.http4s.server.Router

protected[api] object AppRouter {

  import es.ams.api.router.routes.HealthyRoutes._
  import es.ams.api.router.routes.BasicRoutes._
  import es.ams.api.router.routes.MiddlewareBasicRoutes._

  private val myMiddlewareBasic = myMiddleRedinessRoutes(helloWorldRoute, Header("nameHeader", "valueHeader"))

  private val healthRoutes =
    redinessRoute <+>
      livenessRoute <+>
      myMiddlewareBasic <+>
      helloWorldRoute <+>
      basicRoute

  val healthyRouter = Router("/" -> healthRoutes).orNotFound
}
