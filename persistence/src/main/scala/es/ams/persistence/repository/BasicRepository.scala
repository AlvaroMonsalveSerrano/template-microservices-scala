package es.ams.persistence.repository

import es.ams.persistence.{BaseAsynRepository, IBase}

import scala.concurrent.{ExecutionContext, Future}

class BasicRepository(configPrefix: String)(implicit ec: ExecutionContext)
    extends BaseAsynRepository(configPrefix)
    with IBase {

  import ctx._
  import es.ams.model.DomainBasic.Base

  override def findAll(): Future[List[Base]] = {
    val program = for {
      result <- runIO(selectAll())
    } yield { result }

    performIO(program)
  }

}
object BasicRepository {
  def apply()(implicit ec: ExecutionContext)                     = new BasicRepository("asynpostgres")
  def apply(configPrefix: String)(implicit ec: ExecutionContext) = new BasicRepository(configPrefix)
}
