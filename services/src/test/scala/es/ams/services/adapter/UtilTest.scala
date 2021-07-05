package es.ams.services.adapter

import com.dimafeng.testcontainers.PostgreSQLContainer

import java.sql.{Connection, DriverManager}

case class UtilTest(initScript: String) {

  val container: PostgreSQLContainer = {
    val psql: PostgreSQLContainer = new PostgreSQLContainer()
    psql.start()
    psql.container.withInitScript(initScript)
    psql
  }

  def run(): Boolean = {
    Class.forName(container.driverClassName)
    val conn: Connection = DriverManager
      .getConnection(container.jdbcUrl, container.username, container.password)
    conn.createStatement().execute(initScript)
  }

  def stop() = {
    container.stop()
  }

  def getUriToDatabase(): String = s"${container.jdbcUrl}&user=${container.username}&password=${container.password}"

}
object UtilTest {
  def apply(script: String) = new UtilTest(script)
}
