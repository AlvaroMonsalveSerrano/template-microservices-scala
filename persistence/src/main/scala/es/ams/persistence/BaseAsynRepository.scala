package es.ams.persistence

import es.ams.persistence.queries.BasicQueries
import io.getquill.{PostgresAsyncContext, SnakeCase}

abstract class BaseAsynRepository(configPrefix: String) {
  // Define ctx with all Entity Queries
  protected val ctx = new PostgresAsyncContext(SnakeCase, configPrefix) with BasicQueries
}
