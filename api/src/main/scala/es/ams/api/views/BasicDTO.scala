package es.ams.api.views

protected[api] object BasicDTO {

  case class CreateBasic(name: String, value: String)

  case class UpdateBasic(id: Int, name: String, value: String)

  case class DeleteBasic(id: Int)
}
