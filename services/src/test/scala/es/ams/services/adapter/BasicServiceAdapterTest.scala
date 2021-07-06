package es.ams.services.adapter

import zio.test._
import Assertion._
import es.ams.services._
import es.ams.services.views.BasicViews._

object BasicServiceAdapterTest extends DefaultRunnableSpec {

  import BasicServiceAdapter._

  val initScript =
    """
      | CREATE TABLE IF NOT EXISTS Base (
      |	  id_rec serial PRIMARY KEY,
      |	  length_rec int,
      |	  width_rec int
      |);
      |insert into base (length_rec, width_rec) values (11, 11);
      |insert into base (length_rec, width_rec) values (22, 22);
      |""".stripMargin

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

  val individuall = suite("individually") {
    val utilTest = UtilTest(initScript)
    utilTest.run()

    suite("Basic Service Adapter getListEntity")(
      testGetListEntity,
      testDoActionPost,
      testDoActionPut
    ).provideCustomLayerShared(BasicService.live(Some(utilTest.getUriToDatabase())))

  }

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = {
    individuall
  }

}
