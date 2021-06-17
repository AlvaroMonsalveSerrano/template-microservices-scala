package es.ams.router.routes

import org.http4s.Request
import cats.effect._
import org.http4s._
import org.http4s.implicits._
import es.ams.router.routes.BasicRoutes._

class BasicRoutesTest extends munit.FunSuite {

  test("Test helloWorld route OK") {
    val requestTest = Request[IO](Method.GET, uri"/hello/pepe")
    val result      = helloWorldRoute.orNotFound.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)

  }

  test("Test basic Route: GET list") {
    val requestTest = Request[IO](Method.GET, uri"/list")
    val result      = basicRoute.orNotFound.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)

  }

  test("Test basic Route: DELETE resource") {
    val requestTest = Request[IO](Method.DELETE, uri"/resource/1")
    val result      = basicRoute.orNotFound.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)

  }

  test("Test basic Route: PUT resource") {
    val bodyTest    = """param1=value1&param2=value2"""
    val requestTest = Request[IO](Method.PUT, uri"/resource").withEntity(bodyTest)
    val result      = basicRoute.orNotFound.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)

  }

  test("Test basic Route: POST resource") {
    val bodyTest    = """param1=value1&param2=value2"""
    val requestTest = Request[IO](Method.POST, uri"/resource").withEntity(bodyTest)
    val result      = basicRoute.orNotFound.run(requestTest).unsafeRunSync()

    assertEquals(result.status, Status.Ok)
    // TODO check body.

  }

}
