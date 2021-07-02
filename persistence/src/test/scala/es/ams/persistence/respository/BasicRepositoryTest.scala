package es.ams.persistence.respository

import es.ams.model._
import es.ams.persistence.BasicRepository
import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}

import java.sql.{Connection}

import java.sql.DriverManager

class BasicRepositoryTest extends AnyFlatSpec with ForAllTestContainer {

//  private val logger = LoggerFactory.getLogger(this.getClass)

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

  override val container: PostgreSQLContainer = {
    val psql: PostgreSQLContainer = new PostgreSQLContainer()
    psql.start()
    psql.container.withInitScript(initScript)
    psql
  }

  def stop() = {
    container.stop()
  }

  "PostgreSQL queries persistence" should "Select all with class" in {

    Class.forName(container.driverClassName)
    val conn: Connection = DriverManager
      .getConnection(container.jdbcUrl, container.username, container.password)
    conn.createStatement().execute(initScript)

    val urlTest            = s"${container.jdbcUrl}&user=${container.username}&password=${container.password}"
    val basicRepository    = new BasicRepository("", Some(urlTest))
    val result: List[Base] = Await.result(basicRepository.findAll(), Duration.Inf)

    println(s"result=${result}")
    assert(result.size == 2)
  }

  it should "Select all with object" in {
    val basicRepository    = BasicRepository("asynpostgres")
    val result: List[Base] = Await.result(basicRepository.findAll(), Duration.Inf)

    assert(result.size > 0)

  }

}
