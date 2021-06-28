package es.ams.api.adapter

import es.ams.api.views.BasicDTO.{CreateBasic, UpdateBasic}
import es.ams.api.views.BasicViews.{BasicResponse, ErrorResponse}

class AppAdapterTest extends munit.FunSuite {

  import es.ams.api.adapter.BasicAdapter._

  test("Test AppAdapter: getListEntity function ") {
    val result = getList()
    assertEquals(result.fold(exp => false, basicResponse => true), true)
  }

  test("Test AppAdapter: doActionPost function ") {
    CreateBasic(name = "1", value = "2")
    val result = doPost(CreateBasic(name = "1", value = "2"))
    assertEquals(result.fold(exp => false, basicResponse => true), true)
  }

  test("Test AppAdapter: doActionPut function ") {
    val result: Either[ErrorResponse, BasicResponse] = doPut(UpdateBasic(id = 1, name = "1", value = "2"))
    assertEquals(result.fold(exp => false, basicResponse => true), true)
  }

}
