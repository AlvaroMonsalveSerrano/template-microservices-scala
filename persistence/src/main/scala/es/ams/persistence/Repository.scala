package es.ams.persistence

import scala.concurrent.Future

import es.ams.model.DomainBasic.Base

trait IBasic {
  def findAll(): Future[List[Base]]
}
