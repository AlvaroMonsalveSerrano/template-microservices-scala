package es.ams.persistence.queries

import io.getquill.context.Context
import io.getquill.monad.IOMonad

protected[persistence] trait OtherEntityQueries extends IOMonad {

  this: Context[_, _] =>

}
