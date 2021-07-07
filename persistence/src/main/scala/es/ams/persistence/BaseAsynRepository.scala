package es.ams.persistence

import cats.effect.IO
import ciris.{ConfigValue, env, prop}
import es.ams.persistence.queries.{BasicQueries, OtherEntityQueries}
import io.getquill.{PostgresAsyncContext, SnakeCase}
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}

import scala.concurrent.ExecutionContext
import com.typesafe.scalalogging._

/** Definition of the base class of the repository. It has a reference to the Quill context which must implement those
  * operations for the different domain entities.
  *
  * @param configPrefix Context name
  */
protected[persistence] abstract class BaseAsynRepository(
    configPrefix: String,
    urlDatabase: Option[String] = None
)(implicit
    executionContext: ExecutionContext
) {
  protected val ctx = LoadEnvironmentDatabase.apply().loadPostgresqlContext(configPrefix, urlDatabase)

}

private[persistence] class LoadEnvironmentDatabase()(implicit executionContext: ExecutionContext) {

  private val logger   = Logger[LoadEnvironmentDatabase]
  private val HOST     = "POSTGRESQL_HOST"
  private val PORT     = "POSTGRESQL_PORT"
  private val DATABASE = "POSTGRESQL_DATABASE"
  private val USER     = "POSTGRESQL_USER"
  private val PWD      = "POSTGRESQL_PWD"

  private implicit val cs = IO.contextShift(executionContext)

  private lazy val host: ConfigValue[String]     = env(HOST).or(prop("postgresql.host")).as[String]
  private lazy val port: ConfigValue[String]     = env(PORT).or(prop("postgresql.port")).as[String]
  private lazy val database: ConfigValue[String] = env(DATABASE).or(prop("postgresql.database")).as[String]
  private lazy val user: ConfigValue[String]     = env(USER).or(prop("postgresql.user")).as[String]
  private lazy val password: ConfigValue[String] = env(PWD).or(prop("postgresql.pwd")).as[String]

  def loadURIPostgresql(): String = {
    "postgresql://" +
      host.load[IO].unsafeRunSync() + ":" +
      port.load[IO].unsafeRunSync() + "/" +
      database.load[IO].unsafeRunSync() + "?user=" +
      user.load[IO].unsafeRunSync() + "&password=" +
      password.load[IO].unsafeRunSync()
  }

  def loadPostgresqlContext(configPrefix: String, urlDatabase: Option[String] = None) = configPrefix match {
    case "" => {
      urlDatabase match {
        case None => {
          // App
          val urlPostgresql = loadURIPostgresql()
          logger.info(s"urlPostgresql=${urlPostgresql}")
          val config: Config = {
            ConfigFactory
              .empty()
              .withValue(
                "url",
                ConfigValueFactory.fromAnyRef(urlPostgresql)
              )
          }
          new PostgresAsyncContext(SnakeCase, config) with BasicQueries with OtherEntityQueries

        }
        case Some(valueUrl) => {
          val config: Config = {
            ConfigFactory
              .empty()
              .withValue(
                "url",
                ConfigValueFactory.fromAnyRef(urlDatabase.get)
              )
          }
          new PostgresAsyncContext(SnakeCase, config) with BasicQueries with OtherEntityQueries

        }
      }
    }
    case _ =>
      // test
      new PostgresAsyncContext(SnakeCase, configPrefix) with BasicQueries with OtherEntityQueries //
  }
}
object LoadEnvironmentDatabase {
  def apply()(implicit executionContext: ExecutionContext) = new LoadEnvironmentDatabase()
}
