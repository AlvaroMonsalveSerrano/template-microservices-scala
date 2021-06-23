package es.ams.services.persistence.respository

import org.scalatest.flatspec.AnyFlatSpec
import es.ams.services.model.DomainBasic.Base
import es.ams.persistence.repository.BasicRepository

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class BasicRepositoryTest extends AnyFlatSpec {

  "PostgreSQL queries persistence" should "Select all with class" in {
    val basicRepository    = new BasicRepository("asynpostgres")
    val result: List[Base] = Await.result(basicRepository.findAll(), Duration.Inf)

    assert(result.size > 0)
  }

  it should "Select all with object" in {
    val basicRepository    = BasicRepository("asynpostgres")
    val result: List[Base] = Await.result(basicRepository.findAll(), Duration.Inf)

    assert(result.size > 0)

  }

}
