package es.ams.persistence.queries

import io.getquill._
import io.getquill.context.Context
import io.getquill.monad.IOMonad
import es.ams.model._

protected[persistence] trait BasicQueries extends IOMonad {
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

  def deleteBase(id: Int): Quoted[Delete[Base]] = {
    quote {
      query[Base]
        .filter(e => e.id_rec == lift(id))
        .delete
    }
  }

  def updateBase(entity: Base): Quoted[Update[Base]] = {
    quote {
      query[Base]
        .filter(e => e.id_rec == lift(entity.id_rec))
        .update(lift(entity))
    }
  }

}
