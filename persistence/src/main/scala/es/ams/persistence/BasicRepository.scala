package es.ams.persistence

import scala.concurrent.{ExecutionContext, Future}

/** Declarations of programs with operations in database.
  *
  * @param configPrefix
  * @param ec
  */
class BasicRepository(
    configPrefix: String = "",
    urlDatabase: Option[String] = None
)(implicit ec: ExecutionContext)
    extends BaseAsynRepository(configPrefix, urlDatabase)
    with IBasic {

  import ctx._
  import es.ams.model._

  override def findAll(): Future[List[Base]] = {
    val program = for {
      result <- runIO(selectAll())
    } yield { result }

    performIO(program)
  }

  override def insert(entity: Base): Future[Int] = {
    val program = for {
      result <- runIO(insertBase(entity))
    } yield { result }

    performIO(program)
  }

  override def delete(id: Int): Future[Int] = {
    val program = for {
      _ <- runIO(deleteBase(id))
    } yield { id }

    performIO(program)
  }

  override def update(entity: Base): Future[Base] = {
    val program = for {
      _ <- runIO(updateBase(entity))
    } yield { entity }

    performIO(program)
  }

}
object BasicRepository {
  def apply()(implicit ec: ExecutionContext)                            = new BasicRepository("")
  def apply(configPrefix: String)(implicit ec: ExecutionContext)        = new BasicRepository(configPrefix)
  def apply(urlDatabase: Option[String])(implicit ec: ExecutionContext) = new BasicRepository("", urlDatabase)
}
