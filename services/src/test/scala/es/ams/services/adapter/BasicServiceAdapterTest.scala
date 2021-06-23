package es.ams.services.adapter

import zio.test._
import Assertion._
import es.ams.services.adapter.basicservice._
import es.ams.services.views.BasicViews._

object BasicServiceAdapterTest extends DefaultRunnableSpec {

  import BasicServiceAdapter._

  def testGetListEntity = testM("getListEntity function") {
    for {
      lstEntity <- getListEntity()
    } yield {
      assert(lstEntity.size > 0)(equalTo(true))
    }
  }

  def testDoActionPost = testM("doActionPost function") {
    for {
      result <- doActionPost(BasicRequest(name = "1", value = "2"))
    } yield {
      assert(result > 0)(equalTo(true))
    }
  }

  def testDoActionPut = testM("doActionPut function") {
    for {
      result <- doActionPut(BasicRequest(name = "1", value = "2"))
    } yield {
      assert(result.size > 0)(equalTo(true))
      assert(result.size == 2)(equalTo(true))
    }
  }

  val individuall = suite("individually")(
    suite("Basic Service Adapter getListEntity")(
      testGetListEntity, // TODO Task[List[Base]]
      testDoActionPost,
      testDoActionPut
    ).provideCustomLayerShared(BasicService.live)
  )

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = {
    individuall
  }

}
