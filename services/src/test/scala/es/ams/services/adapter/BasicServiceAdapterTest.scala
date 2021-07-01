package es.ams.services.adapter

import zio.test._
import Assertion._
import es.ams.services._
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
      result <- doActionPost(BasicServiceRequest(name = "1", value = "2"))
    } yield {
      assert(result > 0)(equalTo(true))
    }
  }

  def testDoActionPut = testM("doActionPut function") {
    for {
      result <- doActionPut(BasicServiceRequest(id = Some(1), name = "1", value = "2"))
    } yield {
      assert(result.isValid())(equalTo(true))
    }
  }

  val individuall = suite("individually")(
    suite("Basic Service Adapter getListEntity")(
//      testGetListEntity, // TODO
//      testDoActionPost,
//      testDoActionPut
    ).provideCustomLayerShared(BasicService.live)
  )

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = {
    individuall
  }

}
