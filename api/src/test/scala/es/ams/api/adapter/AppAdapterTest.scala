package es.ams.api.adapter

class AppAdapterTest extends munit.FunSuite {

  import es.ams.api.adapter.BasicAdapter._

  test("Test AppAdapter: getListEntity function ") {
    val result = getList()
    assertEquals(result.size > 0, true)
  }

  test("Test AppAdapter: doActionPost function ") {
    val result = doPost("1", "2")
    assertEquals(result > 0, true)
  }

  test("Test AppAdapter: doActionPut function ") {
    val result = doPut(1, "1", "2")
    assertEquals(result.isValid(), true)
  }

}
