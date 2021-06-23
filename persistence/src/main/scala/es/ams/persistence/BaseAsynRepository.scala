package es.ams.persistence

import es.ams.persistence.queries.{BasicQueries, OtherEntityQueries}
import io.getquill.{PostgresAsyncContext, SnakeCase}

/** Definition of the base class of the repository. It has a reference to the Quill context which must implement those
  * operations for the different domain entities.
  *
  * @param configPrefix Context name
  */
abstract class BaseAsynRepository(configPrefix: String) {
  // Define ctx with all Entity Queries
  protected val ctx = new PostgresAsyncContext(SnakeCase, configPrefix) with BasicQueries with OtherEntityQueries
}
