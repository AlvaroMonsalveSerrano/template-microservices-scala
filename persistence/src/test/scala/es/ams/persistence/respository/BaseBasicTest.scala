package es.ams.persistence.respository

import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}
import org.scalatest.flatspec.AnyFlatSpec

import java.sql.{Connection, DriverManager}

class BaseBasicTest extends AnyFlatSpec with ForAllTestContainer {
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

  def executeInitScript(): Boolean = {
    Class.forName(container.driverClassName)
    val conn: Connection = DriverManager
      .getConnection(container.jdbcUrl, container.username, container.password)
    conn.createStatement().execute(initScript)
  }

  def getURIDatabase(): String = s"${container.jdbcUrl}&user=${container.username}&password=${container.password}"

}
