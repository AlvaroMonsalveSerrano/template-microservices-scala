package es.ams.services.adapter

import zio.test._
import Assertion._
import es.ams.services._

object CommonServiceTest extends DefaultRunnableSpec {

  import BusinessServiceAdapter._
  import BasicServiceAdapter._

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
      listBase      <- findAll()
    } yield {
      assert(valueReturned)(equalTo(paramTest))
      assert(valueReturned.isEmpty)(equalTo(false))
      assert(listBase.size)(equalTo(2))
    }
  }

  val individuall = suite("individually") {
    val utilTest = UtilTest(initScript)
    utilTest.run()

    suite("BusinessService")(
      testDoSomething
    ).provideCustomLayerShared(BusinessService.live(utilTest.getUriToDatabase()) ++ BasicService.live)
  }

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = {
    individuall
  }

}
