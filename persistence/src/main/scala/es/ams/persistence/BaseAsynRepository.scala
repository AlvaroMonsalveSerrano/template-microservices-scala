package es.ams.persistence

import cats.effect.IO
import ciris.{ConfigValue, env, prop}
import es.ams.persistence.queries.{BasicQueries, OtherEntityQueries}
import io.getquill.{PostgresAsyncContext, SnakeCase}
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}

import scala.concurrent.ExecutionContext

/** Definition of the base class of the repository. It has a reference to the Quill context which must implement those
  * operations for the different domain entities.
  *
  * @param configPrefix Context name
  */
protected[persistence] abstract class BaseAsynRepository(configPrefix: String)(implicit
    executionContext: ExecutionContext
) {
  private val urlPostgresql = LoadEnvironmentDatabase.apply().loadURIPostgresql()
  private val config: Config = {
    ConfigFactory
      .empty()
      .withValue(
        "url",
        ConfigValueFactory.fromAnyRef(urlPostgresql)
      )
  }

//  protected val ctx = new PostgresAsyncContext(SnakeCase, configPrefix) with BasicQueries with OtherEntityQueries
  protected val ctx = new PostgresAsyncContext(SnakeCase, config) with BasicQueries with OtherEntityQueries

}

private[persistence] class LoadEnvironmentDatabase()(implicit executionContext: ExecutionContext) {

  private implicit val cs = IO.contextShift(executionContext)

  private lazy val host: ConfigValue[String]     = env("POSTGRESQL_HOST").or(prop("localhost")).as[String]
  private lazy val port: ConfigValue[String]     = env("POSTGRESQL_PORT").or(prop("port")).as[String]
  private lazy val database: ConfigValue[String] = env("POSTGRESQL_DATABASE").or(prop("database")).as[String]
  private lazy val user: ConfigValue[String]     = env("POSTGRESQL_USER").or(prop("user")).as[String]
  private lazy val password: ConfigValue[String] = env("POSTGRESQL_PWD").or(prop("pwd")).as[String]

  def loadURIPostgresql(): String = {
    "postgresql://" +
      host.load[IO].unsafeRunSync() + ":" +
      port.load[IO].unsafeRunSync() + "/" +
      database.load[IO].unsafeRunSync() + "?user=" +
      user.load[IO].unsafeRunSync() + "&password=" +
      password.load[IO].unsafeRunSync()
  }
}
object LoadEnvironmentDatabase {
  def apply()(implicit executionContext: ExecutionContext) = new LoadEnvironmentDatabase()
}
