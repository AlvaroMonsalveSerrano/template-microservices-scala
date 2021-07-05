package es.ams.services.adapter

import zio.test._
import Assertion._
import com.dimafeng.testcontainers.PostgreSQLContainer
import es.ams.services._

import java.sql.{Connection, DriverManager}

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

  val container: PostgreSQLContainer = {
    val psql: PostgreSQLContainer = new PostgreSQLContainer()
    psql.start()
    psql.container.withInitScript(initScript)
    psql
  }

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
    // TODO Refactor
    val container: PostgreSQLContainer = {
      val psql: PostgreSQLContainer = new PostgreSQLContainer()
      psql.start()
      psql.container.withInitScript(initScript)
      psql
    }

    // TODO Refactor
    Class.forName(container.driverClassName)
    val conn: Connection = DriverManager
      .getConnection(container.jdbcUrl, container.username, container.password)
    conn.createStatement().execute(initScript)
    val urlTest = s"${container.jdbcUrl}&user=${container.username}&password=${container.password}"

    suite("BusinessService")(
      testDoSomething
    ).provideCustomLayerShared(BusinessService.live(urlTest) ++ BasicService.live)
  }

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = {
    individuall
  }

}
