package es.ams.persistence

import es.ams.model.DomainBasic.Base

import scala.concurrent.Future

trait IBasic {
  def findAll(): Future[List[Base]]

  def insert(entity: Base): Future[Int]
}
