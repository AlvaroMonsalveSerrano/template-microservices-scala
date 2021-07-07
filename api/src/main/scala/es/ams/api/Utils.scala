package es.ams.api

import cats.data.Chain
import cats.effect.IO
import ciris.{ConfigValue, env, prop}

import scala.concurrent.ExecutionContext

import es.ams.api.views.BasicDTO.{CreateBasic, UpdateBasic}

private[api] class Utils(implicit executionContext: ExecutionContext) {

  private val HOST     = "POSTGRESQL_HOST"
  private val PORT     = "POSTGRESQL_PORT"
  private val DATABASE = "POSTGRESQL_DATABASE"
  private val USER     = "POSTGRESQL_USER"
  private val PWD      = "POSTGRESQL_PWD"

  private lazy val host: ConfigValue[String]     = env(HOST).or(prop("postgresql.host")).as[String]
  private lazy val port: ConfigValue[String]     = env(PORT).or(prop("postgresql.port")).as[String]
  private lazy val database: ConfigValue[String] = env(DATABASE).or(prop("postgresql.database")).as[String]
  private lazy val user: ConfigValue[String]     = env(USER).or(prop("postgresql.user")).as[String]
  private lazy val password: ConfigValue[String] = env(PWD).or(prop("postgresql.pwd")).as[String]

  private implicit val cs = IO.contextShift(executionContext)

  // Es una utilidad.
  def getStringValueFromChain[A](chain: Chain[A]): String = chain match {
    case Chain(a) => a.toString
    case _        => ""
  }

  def getIntValueFromChain[A](chain: Chain[A]): Int = chain match {
    case Chain(a) => getStringValueFromChain(chain).toInt
    case _        => 0
  }

  def loadCreateBasic[A](elem1: Chain[A], elem2: Chain[A]): CreateBasic =
    CreateBasic(name = getStringValueFromChain(elem1), value = getStringValueFromChain(elem2))

  def loadUpdateBasic[A](elem1: Chain[A], elem2: Chain[A], elem3: Chain[A]): UpdateBasic =
    UpdateBasic(
      id = getIntValueFromChain(elem1),
      name = getStringValueFromChain(elem2),
      value = getStringValueFromChain(elem3)
    )

  def loadURIPostgresql(): Option[String] = {
    Some(
      "postgresql://" +
        host.load[IO].unsafeRunSync() + ":" +
        port.load[IO].unsafeRunSync() + "/" +
        database.load[IO].unsafeRunSync() + "?user=" +
        user.load[IO].unsafeRunSync() + "&password=" +
        password.load[IO].unsafeRunSync()
    )
  }

}
object Utils {
  def apply()(implicit executionContext: ExecutionContext) = new Utils()
}
