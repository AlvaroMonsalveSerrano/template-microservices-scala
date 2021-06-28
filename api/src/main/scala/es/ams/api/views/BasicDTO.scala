package es.ams.api.views

private[api] object BasicDTO {

  sealed trait BaseBasic
  case class CreateBasic(name: String, value: String)          extends BaseBasic
  case class UpdateBasic(id: Int, name: String, value: String) extends BaseBasic
  case class DeleteBasic(id: Int)                              extends BaseBasic
}
