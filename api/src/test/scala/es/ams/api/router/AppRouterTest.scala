package es.ams.api.router

import org.http4s.Request
import cats.effect._
import org.http4s._
import org.http4s.implicits._
import AppRouter._
import org.http4s.util.CaseInsensitiveString

class AppRouterTest extends munit.FunSuite {

  test("Test AppRouter: rediness function") {
    val requestTest = Request[IO](Method.GET, uri"/rediness")
    val result      = healthyRouter.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)
  }

  test("Test AppRouter: liveness function") {
    val requestTest = Request[IO](Method.GET, uri"/liveness")
    val result      = healthyRouter.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)
  }

  test("Test AppRouter: myMiddlewareBasic") {
    val requestTest = Request[IO](Method.GET, uri"/hello/test")
    val result      = healthyRouter.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)
    assertEquals(result.headers.get(CaseInsensitiveString("nameHeader")).isEmpty, false)
  }

  // TODO test function: helloWorldRoute, basicRoute

}
