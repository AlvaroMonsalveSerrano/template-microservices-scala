package es.ams.router.routes

import org.http4s.Request
import cats.effect._
import org.http4s._
import org.http4s.implicits._
import es.ams.router.routes.HealthyRoutes._

class HealthyRoutesTest extends munit.FunSuite {

  test("Test healthy Route: redinessRoute function") {
    val requestTest = Request[IO](Method.GET, uri"/rediness")
    val result      = redinessRoute.orNotFound.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)

  }

  test("Test healthy Route: livenessRoute function") {
    val requestTest = Request[IO](Method.GET, uri"/liveness")
    val result      = livenessRoute.orNotFound.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)

  }

}
