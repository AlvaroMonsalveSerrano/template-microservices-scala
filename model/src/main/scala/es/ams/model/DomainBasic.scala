package es.ams.model

/** Common elements to use cases
  */
protected[model] class BaseModel {}

/** Model for Basic use case
  */
protected[model] trait DomainBasic {

  case class Base(id_rec: Int, length_rec: Int, width_rec: Int)

}
