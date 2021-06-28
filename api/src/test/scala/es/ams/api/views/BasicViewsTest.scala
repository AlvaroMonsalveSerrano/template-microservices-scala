package es.ams.api.views

import io.circe.syntax._
import io.circe.parser._

import es.ams.api.views.BasicViews.CreateResponse

class BasicViewsTest extends munit.FunSuite {

  test("CreateResponse test") {

    val objTest = CreateResponse(id = 1)

    assertEquals(objTest.asJson.noSpaces, """{"id":1}""")

    val stringJsonTest = """{"id":1}"""
    decode[CreateResponse](stringJsonTest) match {
      case Left(error) => fail(s"Error: ${error}")
      case Right(value) => {
        assert(value.id == 1, true)
      }
    }
  }

}
