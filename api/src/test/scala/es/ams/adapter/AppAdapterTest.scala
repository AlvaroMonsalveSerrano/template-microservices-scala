package es.ams.adapter

class AppAdapterTest extends munit.FunSuite {

  import es.ams.adapter.BasicAdapter._

  test("Test AppAdapter: getListEntity function ") {
    val result = getList()
    assertEquals(result.size > 0, true)
  }

  test("Test AppAdapter: doActionPost function ") {
    val result = doPost("param1", "param2")
    assertEquals(result.size > 0, true)
  }

  test("Test AppAdapter: doActionPut function ") {
    val result = doPut("param1", "param2")
    assertEquals(result.size > 0, true)
  }

}
