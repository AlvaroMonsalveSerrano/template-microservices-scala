package es.ams.adapter

class AppAdapterTest extends munit.FunSuite {

  import es.ams.adapter.BasicAdapter._

  test("Test AppAdapter: getListEntity function ") {
    val result = getListEntity().unsafeRunSync()
    assertEquals(result.size, 3)
  }

  test("Test AppAdapter: doActionPost function ") {
    val result = doActionPost("param1", "param2").unsafeRunSync()
    assertEquals(result.size, 2)
  }

  test("Test AppAdapter: doActionPut function ") {
    val result = doActionPut("param1", "param2").unsafeRunSync()
    assertEquals(result.size, 2)
  }

  test("Test AppAdapter: getResult function ") {
    val result = getResult("param1", "param2")
    assertEquals(result.size, 2)
  }

}
