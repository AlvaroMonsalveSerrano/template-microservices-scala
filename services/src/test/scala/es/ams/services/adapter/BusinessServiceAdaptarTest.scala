package es.ams.services.adapter

import zio.test._
import Assertion._
import es.ams.services._

object BusinessServiceAdaptarTest extends DefaultRunnableSpec {

  import BusinessServiceAdapter._

  // TODO Refactor
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

  def testDoSomething = testM("BusinessService doSomething") {
    val paramTest = "ParamTest"
    for {
      valueReturned <- doSomething1(paramTest)
    } yield {
      assert(valueReturned)(equalTo(paramTest))
      assert(valueReturned.isEmpty)(equalTo(false))
    }
  }

  def testFindAll = testM("BusinessService findAll Base") {
    for {
      resultList <- findAll()
    } yield {
      assert(resultList.size == 2)(equalTo(true))
    }
  }

  val individuall = suite("individually") {
    val utilTest = UtilTest(initScript)
    utilTest.run()
    suite("BusinessService")(
      testDoSomething,
      testFindAll
    ).provideCustomLayerShared(BusinessService.live(utilTest.getUriToDatabase()))

  }

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = {
    individuall
  }

}
