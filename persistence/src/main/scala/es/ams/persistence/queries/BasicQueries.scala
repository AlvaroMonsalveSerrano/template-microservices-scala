package es.ams.persistence.queries

import io.getquill._
import io.getquill.context.Context
import io.getquill.monad.IOMonad
import es.ams.services.model.DomainBasic.Base

trait BasicQueries extends IOMonad {
  this: Context[_, _] =>

  def selectAll(): Quoted[EntityQuery[Base]] = {
    quote {
      query[Base]
    }
  }

  def insertBase(entity: Base): Quoted[ActionReturning[Base, Int]] = {
    quote {
      query[Base]
        .insert(lift(entity))
        .returningGenerated(_.id_rec)
    }
  }

}
