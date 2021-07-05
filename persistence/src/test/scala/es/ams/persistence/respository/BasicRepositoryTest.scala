package es.ams.persistence.respository

import es.ams.model._
import es.ams.persistence.BasicRepository

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class BasicRepositoryTest extends BaseBasicTest {

  "PostgreSQL queries persistence" should "Select all with class" in {
    executeInitScript()
    val basicRepository    = new BasicRepository("", Some(getURIDatabase()))
    val result: List[Base] = Await.result(basicRepository.findAll(), Duration.Inf)

    assert(result.size == 2)
  }

  it should "Select all with object" in {
    executeInitScript()
    val basicRepository    = new BasicRepository("", Some(getURIDatabase()))
    val result: List[Base] = Await.result(basicRepository.findAll(), Duration.Inf)

    assert(result.size > 0)
  }

}
