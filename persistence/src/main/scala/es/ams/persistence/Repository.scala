package es.ams.persistence

import scala.concurrent.Future

import es.ams.model.DomainBasic.Base

trait IBase {
  def findAll(): Future[List[Base]]
}
