package es.ams.persistence

import es.ams.model._

import scala.concurrent.Future

protected[persistence] trait IBasic {
  def findAll(): Future[List[Base]]

  def insert(entity: Base): Future[Int]

  def delete(id: Int): Future[Int]

  def update(entity: Base): Future[Base]
}
