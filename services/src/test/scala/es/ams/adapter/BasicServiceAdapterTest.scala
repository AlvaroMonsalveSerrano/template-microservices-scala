package es.ams.adapter

import zio.test._
import Assertion._

object BasicServiceAdapterTest extends DefaultRunnableSpec {

  import es.ams.adapter.BasicServiceAdapter._
  import es.ams.adapter.basicservice._

  def testGetListEntity = testM("getListEntity function") {
    for {
      lstEntity <- getListEntity()
    } yield {
      assert(lstEntity.size)(equalTo(2))
    }
  }

  def testDoActionPost = testM("doActionPost function") {
    for {
      result <- doActionPost("param1", "param2")
    } yield {
      assert(result.size > 0)(equalTo(true))
      assert(result.size == 2)(equalTo(true))
    }
  }

  def testDoActionPut = testM("doActionPut function") {
    for {
      result <- doActionPut("param1", "param2")
    } yield {
      assert(result.size > 0)(equalTo(true))
      assert(result.size == 2)(equalTo(true))
    }
  }

  val individuall = suite("individually")(
    suite("Basic Service Adapter getListEntity")(
//      testGetListEntity, // TODO Task[List[Base]]
      testDoActionPost,
      testDoActionPut
    ).provideCustomLayerShared(BasicService.live)
  )

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = {
    individuall
  }

}
