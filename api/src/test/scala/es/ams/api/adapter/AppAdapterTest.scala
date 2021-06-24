package es.ams.api.adapter

import es.ams.api.views.BasicViews.ErrorResponse
import es.ams.services.views.BasicViews.BasicServiceResponse

class AppAdapterTest extends munit.FunSuite {

  import es.ams.api.adapter.BasicAdapter._

  test("Test AppAdapter: getListEntity function ") {
    val result = getList()
    assertEquals(result.fold(exp => false, basicResponse => true), true)
  }

  test("Test AppAdapter: doActionPost function ") {
    val result = doPost("1", "2")
    assertEquals(result.fold(exp => false, basicResponse => true), true)
  }

  test("Test AppAdapter: doActionPut function ") {
    val result: Either[ErrorResponse, BasicServiceResponse] = doPut(1, "1", "2")
    assertEquals(result.fold(exp => false, basicResponse => true), true)
  }

}
